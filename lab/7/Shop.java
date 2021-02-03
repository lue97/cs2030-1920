import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * A shop object maintains the list of servers and support queries
 * for server.
 *
 * @author weitsang
 * @author atharvjoshi
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
class Shop {
	/** List of servers. */
	private final List<Server> servers;

	/**
	 * Create a new shop with a given number of servers.
	 * @param numOfServers The number of servers.
	 */
	Shop(int numOfServers) {
		this.servers = IntStream.rangeClosed(1, numOfServers).mapToObj(x -> new Server(x)).collect(Collectors.toList());
	}

	Shop(List<Server> servers) {
		this.servers = new ArrayList<Server>(servers);
	}

	List<Server> getServers() {
		return this.servers;
	}

	public <T, R> Optional<Server> find(Predicate<Server> predicate) {
		List<Server> servers_ = this.servers.stream().filter(predicate).collect(Collectors.toList());
		if (servers_.size() <= 0) return Optional.empty();
		return Optional.of(servers_.get(0));
	}

	public Shop replace(Server server_) {
		List<Server> servers_ = this.servers.stream()
								.map(s -> s.getId() == server_.getId() ? server_ : s)
								.collect(Collectors.toList());
		return new Shop(servers_);
	}

	/**
	 * Return a string representation of this shop.
	 * @return A string reprensetation of this shop.
	 */
	public String toString() {
		return servers.toString();
	}
}
