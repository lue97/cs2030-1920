public class Driver {
    String id;
    int wait;
    public Driver(String id, int wait) {
        this.id = id;
        this.wait = wait;
    }
    @Override
    public String toString() {
        return String.format("%s (%d mins away) %s", id, wait, getClass().getSimpleName());
    }
}
