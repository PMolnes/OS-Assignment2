package dining_philosopher;

public class Philosopher extends Thread {

    private Monitor monitor;
    private int id;

    public Philosopher(int id, Monitor monitor) {
        this.id = id;
        this.monitor = monitor;
    }

    /**
     * Each thread (Philosopher) tries to eat 10 times.
     * This can easily be turned into an infinite loop with a
     * while (true) loop.
     */
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            monitor.tryEat(this.id);
        }
    }
}
