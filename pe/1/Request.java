public class Request {
    int dist;
    int pax;
    int time;
    public Request(int d, int p, int t) {
        dist = d;
        pax = p;
        time = t;
    }
    @Override
    public String toString() {
        return String.format("%dkm for %dpax @ %dhrs", dist, pax, time);
    }
}
