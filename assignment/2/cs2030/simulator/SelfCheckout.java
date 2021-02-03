package cs2030.simulator;

import java.util.Optional;
import java.util.Queue;
import java.util.LinkedList;


/**
 * The SelfCheckout class encapsulates information and methods pertaining to a
 * human self-check servers.
 *
 * @author A0200294R
 * @version CS2030 AY19/20 Sem 1 Assignment 2
 */
class SelfCheckout extends Server {

    /** A shared queue for self-check servers. */
    private static Queue<Customer> sharedQueue = new LinkedList<>();

    /**
     * Creates a server and initalizes it with a unique id.
     * @param id The {@code id} of this server.
     * @param maxCust The maximum length of this server's queue.
     */
    public SelfCheckout(int id, int maxCust) {
        super(id, maxCust);
        this.waitingCustomers = sharedQueue;
    }

    /**
     * Private constructor for a server.
     * @param id The {@code id} of this server.
     * @param maxCust The maximum length of this server's queue.
     * @param currentCustomer The customer that the server is currently serving.
     */
    private SelfCheckout(int id, int maxCust, Optional<Customer> currentCustomer) {
        super(id, maxCust, currentCustomer, SelfCheckout.sharedQueue);
    }

    /**
     * Change this server's state to idle by removing its current customer.
     * @return A new server with the current customer removed.
     */
    @Override
    public SelfCheckout makeIdle() {
        return new SelfCheckout(
                   id,
                   this.maxCust,
                   Optional.empty()
               );
    }

    /**
     * Serve a customer.
     * @param customer The customer to be served.
     * @return The new server serving this customer.
     */
    public Server serve(Customer customer) {
        if (!this.getWaitingCustomer().filter(c -> c.equals(customer)).isEmpty()) {
            SelfCheckout.sharedQueue.remove();
        }
        return new SelfCheckout(this.id, this.maxCust, Optional.of(customer));
    }

    /**
     * Make a customer wait for this server.
     * @param customer The customer who will wait for this server.
     * @return The new server with a waiting customer.
     */
    public Server askToWait(Customer customer) {
        SelfCheckout.sharedQueue.add(customer);
        return this;
    }

    /**
     * Return a string representation of this customer.
     * @return the {@code id} of the server prefixed with the type of srever.
     */
    @Override
    public String toString() {
        return String.format("self-check %d", this.id);
    }
}
