import java.util.ArrayList;

public class Barber extends Thread {

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
    private synchronized boolean getIntoBarberChair(Customer customer) {
        if (isBusy()) return false;

        //Barber is not busy, and puts the customer as his currentCustomer
        this.currentCustomer = customer;

        //Wake up the barber
        this.notify();
        return true;
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
}
