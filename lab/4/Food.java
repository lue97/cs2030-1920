public class Food extends MenuItem {

	private final int price;

	public Food(int id, String type, String name, int price) {
		super(id, type, name);
		this.price = price;
	}

	public int getPrice() {
		return this.price;
	}

	@Override
	public String toString() {
		return String.format("#%d %s: %s (%d)", this.id, this.type, this.name, this.price);
	}

}
