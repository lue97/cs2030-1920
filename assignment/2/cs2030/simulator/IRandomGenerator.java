package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;


/**
 * An immutable random number generator that is a wrapper around the RandomGenerator class. 
 */
class IRandomGenerator {

	/** The method call history of this generator. */
	private final List<Integer> history;

	/** The random generator being wrapped */
	private final RandomGenerator generator;

	/** The seed for this generator. */
	private final int seed_;

	/** The lambda value for this generator. */
	private final double lambda_;

	/** The mu value for this generator. */
	private final double mu_;

	/** The rho value for this generator. */
	private final double rho_;

	/**
	 * Create a new IRandomGenerator from scratch.
	 * @param seed seed value for the generator.
	 * @param lambda lambda value for the generator.
	 * @param mu mu value for the generator.
	 * @param rho rho value for the generator.
	 */
	public IRandomGenerator(int seed, double lambda, double mu, double rho) {
		this.generator  = new RandomGenerator(seed, lambda, mu, rho);
		this.seed_ = seed;
		this.lambda_ = lambda;
		this.mu_ = mu;
		this.rho_ = rho;
		this.history = new ArrayList<>();
	}

	/**
	 * Clone an IRandomGenerator from another IRandomGenerator.
	 * @param iGenerator the generator to be cloned.
	 */
	public IRandomGenerator(IRandomGenerator iGenerator) {
		this(iGenerator.seed_, iGenerator.lambda_, iGenerator.mu_, iGenerator.rho_);
		this.history.addAll(iGenerator.history);
		this.replay();
	}

	/**
	 * Performs all operations in the history on this generator.
	 */
	private void replay() {
		this.history.stream().forEach(x -> {
			switch(x) {
				case 0: this.generator.genCustomerType();
				break;
				case 1: this.generator.genInterArrivalTime();
				break;
				case 2: this.generator.genRandomRest();
				break;
				case 3: this.generator.genRestPeriod();
				break;
				case 4: this.generator.genServiceTime();
				break;
				default: break;
			}
		});
	}

	/**
	 * Generates the type of customer.
	 * @return A pair containing the type of customer and the new Random generator
	 */
	public Pair<Double, IRandomGenerator> genCustomerType() {
		IRandomGenerator iGenerator = new IRandomGenerator(this);
		iGenerator.history.add(0);
		double rv = iGenerator.generator.genCustomerType();
		return Pair.of(rv, iGenerator);
	}

	/**
	 * Generates the arrival time of customers.
	 * @return A pair containing the arrival time and the new Random generator
	 */
	public Pair<Double, IRandomGenerator> genInterArrivalTime() {
		IRandomGenerator iGenerator = new IRandomGenerator(this);
		iGenerator.history.add(1);
		double rv = iGenerator.generator.genInterArrivalTime();
		return Pair.of(rv, iGenerator);
	}

	/**
	 * Generates the rest value of servers.
	 * @return A pair containing the rest value and the new Random generator
	 */
	public Pair<Double, IRandomGenerator> genRandomRest() {
		IRandomGenerator iGenerator = new IRandomGenerator(this);
		iGenerator.history.add(2);
		double rv = iGenerator.generator.genRandomRest();
		return Pair.of(rv, iGenerator);
	}

	/**
	 * Generates the rest period of servers.
	 * @return A pair containing the type of customer and the new Random generator
	 */
	public Pair<Double, IRandomGenerator> genRestPeriod() {
		IRandomGenerator iGenerator = new IRandomGenerator(this);
		iGenerator.history.add(3);
		double rv = iGenerator.generator.genRestPeriod();
		return Pair.of(rv, iGenerator);
	}


	/**
	 * Generates the service time of customers.
	 * @return A pair containing the service time of customer and the new Random generator
	 */
	public Pair<Double, IRandomGenerator> genServiceTime() {
		IRandomGenerator iGenerator = new IRandomGenerator(this);
		iGenerator.history.add(4);
		double rv = iGenerator.generator.genServiceTime();
		return Pair.of(rv, iGenerator);
	}
}
