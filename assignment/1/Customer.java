/**
* A Customer class meant for simulating customers.
*/
public class Customer {

	/**
	* A constant time taken for customers to be served.
	*/
	private static double SERVE_TIME = 1.0;

	/**
	* A counter to keep track of the number of customers created.
	*/
	private static int counter = 1;

	/**
	* The id of this customer.
	*/
	private final int id;

	/**
	* The time taken to serve this customer.
	*/
	private final double serveTime;
	
	/**
	* The arrival time of this customer.
	*/
	private final double arrival;

	/**
	* Constructs a customer with the specified arrival time.
	* @param arrival The arrival time of the customer
	*/
	public Customer(Double arrival) {
		this.id = Customer.counter++;
		this.arrival = arrival;
		this.serveTime = Customer.SERVE_TIME;
	}

	/**
	* Returns the id of this customer.
	* @return int id of Customer
	*/
	public int getId() {
		return this.id;
	}

	/**
	* Returns the arrival Time of this customer.
	* @return double arrival of Customer
	*/
	public double getArrival() {
		return this.arrival;
	}

	/**
	* Returns the time taken to server this customer.
	* @return double serveTime of Customer
	*/
	public double getServeTime() {
		return this.serveTime;
	}

	@Override
	public String toString() {
		return String.format("Customer %d", this.id);
	}
}
