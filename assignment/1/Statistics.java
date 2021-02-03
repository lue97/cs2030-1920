public class Statistics {

	/**
	* The number of customer served.
	*/
	private int served;

	/**
	* The sum of time taken for waiting by all customers.
	*/
	private double totalWait;

	/**
	* The number of customer who left before being served.
	*/
	private int left;

	/**
	* Constructs a Statistics with 0 value for all variables.
	*/
	public Statistics() {
		this.served = 0;
		this.totalWait = 0;
		this.left = 0;
	}

	/**
	* Increments the total number of customer served.
	* @param served The amount of customers to be incremented
	*/
	public void incServed(int served) {
		this.served += served;
	}

	/**
	* Increments the sum of time taken for waiting by all customers.
	* @param totalWait the amount of time to be incremented
	*/
	public void incWait(double totalWait) {
		this.totalWait += totalWait;
	}

	/**
	* Increments the number of customer who left before being served.
	* @param left the amount of people who left before being served to be incremented
	*/
	public void incLeft(int left) {
		this.left += left;
	}

	@Override
	public String toString() {
		return String.format(
				"[%.3f %d %d]",
				this.totalWait / this.served,
				this.served,
				this.left);
	}
}
