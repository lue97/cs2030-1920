public abstract class Service {
    public int fare;
    public Service(int fare) {
        this.fare = fare;
    }
    public abstract int computeFare(Request request);
}
