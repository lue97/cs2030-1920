/**
* A leave event for event simulation.
*/
public class LeaveEvent extends Event {

	/**
	* Constructs a ServeEvent with the specified time and customer.
	* @param time The scheduled time for this event
	* @param customer The customer associated with this event
	*/
	public LeaveEvent(double time, Customer customer) {
		super(time, customer);
	}

	/**
	* Executes the activities to be carried out during the leave event. The
	* customer will leave the simulation.
	* @param simulation The simulation associated with this event
	*/
	public void execute(Simulation simulation) {
		simulation.setClock(this.time);
		System.out.println(this.toString());
		((CustomerSimulation) simulation).getStat().incLeft(1);
	}

	@Override
	public String toString() {
		return String.format(
				"%.3f %d leaves",
				this.time,
				this.customer.getId());
	}
}
