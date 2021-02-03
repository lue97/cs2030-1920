import java.util.List;
import java.util.ArrayList;

public class Order {

	private final Menu menu;
	private final List<Integer> ids;

	public Order(Menu menu) {
		this.menu = menu;
		this.ids = new ArrayList<Integer>();
	}

	public Order add(int[] ids) {
		for (int id : ids) {
			this.ids.add(id);
		}
		return this;
	}

	public int getTotal() {
		int total = 0;
		for (int id : ids) {
			total += this.menu.getItemById(id).getPrice();
		}
		return total;
	}

	@Override
	public String toString() {
		String s = "\n";
		for (int id : this.ids) {
			s += menu.getItemById(id) + "\n";
		}
		s += String.format("Total: %d", this.getTotal());
		return s;
	}

}
