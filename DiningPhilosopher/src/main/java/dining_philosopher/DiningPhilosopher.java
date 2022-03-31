package dining_philosopher;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosopher {

    enum State { THINKING, HUNGRY, EATING };
    State[] state = new State[5];
    Condition[] self = new Condition[5];
    Lock lock = new ReentrantLock();

    public DiningPhilosopher() {

    }

    /**
     * Lets given philosopher eat if his neighbours are
     * not eating and he is hungry.
     * @param i index of the philosopher to test.
     */
    private void test(int i) {
        if ((state[(i + 4) % 5] != State.EATING) &&
            (state[i] == State.HUNGRY) &&
            (state[(i + 1) % 5] != State.EATING)) {
            state[i] = State.EATING;
            System.out.println("Philosopher " + i + " is eating");
            self[i].signal(); // Let's the other philosophers know something has changed.
        }

    }
}
