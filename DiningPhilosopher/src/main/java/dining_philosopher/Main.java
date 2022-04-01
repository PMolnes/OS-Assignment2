package dining_philosopher;

public class Main {

    public static void main(String[] args) {

        Monitor monitor = new Monitor();
        Philosopher[] philosophers = new Philosopher[5];

        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher(i, monitor);
        }
        for (int i = 0; i < 5; i++) {
            philosophers[i].start();
        }

        try {
            for (int i = 0; i < 5; i++) {
                philosophers[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("  -      10 rounds of the    -");
        System.out.println(" ---     Dining Philosophers -");
        System.out.println("-----    Problem             -");
        System.out.println("  -      - - - - - - - - - - -");
        System.out.println("  -      - - - - - - - - - - -");
        System.out.println("  -      - - - - - - - - - - -");
    }
}
