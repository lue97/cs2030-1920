import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Request r = new Request(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
		Driver d;
        List<Booking> bookings = new ArrayList<Booking>();
        while (scanner.hasNext()) {
	        switch (scanner.next()) {
    	    	case "NormalCab":
					d = new NormalCab(scanner.next(), scanner.nextInt());
					bookings.add(new Booking(d, new JustRide(), r));
					bookings.add(new Booking(d, new TakeACab(), r));
					break;
				case "PrivateCar":
					d = new PrivateCar(scanner.next(), scanner.nextInt());
					bookings.add(new Booking(d, new JustRide(), r));
					bookings.add(new Booking(d, new ShareARide(), r));
					break;
    		}
		}
		Collections.sort(bookings);
		for (Booking b : bookings) {
			System.out.println(b);
		}
	}
}

