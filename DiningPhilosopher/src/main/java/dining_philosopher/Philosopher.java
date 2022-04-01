package dining_philosopher;

public class Philosopher extends Thread {

    private Monitor monitor;
    private int id;

    public Philosopher(int id, Monitor monitor) {
        this.id = id;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            monitor.tryEat(id);
        }
    }
}
