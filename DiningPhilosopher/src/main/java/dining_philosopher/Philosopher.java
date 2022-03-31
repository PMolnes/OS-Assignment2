package dining_philosopher;

import java.util.Random;

public class Philosopher implements Runnable {

    private int id;
    private Chopstick leftChopstick;
    private Chopstick rightChopstick;
    private State state;

    public Philosopher(int id, Chopstick leftChopstick, Chopstick rightChopstick) {
        this.id = id;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        this.state = State.THINKING;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Chopstick getLeftChopstick() {
        return leftChopstick;
    }

    public Chopstick getRightChopstick() {
        return rightChopstick;
    }

    @Override
    public void run() {
        // etter random amount s 1-10 bli sulten
        Random random = new Random();
        this.state = State.HUNGRY;
    }
}
