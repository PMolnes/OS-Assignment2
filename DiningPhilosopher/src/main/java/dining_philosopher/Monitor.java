package dining_philosopher;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    private final ReentrantLock lock;
    private final Condition[] self;
    private State state[];

    public Monitor() {
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

    private void availableChopsticks(int i) {
        if ((state[(i + 1) % 5] != State.EATING) && (state[(i + 4) % 5] != State.EATING) && (state[i] == State.HUNGRY)) {
            state[i] = State.EATING;
            System.out.println("Philosopher " + i + " is eating.");
            self[i].signal();
        }
    }

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
