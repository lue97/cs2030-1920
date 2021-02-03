public class TakeACab extends Service {
    public TakeACab() {
        super(33); 
    }

    @Override
    public int computeFare(Request r) {
        return this.fare * r.dist + 200;
    }
    @Override
    public String toString() {
        return "TakeACab";
    }
}
