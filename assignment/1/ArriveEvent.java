/**
* A arrive event for event simulation.
*/
public class ArriveEvent extends Event {

	/**
	* Constructs an ArriveEvent with the specified customer.
	* @param customer The customer associated with this event
	*/
	public ArriveEvent(Customer customer) {
		super(customer.getArrival(), customer);
	}

	/**
	* Executes the activities to be carried out during the arrive event.
	* This event assigns a server to the customer and adds an WaitEvent
	* or ServeEvent to the event queue of the associated simulation.
	* @param simulation The simulation associated with this event
	*/
	public void execute(Simulation simulation) {
		simulation.setClock(this.time);
		Server server;
		if ((server = (((CustomerSimulation) simulation).getServer())) != null) {
			server.enqueue(this.customer);
			if (server.getCurrent() == null) {
				simulation.add(
						new ServeEvent(this.customer.getArrival(),
						this.customer, server));
			} else {
				simulation.add(
						new WaitEvent(this.customer.getArrival(),
							this.customer, server));
			}
		} else {
			simulation.add(new LeaveEvent(this.customer.getArrival(), this.customer));
		}
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		return String.format(
				"%.3f %d arrives",
				this.customer.getArrival(),
				this.customer.getId());
	}
}
