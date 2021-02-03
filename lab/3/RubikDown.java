public class RubikDown extends Rubik {
	RubikDown(Rubik rubik) { super(rubik); }
	public Rubik left() { return this.downView().left().upView();}
	public Rubik right() { return this.downView().right().upView(); }
	public Rubik half() { return this.downView().half().upView(); }
}
