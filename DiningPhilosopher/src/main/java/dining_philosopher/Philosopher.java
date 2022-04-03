package dining_philosopher;

public class Philosopher extends Thread {

    private Monitor monitor;
    private int id;

    public Philosopher(int id, Monitor monitor) {
        this.id = id;
        this.monitor = monitor;
    }

    /**
     * Each thread (Philosopher) tries to eat an infinite amount of times.
     */
    @Override
    public void run() {
        while (true) {
            monitor.tryEat(this.id);
        }
    }
}
