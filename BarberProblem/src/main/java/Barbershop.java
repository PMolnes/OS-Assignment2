public class Barbershop {
    private final static int NUMBER_OF_WAITING_CHAIRS = 5;

    public static void main(String[] args) {
        Barber barber = new Barber(NUMBER_OF_WAITING_CHAIRS);

        barber.start();

        Customer[] customers = new Customer[50];
        for (int i = 0; i < 50; i++) {
            Customer customer = new Customer();
            customer.setBarber(barber);
            customer.start();
            customers[i] = customer;
        }

        try {
            for (int i = 0; i < 50; i++) {
                customers[i].join();
            }
            barber.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
