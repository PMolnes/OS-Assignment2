import java.util.ArrayList;
import java.util.Random;

public class Barber extends Thread {

    private final Random random = new Random();
    /**
     * This is the current customer the barber is
     * cutting/shaving.
     */
    private Customer currentCustomer = null;

    /**
     * Customers waiting in queue.
     */
    private final ArrayList<Customer> waitingCustomers;

    /**
     * The number of waiting chairs totally available.
     */
    private final int waitingChairs;

    public Barber(int waitingChairs) {
        this.waitingChairs = waitingChairs;
        this.waitingCustomers = new ArrayList<Customer>(waitingChairs);
    }

    /**
     * Customer tries to get into the BarberChair and get a haircut.
     * If the Barber is busy it returns false, true if the customer
     * can get his hair cut.
     * @param customer the customer that want's to get his hair cut.
     * @return true if the customer can get his hair cut, false if the
     * barber is busy.
     */
    public synchronized boolean getIntoBarberChair(Customer customer) {
        if (isBusy()) return false;

        //Barber is not busy, and puts the customer as his currentCustomer
        this.currentCustomer = customer;

        //Wake up the barber
        this.notify();
        return true;
    }

    /**
     * A customer tries to take a place in the waiting seats. Places the customer in a seat
     * if there is space.
     * @param customer to be placed in queue
     * @return true if the customer gets placed in the queue, false if it's full.
     * @throws Exception
     */
    public boolean placeCustomerInWaitingChair(Customer customer) throws Exception {
        if (waitingCustomers.contains(customer)) {
            throw new Exception("Customer " + customer.getId() + " already in queue.");
        }
        System.out.println("Customer " + customer.getId() + ": Wants to wait, " +
                waitingCustomers.size() + " customer(s) already waiting.");
        if (freeWaitingChairs() < 1) {
            return false;
        } else {
            waitingCustomers.add(customer);
            return true;
        }
    }

    /**
     * The Barber sleeps until the first Customer comes
     * into the shop.
     * @throws InterruptedException
     */
    private synchronized void sleepUntilCustomer() throws InterruptedException {
        System.out.println("Barber: Sleeping zZzZ...");
        this.wait();
    }

    /**
     * @return true if the Barber is busy cutting hair.
     */
    private boolean isBusy() {
        return currentCustomer != null;
    }

    /**
     * @return the amount of free waiting chairs.
     */
    private int freeWaitingChairs() {
        return this.waitingChairs - waitingCustomers.size();
    }

    private void cutCustomer() throws InterruptedException {
        System.out.println("Barber: Giving haircut to customer: " + currentCustomer.getId() + "...");
        int cutTime = random.nextInt(1000);
        sleep(cutTime);
        System.out.println("Barber: Finished haircut for customer " + currentCustomer.getId());

        this.currentCustomer.notifyCutDone();

        this.currentCustomer = null;
    }

    private Customer getNextWaitingCustomer() throws InterruptedException {
        if (waitingCustomers.size() < 1) return null;

        Customer customer = waitingCustomers.remove(0);
        System.out.println("Customer " + customer.getId() + ": Sits down to get haircut.");
        customer.notifyWaitingDone();
        return customer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                sleepUntilCustomer();

                while (currentCustomer != null) {
                    cutCustomer();
                    currentCustomer = getNextWaitingCustomer();
                }
            }
        } catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
