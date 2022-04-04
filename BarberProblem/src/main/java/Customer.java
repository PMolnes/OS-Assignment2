import java.util.Random;

public class Customer extends Thread {

    /**
     * Barber used by the Customer
     */
    private Barber barber;

    private boolean haircutDone = false;

    private final Random random = new Random();

    public void setBarber(Barber barber) {
        this.barber = barber;
    }

    private boolean askForCut() throws Exception {
        if (barber == null) {
            System.out.println("Customer " + getId() + ": No Barber assigned for Customer.");
            return false;
        }

        System.out.println("Customer " + getId() + ": Arrived, wants a haircut...");
        boolean barberAvailable = barber.getIntoBarberChair(this);

        if (!barberAvailable) {
            if (barber.placeCustomerInWaitingChair(this)) {
                System.out.println("Customer " + getId() + ": Sits down to wait, Barber is busy...");
                waitForBarberToBeAvailable();
            } else {
                System.out.println("Customer " + getId() + ": Leaves, waiting seats are full.");
                return false;
            }
        }

        System.out.println("Customer " + getId() + ": Sits down in Barber's chair...");
        waitForCutToBeDone();
        this.haircutDone = true;

        return true;
    }

    public synchronized void waitForCutToBeDone() throws InterruptedException {
        this.wait();
    }

    private synchronized void waitForBarberToBeAvailable() throws InterruptedException {
        this.wait();
    }

    public synchronized void notifyCutDone() throws InterruptedException {
        Customer.this.notifyAll();
    }

    public synchronized void notifyWaitingDone() throws InterruptedException {
        Customer.this.notifyAll();
    }

    @Override
    public void run() {
        while (!haircutDone) {
            try {
                sleep(random.nextInt(3000));
                askForCut();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
