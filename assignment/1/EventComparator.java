import java.util.Comparator;

/**
* Comparator for scheduling events for the simulation.
*/
class EventComparator implements Comparator<Event> {

	/**
	* Compares the priority of 2 events. The events are first compared with
	* their scheduled time. If there is a conflict, then the id of customer
	* will be taken into consideration.
	* @param e1 The first event to be compared
	* @param e2 The second event to be compared
	* @return The result of comparison
	*/
	public int compare(Event e1, Event e2) {
		if (e1.getTime() < e2.getTime()) {
			return -1;
		} else if (e1.getTime() == e2.getTime()) {
			return e1.getCustomer().getId() - e2.getCustomer().getId();
		} else {
			return 1;
		}
	}
}
