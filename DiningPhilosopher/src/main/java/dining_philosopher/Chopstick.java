package dining_philosopher;

public class Chopstick {

    private int id;
    private boolean available;

    public Chopstick(int id) {
        this.id = id;
        this.available = true;
    }

    public boolean available() {
        return this.available;
    }

    public int getId() {
        return this.id;
    }

    public void pickUp() {
        this.available = false;
    }

    public void putDown() {
        this.available = true;
    }
}
