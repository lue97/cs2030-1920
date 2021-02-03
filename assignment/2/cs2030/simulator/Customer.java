package cs2030.simulator;

/**
 * The Customer class encapsulates information and methods pertaining to a
 * Customer in a simulation.
 *
 * @author A0200294R
 * @version CS2030 AY19/20 Sem 1 Assignment 2
 */
class Customer {
    /** The unique ID of this customer. */
    private final int id;

    /** The type of customer .*/
    private final boolean greedy;

    /** The time this customer arrives. */
    private double timeArrived;

    /**
     * Create and initalize a new customer.
     * The {@code id} of the customer is set.
     *
     * @param timeArrived The time this customer arrived in the simulation.
     * @param id The id of the customer.
     * @param greedy The type of customer.
     */
    public Customer(double timeArrived, int id, boolean greedy) {
        this.timeArrived = timeArrived;
        this.id = id;
        this.greedy = greedy;
    }

    /**
     * Return the waiting time of this customer.
     * @param t The current time
     * @return The waiting time of this customer.
     */
    public double timeWaited(double t) {
        return t - timeArrived;
    }

    /**
     * Return the type of customer.
     * @return true if the customer is greedy; false otherwise.
     */
    public boolean isGreedy() {
        return this.greedy;
    }

    /**
     * Return a string representation of this customer.
     * @return the id of the customer postfixed with the type of customer.
     */
    @Override
    public String toString() {
        return String.format("%d%s", this.id, this.greedy ? "(greedy)" : "");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Customer) {
            Customer c = (Customer) obj;
            return c.id == this.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
