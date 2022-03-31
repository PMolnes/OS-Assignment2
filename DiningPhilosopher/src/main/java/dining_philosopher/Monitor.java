package dining_philosopher;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    Chopstick[] chopsticks = new Chopstick[5];
    Philosopher[] philosophers = new Philosopher[5];
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition[] self;

    public Monitor() {
        State[] states = new State[5];
        self = new Condition[5];
        for (int i = 0; i < 5; i++) {
            states[i] = State.THINKING;
            chopsticks[i] = new Chopstick(i);
        }
        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i + 4) % 5]);
        }
    }

    /**
     *
     * @param i
     */
    private void tryEat(int i) throws InterruptedException {
        Philosopher philosopher = philosophers[i];
        Chopstick leftChopstick = philosopher.getLeftChopstick();
        Chopstick rightChopstick = philosopher.getRightChopstick();
        if ((philosopher.getState() == State.HUNGRY) && (leftChopstick.available()) && (rightChopstick.available())) {
            philosopher.setState(State.EATING);
            leftChopstick.pickUp();
            rightChopstick.pickUp();
            // random amount of s 1 - 10?
            leftChopstick.putDown();
            rightChopstick.putDown();
            philosopher.setState(State.THINKING);
            self[i].signal();
        } else {
            self[i].wait();
        }
    }




}


/**
 * 1. Philosopher is thinking...
 * 2. Philosopher gets hungry:
 * 3. Am I first in queue? No: wait Yes:
 * 4. Are the chopsticks to the right and left available? Yes: Eat no: Wait
 */