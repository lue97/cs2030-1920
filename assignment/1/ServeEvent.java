/**
* A serve event for event simulation.
*/
public class ServeEvent extends Event {

	/**
	* The server associated with this event.
	*/
	private final Server server;

	/**
	* Constructs a ServeEvent with the specified time, customer, and server.
	* @param time The scheduled time for this event
	* @param customer The customer associated with this event
	* @param server The server assoiciated with this event
	*/
	public ServeEvent(double time, Customer customer, Server server) {
		super(time, customer);
		this.server = server;
	}

	/**
	* Executes the activities to be carried out during the serve event.
	* The server associated with this event will start serving the customer
	* associated with this event.
	* @param simulation The simulation associated with this event
	*/
	public void execute(Simulation simulation) {
		simulation.setClock(this.time);
		((CustomerSimulation) simulation).getStat().incServed(1);
		((CustomerSimulation) simulation).getStat().incWait(
				this.time - this.customer.getArrival());
		this.server.serve(this.time);
		simulation.add(
				new DoneEvent(this.time + this.customer.getServeTime(),
				this.customer, this.server));
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		return String.format(
				"%.3f %d served by %d",
				this.time,
				this.customer.getId(), this.server.getId());
	}

}
