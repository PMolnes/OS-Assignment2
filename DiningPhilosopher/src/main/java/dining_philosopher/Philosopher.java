package dining_philosopher;

public class Philosopher {

    private Chopstick leftChopstick;
    private Chopstick rightChopstick;

    public Philosopher(Chopstick leftChopstick, Chopstick rightChopstick) {
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
    }
}
