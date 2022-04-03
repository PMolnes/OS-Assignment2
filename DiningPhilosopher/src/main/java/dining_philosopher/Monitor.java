package dining_philosopher;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This is a Monitor class which keeps track of the shared resources
 * (chopsticks). It checks wether neighbouring Philosophers are eating,
 * before given Philosopher decides to eat.
 */
public class Monitor {
    private final ReentrantLock lock;
    private final Condition[] self;
    private State state[];

    public Monitor() {
        /**
         * This creates a new lock where the "Fairness" parameter is set to true.
         * This lets the thread that has waited the longest get access to it first,
         * if multiple threads try to access it at the same time. Overall, this
         * fixes the starvation problem.
         */
        lock = new ReentrantLock(true);
        self = new Condition[5];
        state = new State[5];

        for (int i = 0; i < 5; i++) {
            self[i] = lock.newCondition();
            state[i] = State.THINKING;
        }
    }

    public void tryEat(int i) {
        try {
            pickup(i);
            putdown(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests if the chopsticks next to the given Philosopher
     * are available.
     * @param i Philosopher that tries to pickup chopsticks.
     */
    private void availableChopsticks(int i) {
        if ((state[(i + 1) % 5] != State.EATING) && (state[(i + 4) % 5] != State.EATING) && (state[i] == State.HUNGRY)) {
            state[i] = State.EATING;
            System.out.println("Philosopher " + i + " is eating.");
            self[i].signal();
        }
    }

    /**
     * Entered Philosopher gets hungry and tries to pick acquire the shared
     * resources (chopsticks). If the chopsticks are available, he will start
     * eating, or he will wait until the chopsticks are available.
     * @param i the Philosopher that wants to eat.
     * @throws InterruptedException
     */
    private void pickup(int i) throws InterruptedException {
        lock.lock();
        state[i] = State.HUNGRY;
        System.out.println("Philosopher " + i + " is hungry.");
        availableChopsticks(i);

        if (state[i] != State.EATING) self[i].await();
        lock.unlock();
    }

    private void putdown(int i) {
        lock.lock();
        state[i] = State.THINKING;
        System.out.println("Philosopher " + i + " is thinking...");

        availableChopsticks((i + 1) % 5);
        availableChopsticks((i + 4) % 5);

        lock.unlock();
    }
}
