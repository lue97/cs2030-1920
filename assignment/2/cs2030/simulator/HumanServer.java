package cs2030.simulator;

import java.util.Optional;
import java.util.Queue;
import java.util.LinkedList;


/**
 * The HumanServer class encapsulates information and methods pertaining to
 * human servers.
 *
 * @author A0200294R
 * @version CS2030 AY19/20 Sem 1 Assignment 2
 */
class HumanServer extends Server {

    /** Flag to store if srever is on break. */
    private final boolean onBreak;

    /**
     * Creates a server and initalizes it with a unique id.
     * @param id The {@code id} of this server.
     * @param maxCust The maximum length of this server's queue.
     */
    public HumanServer(int id, int maxCust) {
        super(id, maxCust);
        this.onBreak = false;
    }

    /**
     * Private constructor for a server.
     * @param id The {@code id} of this server.
     * @param maxCust The maximum length of this server's queue.
     * @param currentCustomer The customer that the server is currently serving.
     * @param waitingCustomers The queue of customers waiting to be served by this server.
     * @param onBreak If this server is on break.
     */
    private HumanServer(int id, int maxCust, Optional<Customer> currentCustomer,
                        Queue<Customer> waitingCustomers, boolean onBreak) {
        super(id, maxCust, currentCustomer, waitingCustomers);
        this.onBreak = onBreak;
    }

    /**
     * Change this server's state to idle by removing its current customer.
     * @return A new server with the current customer removed.
     */
    @Override
    public HumanServer makeIdle() {
        return new HumanServer(
                   id,
                   this.maxCust,
                   Optional.empty(),
                   this.waitingCustomers,
                   this.onBreak
               );
    }

    /**
     * Change this server's state to taking a break by changing the {@code onBreak}
     * flag to true.
     * @return A new server on break with the current customer removed.
     */
    public HumanServer takeBreak() {
        return new HumanServer(
                   id,
                   this.maxCust,
                   Optional.empty(),
                   this.waitingCustomers,
                   true
               );
    }

    /**
     * Change this server's state to not taking a break by changing the {@code onBreak}
     * flag to false.
     * @return A new server not on break.
     */
    public HumanServer clockIn() {
        return new HumanServer(
                   id,
                   this.maxCust,
                   Optional.empty(),
                   this.waitingCustomers,
                   false
               );
    }

    /**
     * Checks if the server is on break.
     * @return true if the server is on break; false otherwise.
    */
    public boolean isOnBreak() {
        return this.onBreak;
    }

    /**
     * Serve a customer.
     * @param customer The customer to be served.
     * @return The new server serving this customer.
     */
    public Server serve(Customer customer) {
        if (this.getWaitingCustomer().filter(c -> c.equals(customer)).isEmpty()) {
            return new HumanServer(
                       id,
                       this.maxCust,
                       Optional.of(customer),
                       this.waitingCustomers,
                       this.onBreak
                   );
        } else {
            Queue<Customer> newQueue = new LinkedList<>(this.waitingCustomers);
            newQueue.remove();
            return new HumanServer(id, this.maxCust, Optional.of(customer), newQueue, this.onBreak);
        }
    }

    /**
     * Make a customer wait for this server.
     * @param customer The customer who will wait for this server.
     * @return The new server with a waiting customer.
     */
    public Server askToWait(Customer customer) {
        Queue<Customer> newQueue = new LinkedList<>(this.waitingCustomers);
        newQueue.add(customer);
        return new HumanServer(id, this.maxCust, this.currentCustomer, newQueue, this.onBreak);
    }

    /**
     * Return a string representation of this customer.
     * @return A string containing the {@code id} of the server prefixed with
     *     the type of srever.
     */
    @Override
    public String toString() {
        return String.format("server %d", this.id);
    }
}
