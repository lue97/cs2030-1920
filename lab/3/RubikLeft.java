public class RubikLeft extends Rubik {
	RubikLeft(Rubik rubik) { super(rubik); }
	public Rubik left() { return this.leftView().left().rightView();}
	public Rubik right() { return this.leftView().right().rightView(); }
	public Rubik half() { return this.leftView().half().rightView(); }
}
