public class RubikBack extends Rubik {
	RubikBack(Rubik rubik) { super(rubik); }
	public Rubik left() { return this.backView().left().backView();}
	public Rubik right() { return this.backView().right().backView(); }
	public Rubik half() { return this.backView().half().backView(); }
}
