import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class CustomerSimulation extends Simulation {

	/**
	* The list of servers of this system.
	*/
	private List<Server> serverPool;

	/**
	* The statistics of the system.
	*/
	private Statistics stat;
	
	/**
	* Constructs a CustomerSimulation with the specified simulation comparator,
	* the number of server, and the customers.
	* @param comparator the comparator for prioritizing events for this system
	* @param serverCount the number of servers for this simulation
	* @param customers the customers for this simulation
	*/
	public CustomerSimulation(EventComparator comparator, int serverCount, List<Customer> customers) {
		super(comparator);
		this.stat = new Statistics();
		this.serverPool = new ArrayList<Server>();
		for (int i = 0; i < serverCount; i++) {
			this.serverPool.add(new Server());
		}
		for (Customer customer : customers) {
			this.events.add(new ArriveEvent(customer));
		}
	}

	/**
	* Returns the statistics of this system.
	* @return The statistics of the system
	*/
	public Statistics getStat() {
		return this.stat;
	}

	/**
	* Enqueues a new event to this simulation.
	*/
	public void add(Event event) {
		this.events.add(event);
	}

	/**
	* Start the simulation.
	*/
	public void start() {
		while (this.events.peek() != null) {
			this.events.remove().execute(this);
		}
	}

	/**
	* Returns a server from serverPool. The obtained server will be the server
	* serving the least number of customers.
	* @return The server with least load
	*/
	public Server getServer() {
		Server server;
		int minIndex = -1;
		int minima = 2;
		for (int i = 0; i < this.serverPool.size(); i++) {
			server = this.serverPool.get(i);
			if (server.isAvailable()) {
				if (server.getLoad() < minima) {
					minIndex = i;
					minima = server.getLoad();
				}
			}
		}
		if (minIndex == -1) {
			return null;
		} else {
			return this.serverPool.get(minIndex);
		}
	}

}
