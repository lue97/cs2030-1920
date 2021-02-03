package cs2030.simulator;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;

/**
 * A shop object maintains the list of servers and support queries
 * for server.
 *
 * @author weitsang
 * @author atharvjoshi
 * @author A0200294R
 * @version CS2030 AY19/20 Sem 1 Assignment 2
 */
class Shop {
    /** List of servers. */
    public final List<Server> servers;

    /**
     * Create a new shop with a given number of servers.
     * @param numOfServers The number of servers.
     */
    public Shop(int numOfServers, int numOfSelfCheckout, int maxQueue) {
        this.servers = Stream.iterate(1, i -> i + 1)
                       .map(i -> new HumanServer(i, maxQueue))
                       .limit(numOfServers)
                       .collect(Collectors.toList());
        this.servers.addAll(Stream.iterate(numOfServers + 1, i -> i + 1)
                            .map(i -> new SelfCheckout(i, maxQueue))
                            .limit(numOfSelfCheckout)
                            .collect(Collectors.toList())
        );
    }

    /**
     * Constructor for updated shop.
     * @param servers A list of servers for the shop.
     */
    public Shop(List<Server> servers) {
        this.servers = servers;
    }

    /**
     * Find a server matching the predicate.
     *
     * @param predicate A predicate to match the server with.
     * @return Optional.empty if no server matches the predicate, or the
     *     optional of the server if a matching server is found.
     */
    public Optional<Server> find(Predicate<Server> predicate) {
        return this.servers.stream()
               .filter(predicate)
               .findFirst();
    }

    /**
     * Get the length of the shortest queue in the shop.
     * @return The length of the shortest queue in the shop.
    */
    public int getLowestLoad() {
        return this.servers.stream()
               .mapToInt(server -> server.getLoad())
               .min()
               .getAsInt();
    }

    /**
     * Replace a server in the shop.
     * @return A shop with the old server replaced.
     */
    public Shop replace(Server server) {
        return new Shop(
                   servers.stream()
                   .map(s -> (s.equals(server) ? server : s))
                   .collect(Collectors.toList())
               );
    }

    /**
     * Return a string representation of this shop.
     * @return A string reprensetation of this shop.
     */
    public String toString() {
        return servers.toString();
    }
}
