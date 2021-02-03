public class RubikRight extends Rubik {
	RubikRight(Rubik rubik) { super(rubik); }
	public Rubik left() { return this.rightView().left().leftView();}
	public Rubik right() { return this.rightView().right().leftView(); }
	public Rubik half() { return this.rightView().half().leftView(); }
}
