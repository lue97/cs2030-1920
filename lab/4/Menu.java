import java.util.List;
import java.util.ArrayList;

public class Menu {

	private static int counter = 0;

	private final List<MenuItem> items;
	private final List<String> printOrder;

	public Menu() {
		this.items = new ArrayList<MenuItem>();
		this.printOrder = new ArrayList<String>();
	}

	public MenuItem add(String type, String name, int price) {
		MenuItem item = new Food(Menu.counter++, type, name, price);
		this.items.add(item);
		if (!this.printOrder.contains(type)) {
			this.printOrder.add(type);
		}
		return item;
	}

	public MenuItem add(String type, String name, List<Integer> ids) {
		List<MenuItem> comboItems = new ArrayList<MenuItem>();
		for (int id : ids) {
			comboItems.add(this.getItemById(id));
		}
		MenuItem item = new Combo(Menu.counter++, type, name, comboItems);
		this.items.add(item);
		return item;
	}

	public MenuItem getItemById(int id) {
		for (MenuItem item : this.items) {
			if (item.getId() == id) {
				return item;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		String s = "";
		for (String type : this.printOrder) {
			for (MenuItem item : items) {
				if (item.getType().equals(type)) {
					s += item.toString() + "\n";
				}
			}
		}
		for (MenuItem item : items) {
			if (item.getType().equals("Combo")) {
				s += item.toString() + "\n";
			}
		}
		return s;
	}

	public void print() {
		System.out.print(this.toString());
	}

}
