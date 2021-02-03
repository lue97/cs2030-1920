import java.util.Queue;
import java.util.LinkedList;

public class Server {

	/**
	* A constant max number of customers that can be served by a server.
	*/
	private static final int MAX_CUSTOMER = 2;

	/**
	* A counter to keep track of the number of cusomters created.
	*/
	private static int counter = 1;

	/**
	* The id of this server.
	*/
	private final int id;

	/**
	* The queue of customer for this server.
	*/
	private final Queue<Customer> customers;

	/**
	* The current customer this server is serving.
	*/
	private Customer customer;

	/**
	* The completion time for the customer currently being served.
	*/
	private double currentCompletionTime;

	/**
	* Constructs a server.
	*/
	public Server() {
		this.id = Server.counter++;
		this.customers = new LinkedList<Customer>();
		this.currentCompletionTime = 0;
	}

	/**
	* Serve a customer at the front of this server's queue and updates
	* completionTime this server.
	* @param time The time this customer is being served
	*/
	public void serve(double time) {
		this.customer = this.customers.peek();
		this.currentCompletionTime = time + this.customer.getServeTime();
	}

	/**
	* Add a new customer to be served for this server.
	* @param customer The customer to be added
	*/
	public void enqueue(Customer customer) {
		this.customers.add(customer);
	}

	/**
	* Returns the id of this server.
	* @return The id of Server
	*/
	public int getId() {
		return this.id;
	}

	/**
	* Returns the size of the customers queued for this server.
	* @return The size of customer queue
	*/
	public int getLoad() {
		return this.customers.size();
	}

	/**
	* Checks if the server is able to accomodate another customer the queue of
	* this server.
	* @return If the queue is full
	*/
	public boolean isAvailable() {
		return this.customers.size() < Server.MAX_CUSTOMER;
	}

	/**
	* Returns the completion time of the current customer being served by this
	* server.
	* @return The completion time of the current customer
	*/
	public double getCurrentCompletionTime() {
		return this.currentCompletionTime;
	}

	/**
	* Return the current customer being served by this server.
	* @return The current customer
	*/
	public Customer getCurrent() {
		return this.customer;
	}

	/**
	* Removes the current customer from the queue and reset the current
	* customer being served by this server.
	*/
	public void flushCurrent() {
		this.customers.remove();
		this.customer = null;
	}

	@Override
	public String toString() {
		return String.format("Server %d, %d", this.id, this.getLoad());
	}

}
