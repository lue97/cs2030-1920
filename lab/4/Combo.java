import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Combo extends MenuItem {
	
	private final List<MenuItem> comboItems;

	public Combo(int id, String type, String name, List<MenuItem> comboItems) {
		super(id, type, name);
		this.comboItems = comboItems;
	}

	@Override
	public int getPrice() {
		int total = 0;
		for (MenuItem comboItem : this.comboItems) {
			total += comboItem.getPrice();
		}
		return total - 50;
	}

	@Override
	public String toString() {
		String s = super.toString() + "\n";
		for (MenuItem comboItem : this.comboItems) {
			s += String.format("   %s\n", comboItem.toString());
		}
		return s.substring(0, s.length() - 1);
	}

}
