public class DoneEvent extends Event {

	/**
	* The server associated with this event.
	*/
	private final Server server;

	/**
	* Constructs a DoneEvent based on the spcified time, customer, and server.
	* @param time The scheduled time for this event
	* @param customer The customer associated with this event
	* @param server The server assoiciated with this event
	*/
	public DoneEvent(double time, Customer customer, Server server) {
		super(time, customer);
		this.server = server;
	}

	/**
	* Executes the activities to be carried out during the done event. The
	* server will stop serving the customer.
	* @param simulation The simulation associated with this event
	*/
	public void execute(Simulation simulation) {
		simulation.setClock(this.time);
		this.server.flushCurrent();
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		return String.format(
			"%.3f %d done serving by %d", 
			this.time,
			this.customer.getId(),
			this.server.getId());
	}
}
