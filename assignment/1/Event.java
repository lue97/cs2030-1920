/**
* An Event class for event simulation.
*/
public abstract class Event {

	/**
	* The scheduled time of this event.
	*/
	protected final double time;

	/**
	* The customer associated with this event.
	*/
	protected final Customer customer;

	/**
	* Constructs an Event with the specified time and customer.
	* @param time The scheduled time for this event
	* @param customer The customer associated with this event
	*/
	protected Event(double time, Customer customer) {
		this.time = time;
		this.customer = customer;
	}

	/**
	* Returns the scheduled time of this event.
	* @return Time of this event
	*/
	public double getTime() {
		return this.time;
	}

	/**
	* Return the associated customer of this event.
	* @return Customer of this event
	*/
	public Customer getCustomer() {
		return this.customer;
	}
	
	/**
	* Executes activities to be carried out during this event.
	* @param simulation The simulation used
	*/
	public abstract void execute(Simulation simulation);
}
