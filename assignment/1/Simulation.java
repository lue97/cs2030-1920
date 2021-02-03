import java.util.Comparator;
import java.util.PriorityQueue;

/**
* A Simulation class for discrete event simulations.
*/
public abstract class Simulation {

	/**
	* A event queue for this simulation.
	*/
	protected PriorityQueue<Event> events;

	/**
	* Current time of this simulation.
	*/
	protected double time;

	/**
	* Constructs a Simulation with an empty event queue and an event comparator.
	* @param comparator A comparator used for event scheduling
	*/
	public Simulation(EventComparator comparator) {
		this.events = new PriorityQueue<Event>(11, comparator);
		this.time = 0;
	}

	/**
	* Sets the simulation clock.
	* @param time New time for clock
	*/
	public void setClock(double time) {
		this.time = time;
	}

	/**
	* Adds an event to the event queue.
	* @param event The event to be added
	*/
	public abstract void add(Event event);

	/**
	* Start the simulation.
	*/
	public abstract void start();
}
