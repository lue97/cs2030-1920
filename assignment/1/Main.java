import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {

	/**
	* The entry point of the program. The program takes the number of servers
	* followed by a list of arrival time of customers from STDIN. The system
	* the creates a simulation based on the above parameters and runs it.
	* Finally, the system prints the statistics of the simulation.
	*/
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		List<Customer> customers = new ArrayList<Customer>();
		int serverCount = scanner.nextInt();
		while (scanner.hasNextDouble()) {
			customers.add(new Customer(scanner.nextDouble()));
		}
		CustomerSimulation simulation = new CustomerSimulation(
				new EventComparator(),
				serverCount,
				customers);
		simulation.start();
		System.out.println(((CustomerSimulation)simulation).getStat().toString());
	}
}
