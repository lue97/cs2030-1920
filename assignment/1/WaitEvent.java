public class WaitEvent extends Event {

	/**
	* The server associated with this event.
	*/
	private final Server server;

	/**
	* Constructs a Wait based on the spcified time, customer, and server.
	* @param time The scheduled time for this event
	* @param customer The customer associated with this event
	* @param server The server assoiciated with this event
	*/
	public WaitEvent(double time, Customer customer, Server server) {
		super(time, customer);
		this.server = server;
	}

	/**
	* Executes the activities to be carried out during the wait event. The
	* customer will wait until its server finish serving the current customer.
	* @param simulation The simulation associated with this event
	*/
	public void execute(Simulation simulation) {
		simulation.setClock(this.time);
		simulation.add(new ServeEvent(
				this.server.getCurrentCompletionTime(),
				this.customer,
				this.server));
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		return String.format(
				"%.3f %d waits to be served by %d",
				this.customer.getArrival(),
				this.customer.getId(),
				this.server.getId());
	}
}
