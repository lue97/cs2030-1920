public class RubikUp extends Rubik {
	RubikUp(Rubik rubik) { super(rubik); }
	public Rubik left() { return this.upView().left().downView();}
	public Rubik right() { return this.upView().right().downView(); }
	public Rubik half() { return this.upView().half().downView(); }
}
