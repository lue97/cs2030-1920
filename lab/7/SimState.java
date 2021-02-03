import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Function;

/**
 * This class encapsulates all the simulation states.There are four main
 * components: (i) the event queue, (ii) the statistics, (iii) the shop
 * (the servers) and (iv) the event logs.
 *
 * @author atharvjoshi
 * @author weitsang
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
public class SimState {
	class Event implements Comparable<Event> {
		double time;
		Function<SimState, SimState> lambda;
		Event(double time, Function<SimState, SimState> lambda) {
			this.lambda = lambda;
			this.time = time;
		}

		public int compareTo(Event other) {
			return (int)Math.signum(this.time - other.time);
		}

		public SimState simulate(SimState sim) {
			return this.lambda.apply(sim);
		}
	}

	/** The priority queue of events. */
	public final PriorityQueue<Event> events;

	/** The statistics maintained. */
	public final Statistics stats;

	/** The shop of servers. */
	public final Shop shop;

	public final List<String> logs;

	public SimState(int numOfServers) {
		this.shop = new Shop(numOfServers);
		this.stats = new Statistics();
		this.events = new PriorityQueue<Event>();
		this.logs = new ArrayList<String>();
	}

	public SimState(PriorityQueue<Event> e, Statistics st, Shop sh, List<String> logs) {
		this.events = new PriorityQueue<Event>(e);
		this.stats = new Statistics(st);
		this.shop = new Shop(sh.getServers());
		this.logs = new ArrayList<String>(logs);
	}

	/**
	 * Add an event to the simulation's event queue.
	 * @parame The event to be added to the queue.
	 * @return The new simulation state.
	 */
	public SimState addEvent(double time, Function<SimState, SimState> lambda) {
		return new SimState(events.add(new Event(time, lambda)), this.stats, this.shop, this.logs);
	}

	/**
	 * Retrieve the next event with earliest time stamp from the
	 * priority queue, and a new state.If there is no more event, an
	 * Optional.empty will be returned.
	 * @return A pair object with an (optional) event and the new simulation
	 *   state.
	 */
	public Pair<Optional<Event>, SimState> nextEvent() {
		return Pair.of(
				   this.events.poll().first
,				   new SimState(this.events.poll().second, this.stats, this.shop, this.logs)
			   );
	}

	public SimState noteArrival(double time, Customer c) {
		List<String> l = new ArrayList<>(this.logs);
		l.add(String.format("%.3f %s arrives\n", time, c));
		return new SimState(this.events, this.stats, this.shop, l);
	}

	public SimState noteWait(double time, Server s, Customer c) {
		List<String> l = new ArrayList<String>(logs);
		l.add(String.format("%.3f %s waits to be served by %s\n", time, c, s));
		return new SimState(this.events, this.stats, this.shop, l);
	}

	public SimState noteServed(double time, Server s, Customer c) {
		List<String> l = new ArrayList<String>(logs);
		l.add(String.format("%.3f %s served by %s\n", time, c, s));
		return new SimState(
				   this.events,
				   this.stats.serveOneCustomer().recordWaitingTime(c.timeWaited(time)),
				   this.shop,
				   l
			   );
	}

	public SimState noteDone(double time, Server s, Customer c) {
		List<String> l = new ArrayList<String>(logs);
		l.add(String.format("%.3f %s done serving by %s\n", time, c, s));
		return new SimState(this.events, this.stats, this.shop, l);
	}

	public SimState noteLeave(double time, Customer customer) {
		List<String> l = new ArrayList<String>(logs);
		l.add(String.format("%.3f %s leaves\n", time, customer));
		return new SimState(this.events, this.stats.looseOneCustomer(), this.shop, l);
	}

	/**
	 * Simulates the logic of what happened when a customer arrives.
	 * The customer is either served, waiting to be served, or leaves.
	 * @param time The time the customer arrives.
	 * @return A new state of the simulation.
	 */
	public SimState simulateArrival(double time) {
		Statistics newStats = stats.incCustomer();
		Customer customer = new Customer(time, newStats.totalCustomers);
		SimState newSim = new SimState(this.events, newStats, this.shop, this.logs);
		return newSim.noteArrival(time, customer).processArrival(time, customer);
	}

	/**
	 * Handle the logic of finding idle servers to serve the customer,
	 * or a server that the customer can wait for, or leave. Called
	 * from simulateArrival.
	 * @param time The time the customer arrives.
	 * @param customer The customer to be served.
	 * @return A new state of the simulation.
	 */
	public SimState processArrival(double time, Customer customer) {
		Optional<Server> s = shop.find(server -> server.isIdle());
		if (s.isPresent()) {
			return serveCustomer(time, s.get(), customer);
		}
		s = shop.find(server -> !server.hasWaitingCustomer());
		if (s.isPresent()) {
			SimState newSim = noteWait(time, s.get(), customer);
			Shop newShop = newSim.shop.replace(s.get().askToWait(customer));
			return new SimState(newSim.events, newSim.stats, newShop, newSim.logs); 
		}
		return noteLeave(time, customer);
	}

	/**
	 * Simulate the logic of what happened when a customer is done being
	 * served. The server either serve the next customer or becomes idle.
	 * @param time The time the service is done.
	 * @param server The server serving the customer.
	 * @param customer The customer being served.
	 * @return A new state of the simulation.
	 */
	public SimState simulateDone(double time, Server server, Customer customer) {
		Optional<Server> server_ = this.shop.find(s -> s.getId() == server.getId());
		Optional<Customer> c = server_.get().getWaitingCustomer();
		SimState newSim = noteDone(time, server, customer);
		Shop newShop;
		if (c.isPresent()) {
			return newSim.serveCustomer(time, server, c.get());
		} else {
			return new SimState(
				newSim.events,
				newSim.stats,
				newSim.shop.replace(server_.get().makeIdle()),
				newSim.logs
			);
		}
	}

	/**
	 * Handle the logic of server serving customer.A new done event
	 * is generated and scheduled.
	 * @paramtimThe time this customer is served.
	 * @paramserver The server serving this customer.
	 * @paramcustomer The customer being served.
	 * @return A new state of the simulation.
	 */
	public SimState serveCustomer(double time, Server server, Customer customer) {
		Optional<Server> s_ = shop.find(s -> s.getId() == server.getId());
		double doneTime = time + Simulation.SERVICE_TIME;
		Server s = s_.get().serve(customer);
		SimState newSim = noteServed(time, server, customer);
		return new SimState(newSim.events, newSim.stats, newSim.shop.replace(s), newSim.logs)
			   .addEvent(doneTime, state -> state.simulateDone(doneTime, s, customer));
	}

	/**
	 * The main simulation loop. Repeatedly get events from the event
	 * queue, simulate and update the event. Return the final simulation
	 * state.
	 * @return The final state of the simulation.
	 */
    public SimState run() {
    	if (!this.events.poll().first.isPresent()) return this;
		SimState state;
		List<SimState> states = Stream.iterate(
				this.nextEvent().first.map(x -> x.simulate(this.nextEvent().second)).get(),
				p -> p.nextEvent().first.map(x -> x.simulate(p.nextEvent().second)).get())
			.takeWhile(x -> x.nextEvent().first.isPresent())
			.collect(Collectors.toList());
		state = states.get(states.size() - 1);
		return state.nextEvent().first.map(x -> x.simulate(state.nextEvent().second)).get();
	}
	/**
	 * Return a string representation of the simulation state, which
	 * consists of all the logs and the stats.
	 * @return A string representation of the simulation.
	 */
	public String toString() {
		return logs.stream()
				.collect(Collectors.joining(""))
			+ this.stats.toString();
	}
}
