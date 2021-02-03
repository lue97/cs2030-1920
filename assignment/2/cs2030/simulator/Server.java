package cs2030.simulator;

import java.util.Optional;
import java.util.Queue;
import java.util.LinkedList;

/**
 * The Server class keeps track of who is the customer being served (if any)
 * and who is the customer waiting to be served (if any).
 *
 * @author A0200294R
 * @version CS2030 AY19/20 Sem 1 Assignment 2
 */
public abstract class Server {
    /** The unique ID of this server. */
    protected final int id;

    /** The customer currently being served, if any. */
    protected Optional<Customer> currentCustomer;

    /** The customer currently waiting, if any. */
    protected Queue<Customer> waitingCustomers;

    /** The maximum size of the server's queue. */
    protected final int maxCust;

    /**
     * Creates a server and initalizes it with a unique id.
     * @param id The unique {@code id} for this server.
     * @param maxCust The maximum size of the customer queue for this server.
     */
    public Server(int id, int maxCust) {
        this.currentCustomer = Optional.empty();
        this.waitingCustomers = new LinkedList<Customer>();
        this.id = id;
        this.maxCust = maxCust;
    }

    /**
     * Private constructor for a server.
     */
    protected Server(int id, int maxCust, Optional<Customer> currentCustomer,
                     Queue<Customer> waitingCustomers) {
        this.id = id;
        this.maxCust = maxCust;
        this.currentCustomer = currentCustomer;
        this.waitingCustomers = waitingCustomers;
    }

    /**
     * Checks if the server is idle.
     * @return true if the server is idle (no current customer); false otherwise.
     */
    public boolean isIdle() {
        return !this.currentCustomer.isPresent();
    }

    /**
     * Checks if the server's queue is full.
     * @return true if the queue is full; false otherwise.
    */
    public boolean isFull() {
        return this.waitingCustomers.size() >= maxCust;
    }

    /**
     * Get the number of customers currently in the server's queue.
     * @return the size of the server's queue.
    */
    public int getLoad() {
        return this.waitingCustomers.size();
    }

    /**
     * Checks if there is a customer waiting for given server.
     * @return true if a customer is waiting for given server; false otherwise.
     */
    public boolean hasWaitingCustomer() {
        return this.waitingCustomers.size() > 0;
    }

    /**
     * Returns waiting customer for given server.
     * @return customer waiting for given server.
     */
    public Optional<Customer> getWaitingCustomer() {
        return Optional.ofNullable(this.waitingCustomers.peek());
    }

    /**
     * Serve a customer.
     * @param customer The customer to be served.
     * @return The new server serving this customer.
     */
    public abstract Server serve(Customer customer);

    /**
     * Make a customer wait for this server.
     * @param customer The customer who will wait for this server.
     * @return The new server with a waiting customer.
     */
    public abstract Server askToWait(Customer customer);

    /**
     * Change this server's state to idle by removing its current customer.
     * @return A new server with the current customer removed.
     */
    public abstract Server makeIdle();

    /**
     * Return a string representation of this server.
     * @return A string
     */
    public String toString() {
        return String.format("server %d", this.id);
    }

    /**
     * Checks if two servers have the same id.
     * @param obj Another objects to compared against.
     * @return true if obj is a server with the same id; false otherwise.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Server)) {
            return false;
        }
        return (this.id == ((Server)obj).id);
    }

    /**
     * Return the hashcode for this server.
     * @return the ID of this server as its hashcode.
     */
    public int hashCode() {
        return this.id;
    }
}
