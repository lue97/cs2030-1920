public class JustRide extends Service {
    public JustRide() {
        super(22); 
    }

    @Override
    public int computeFare(Request r) {
        int total = 0;
        if (r.time >= 600 && r.time <= 900) total += 500;
        return total + this.fare * r.dist;
    }
    @Override
    public String toString() {
        return "JustRide";
    }
}
