public class Booking implements Comparable<Booking> {
    Driver driver;
    Service service;
    Request request;
    public Booking(Driver driver, Service service, Request request) {
        this.driver = driver;
        this.service = service;
        this.request = request;
    }

    @Override
    public int compareTo(Booking b) {
        if (b.service.computeFare(b.request) > this.service.computeFare(request)) {
            return -1;
        } else if (b.service.computeFare(b.request) == this.service.computeFare(request)) {
            return this.driver.wait - b.driver.wait;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "$%.2f using %s (%s)",
                this.service.computeFare(this.request) / 100.0,
                this.driver.toString(),
                this.service.toString()
                );
    }
}
