package dining_philosopher;

public class Philosopher implements Runnable {

    private Chopstick leftChopstick;
    private Chopstick rightChopstick;

    public Philosopher(Chopstick leftChopstick, Chopstick rightChopstick) {
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
    }

    @Override
    public void run() {
        
    }
}
