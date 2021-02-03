package cs2030.simulator;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class encapsulates all the simulation states.  There are four main
 * components: (i) the event queue, (ii) the statistics, (iii) the shop
 * (the servers) and (iv) the event logs.
 *
 * @author weitsang
 * @author atharvjoshi
 * @author A0200294R
 * @version CS2030 AY19/20 Sem 1 Assignment 2
 */
public class SimState {
    
	/**
     * The Event class encapsulates information and methods pertaining to a
     * Simulator event. Stores a lambda that denotes type of event - arrival or
     * done.
     */
    private class Event implements Comparable<Event> {

        /** The time this event occurs at. */
        private double time;

        /** A function that this event will execute. */
        private Function<SimState, SimState> lambda;

        /**
         * Creates an event and initializes it.
         * @param time The time of occurrence.
         * @param f The function that this event will execute.
         */
        public Event(double time, Function<SimState, SimState> f) {
            this.time = time;
            this.lambda = f;
        }

        /**
         * Defines natural ordering of events by their time.
         * Events ordered in ascending order of their timestamps.
         *
         * @param other Another event to compare against.
         * @return 0 if two events occur at same time, a positive number if
         *     this event has later than other event, a negative number otherwise.
         */
        public int compareTo(Event other) {
            return (int)Math.signum(this.time - other.time);
        }

        /**
         * Smulates this event by applying the lambda.
         *
         * @param state The current simulation state.
         * @return A new state of simulation.
         */
        public SimState simulate(SimState state) {
            return this.lambda.apply(state);
        }
    }

    /** The priority queue of events. */
    private PriorityQueue<Event> events;

    /** The statistics maintained. */
    private final Statistics stats;

    /** The shop of servers. */
    private final Shop shop;

    /** The event logs. */
    private final String log;

    /** The customer id. */
    private final int lastCustomerId;

    /** The probability of human servers resting. */
    private final double pRest;

    /** The probability encountering a greedy customer. */
    private final double pGreedy;

    /** The random number generator for the SimState. */
    private final IRandomGenerator rng;

    /** Creates a SimState from input given from entry point. */
    public static SimState init(int seed, int numServer, int numSelfCheckout, int maxQueue,
                                int numCust, double arrivalRate, double serviceRate,
                                double restRate, double pRest, double pGreedy) {
        IRandomGenerator rng = new IRandomGenerator(seed, arrivalRate, serviceRate, restRate);
        SimState simState = new SimState(numServer, numSelfCheckout, maxQueue, pRest, pGreedy, rng);
        double timeArrival = 0;
		Pair<Double, IRandomGenerator> pair;
        for (int i = 0; i < numCust; i++) {
            final double time_ = timeArrival;
            simState = simState.addEvent(timeArrival, s -> s.simulateArrival(time_));
			pair = simState.rng.genInterArrivalTime();
            timeArrival += pair.first;
			simState = simState.generator(pair.second);
        }
        return simState;
    }

    /**
     * A private constructor of internal states.
     * @param shop The list of servers.
     * @param stats The statistics being kept.
     * @param events A priority queue of events.
     * @param log A log of what's happened so far.
     */
    private SimState(Shop shop, Statistics stats, PriorityQueue<Event> events, String log,
                     int lastCustomerId, double pRest, double pGreedy, IRandomGenerator generator) {
        this.shop = shop;
        this.stats = stats;
        this.events = events;
        this.log = log;
        this.lastCustomerId = lastCustomerId;
        this.pRest = pRest;
        this.pGreedy = pGreedy;
		this.rng = generator;
    }

    /**
     * A constructor that generates a new SimState from scratch.
     */
    public SimState(int numServer, int numSelfCheckout, int maxQueue, double pRest,
                    double pGreedy, IRandomGenerator generator) {
        this(
            new Shop(numServer, numSelfCheckout, maxQueue),
            new Statistics(),
            new PriorityQueue<Event>(),
            "",
            1,
            pRest,
            pGreedy,
			generator
        );
    }

    /**
     * Update the random generator of this simulation.
     * @param  generator The updated generator to replace the existing one.
     * @return The new simulation state.
     */
    private SimState generator(IRandomGenerator generator) {
        return new SimState(this.shop, stats, this.events, this.log, this.lastCustomerId,
                            this.pRest, this.pGreedy, generator);
    }

    /**
     * Update the statistics of this simulation.
     * @param  stats The updated statistics to replace the existing one.
     * @return The new simulation state.
     */
    private SimState stats(Statistics stats) {
        return new SimState(this.shop, stats, this.events, this.log, this.lastCustomerId,
                            this.pRest, this.pGreedy, this.rng);
    }

    /**
     * Update a server of this simulations.
     * @param  s The updated server to replace the existing one.
     * @return The new simulation state.
     */
    private SimState server(Server s) {
        return new SimState(shop.replace(s), this.stats, this.events, this.log,
                            this.lastCustomerId, this.pRest, this.pGreedy, this.rng);
    }

    /**
     * Update the event queue of this simulations.
     * @param  pq The priority queue to replace the existing one.
     * @return The new simulation state.
     */
    private SimState events(PriorityQueue<Event> pq) {
        return new SimState(this.shop, this.stats, pq, this.log, this.lastCustomerId,
                            this.pRest, this.pGreedy, this.rng);
    }

    /**
     * Update the event log of this simulations.
     * @param  s The log string to append to this event log.
     * @return The new simulation state.
     */
    private SimState log(String s) {
        return new SimState(this.shop, this.stats, this.events, this.log + s, this.lastCustomerId,
                            this.pRest, this.pGreedy, this.rng);
    }

    /**
     * Update the event log of this simulations.
     * @param  id The server id
     * @return The new simulation state.
     */
    private SimState id(int id) {
        return new SimState(this.shop, this.stats, this.events, this.log, id, this.pRest,
                            this.pGreedy, this.rng);
    }

    /**
     * Add an event to the simulation's event queue.
     * @param time The time the event to be added occur.
     * @param lambda How the state to be updated upon execution of this event.
     * @return The new simulation state.
     */
    public SimState addEvent(double time, Function<SimState, SimState> lambda) {
        return events(events.add(new Event(time, lambda)));
    }

    /**
     * Retrieve the next event with earliest time stamp from the
     * priority queue, and a new state.  If there is no more event, an
     * Optional.empty will be returned.
     * @return A pair object with an (optional) event and the new simulation
     *     state.
     */
    private Pair<Optional<Event>, SimState> nextEvent() {
        Pair<Optional<Event>, PriorityQueue<Event>> result = this.events.poll();
        return Pair.of(result.first, events(result.second));
    }

    /**
     * Called when a customer arrived in the simulation.
     * @param time The time the customer arrives.
     * @param c The customer that arrrives.
     * @return A new state of the simulation after the customer arrives.
     */
    public SimState noteArrival(double time, Customer c) {
        return log(String.format("%.3f %s arrives\n", time, c));
    }

    /**
     * Called when a customer arrived in the simulation.  This methods update
     * the logs of simulation.
     * @param time The time the customer arrives.
     * @param c The customer that arrrives.
     * @return A new state of the simulation after the customer arrives.
     */
    public SimState noteWait(double time, Server s, Customer c) {
        return log(String.format("%.3f %s waits to be served by %s\n", time, c, s));
    }

    /**
     * Called when a customer is served in the simulation.  This methods
     * update the logs and the statistics of the simulation.
     * @param time The time the customer arrives.
     * @param s The server that serves the customer.
     * @param c The customer that is served.
     * @return A new state of the simulation after the customer is served.
     */
    public SimState noteServed(double time, Server s, Customer c) {
        return log(String.format("%.3f %s served by %s\n", time, c, s))
               .stats(stats
                      .serveOneCustomer()
                      .recordWaitingTime(c.timeWaited(time)));
    }

    /**
     * Called when a customer is done being served in the simulation.
     * This methods update the logs of the simulation.
     * @param time The time the customer arrives.
     * @param s The server that serves the customer.
     * @param c The customer that is served.
     * @return A new state of the simulation after the customer is done being
     *     served.
     */
    public SimState noteDone(double time, Server s, Customer c) {
        return log(String.format("%.3f %s done serving by %s\n", time, c, s));
    }

    /**
     * Called when a customer leaves the shops without service.
     * Update the log and statistics.
     * @param time  The time this customer leaves.
     * @param customer The customer who leaves.
     * @return A new state of the simulation.
     */
    public SimState noteLeave(double time, Customer customer) {
        return log(String.format("%.3f %s leaves\n", time, customer))
               .stats(stats.looseOneCustomer());
    }

    /**
     * Simulates the logic of what happened when a customer arrives.
     * The customer is either served, waiting to be served, or leaves.
     * @param time The time the customer arrives.
     * @return A new state of the simulation.
     */
    public SimState simulateArrival(double time) {
		Pair<Double, IRandomGenerator> pair = this.rng.genCustomerType();
        Customer customer = new Customer(time, this.lastCustomerId,
                                         pair.first < this.pGreedy);
        return generator(pair.second).noteArrival(time, customer).id(this.lastCustomerId + 1)
               .processArrival(time, customer);
    }

    /**
     * Handle the logic of finding idle servers to serve the customer,
     * or a server that the customer can wait for, or leave.  Called
     * from simulateArrival.
     * @param time The time the customer arrives.
     * @param customer The customer to be served.
     * @return A new state of the simulation.
     */
    private SimState processArrival(double time, Customer customer) {
        return shop.find(
            server -> (server.isIdle()) &&
            !(server instanceof HumanServer && ((HumanServer) server).isOnBreak())
        )
        .map(server -> serveCustomer(time, server, customer))
        .or(() -> {
            if (customer.isGreedy()) {
                return this.shop.find(
                    server_ -> (server_.getLoad() == this.shop.getLowestLoad()) &&
                    !server_.isFull()
                )
                .map(server_ -> noteWait(time, server_, customer)
                .server(server_.askToWait(customer)));
            } else {
                return this.shop
                .find(server_ -> !server_.isFull())
                .map(server_ -> noteWait(time, server_, customer)
                	.server(server_.askToWait(customer)));
            }
        })
        .orElseGet(() -> noteLeave(time, customer));
    }

    /**
     * Simulates the logic of what happened when a customer is done being
     * served.  The server either serve the next customer or becomes idle.
     * @param time The time the service is done.
     * @param server The server serving the customer.
     * @param customer The customer being served.String.format("%.3f %s leaves\n", time, customer)
     * @return A new state of the simulation.
     */
    public SimState simulateDone(double time, Server server, Customer customer) {
        Server server_ = this.shop.find(s -> s.equals(server)).get();
        Pair<Double, IRandomGenerator> pair;
        if (server_ instanceof HumanServer) {
        	pair = this.rng.genRandomRest();
        	if (pair.first < this.pRest) {
				return generator(pair.second).noteDone(time, server_, customer).simulateBreak(time, server_);
        	}
        } else {
        	pair = Pair.of(0.0, this.rng);
        }
        return
            this.shop.find(s -> s.equals(server))
            .flatMap(s -> s.getWaitingCustomer())
            .map(c -> generator(pair.second).noteDone(time, server_, customer).serveCustomer(time, server_, c))
            .orElseGet(() -> generator(pair.second).noteDone(time, server_, customer).server(server_.makeIdle()));
    }

    /**
     * Handle the logic of server serving customer. A new done event
     * is generated and scheduled.
     * @param time  The time this customer is served.
     * @param server The server serving this customer.
     * @param customer The customer being served.
     * @return A new state of the simulation.
     */
    public SimState serveCustomer(double time, Server server, Customer customer) {
		Pair<Double, IRandomGenerator> pair = this.rng.genServiceTime();
        double timeDone = time + pair.first;
        Server server_ = this.shop.find(s -> s.equals(server)).get();
        return server(server_.serve(customer))
                .generator(pair.second)
                .noteServed(time, server_, customer)
                .addEvent(timeDone, state -> state.simulateDone(timeDone, server_, customer));
    }

    /**
     * Handles the logic of server taking a break. A new break event is
     * generated and scheduled.
     * @param time The time this server takes a break.
     * @param server The server taking a break.
     * @return A new stat of the simulation.
    */
    public SimState simulateBreak(double time, Server server) {
		Pair<Double, IRandomGenerator> pair = this.rng.genRestPeriod();
        HumanServer server_ = (HumanServer) this.shop.find(s -> s.equals(server)).get();
        double timeClockIn = time + pair.first;
        return server(server_.takeBreak())
				.generator(pair.second)
                .addEvent(timeClockIn, state -> state.simulateClockIn(timeClockIn, server_));
    }

    /**
     * Handles the logic of server returning from a break. A new clock in
     * event is generated and scheduled.
     * @param time The time this server takes a break.
     * @param server The server taking a break.
     * @return A new stat of the simulation.
    */
    public SimState simulateClockIn(double time, Server server) {
        HumanServer server_ = (HumanServer) this.shop.find(s -> s.equals(server)).get();
        return server_.getWaitingCustomer()
               .map(c -> server(server_.clockIn()).serveCustomer(time, server_, c))
               .orElseGet(() -> server(server_.clockIn()));
    }

    /**
     * The main simulation loop.  Repeatedly get events from the event
     * queue, simulate and update the event.  Return the final simulation
     * state.
     * @return The final state of the simulation.
     */
    public SimState run() {
        Pair<Optional<Event>,SimState> s = Stream.iterate(this.nextEvent(),
            p -> p.first.isPresent(),
            p -> p.first.get().simulate(p.second).nextEvent())
            .reduce((p, q) -> q)
            .orElseThrow();
        return s.first.get().simulate(s.second);
    }

    /**
     * Return a string representation of the simulation state, which
     * consists of all the logs and the stats.
     * @return A string representation of the simulation.
     */
    @Override
    public String toString() {
        return log + stats.toString();
    }
}
