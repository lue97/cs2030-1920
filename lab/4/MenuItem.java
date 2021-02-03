public abstract class MenuItem {

	public final int id;
	public final String type;
	public final String name;

	public MenuItem(int id, String type, String name) {
		this.id = id;
		this.type = type;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getType() {
		return this.type;
	}

	public abstract int getPrice();

	public String toString() {
		return String.format("#%d %s: %s (%d)", this.id, this.type, this.name, this.getPrice());
	}

}
