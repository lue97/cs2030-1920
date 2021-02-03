public class Dice implements SideViewable {
	private char[] faces;
	public Dice() {
		this.faces = new char[]{'U', 'F', 'R', 'B', 'L', 'D'};
	}

	public Dice(char[] faces) {
		this.faces = new char[6];
		for (int i = 0; i < 6; i++) {
			this.faces[i] = faces[i];
		}
	}
	@Override
	public Dice upView() {
		char[] d = {
			this.faces[3], this.faces[0], this.faces[2],
			this.faces[5], this.faces[4], this.faces[1]
		};
		return new Dice(d);
	}
	@Override
	public Dice frontView() { return this; }

	@Override
	public Dice rightView() {
		char[] d = {
			this.faces[0], this.faces[2], this.faces[3],
			this.faces[4], this.faces[1], this.faces[5]
		};
		return new Dice(d);
	}
	@Override
	public Dice backView() {
		return this.rightView().rightView();
	}
	@Override
	public Dice leftView() {
		char[] d = {
			this.faces[0], this.faces[4], this.faces[1],
			this.faces[2], this.faces[3], this.faces[5]
		};
		return new Dice(d);
	}
	@Override
	public Dice downView() {
		char[] d = {
			this.faces[1], this.faces[5], this.faces[2],
			this.faces[0], this.faces[4], this.faces[3]
		};
		return new Dice(d);
	}

	@Override
	public String toString() {
		return String.format("\n%c\n%c%c%c%c\n   %c",
			this.faces[0], this.faces[1], this.faces[2],
			this.faces[3], this.faces[4], this.faces[5]
		);
	}
}
