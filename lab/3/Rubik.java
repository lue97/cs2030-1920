class Rubik implements Cloneable, SideViewable {
    private final int[][][] grid;

	public Rubik(Rubik rubik) {
		this.grid = new int[6][3][3];
        for (int i = 0; i < rubik.grid.length; i++) {
			for (int j = 0; j < rubik.grid[i].length; j++) {
				for (int k = 0; k < rubik.grid[i][j].length; k++) {
	            	this.grid[i][j][k] = rubik.grid[i][j][k];
				}
			}
        }
	}

	public Rubik(int[][][] grid) {
        this.grid = new int[6][3][3];
        for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				for (int k = 0; k < grid[i][j].length; k++) {
	            	this.grid[i][j][k] = grid[i][j][k];
				}
			}
        }
    }

	public Rubik left() {
		int[][][] r = {
			new Face(this.grid[0]).toIntArray(),
			new Face(this.grid[1]).toIntArray(),
			new Face(this.grid[2]).left().toIntArray(),
			new Face(this.grid[3]).toIntArray(),
			new Face(this.grid[4]).toIntArray(),
			new Face(this.grid[5]).toIntArray()
		};
		for (int i = 0; i < 3; i++) r[0][2][i] = this.grid[3][i][0];
		for (int i = 0; i < 3; i++) r[1][i][2] = this.grid[0][2][2 - i];
		for (int i = 0; i < 3; i++) r[3][i][0] = this.grid[4][0][2 - i];
		for (int i = 0; i < 3; i++) r[4][0][i] = this.grid[1][i][2];
		return new Rubik(r);
	}

	public Rubik right() {
		int[][][] r = {
			new Face(this.grid[0]).toIntArray(),
			new Face(this.grid[1]).toIntArray(),
			new Face(this.grid[2]).right().toIntArray(),
			new Face(this.grid[3]).toIntArray(),
			new Face(this.grid[4]).toIntArray(),
			new Face(this.grid[5]).toIntArray()
		};
		for (int i = 0; i < 3; i++) r[0][2][i] = this.grid[1][2 - i][2];
		for (int i = 0; i < 3; i++) r[1][i][2] = this.grid[4][0][i];
		for (int i = 0; i < 3; i++) r[3][i][0] = this.grid[0][2][i];
		for (int i = 0; i < 3; i++) r[4][0][i] = this.grid[3][2 - i][0];
		return new Rubik(r);
	}

	public Rubik half() { return this.right().right(); }

	public int[][][] toIntArray() {
		int[][][] r = {
			new Face(this.grid[0]).toIntArray(),
			new Face(this.grid[1]).toIntArray(),
			new Face(this.grid[2]).toIntArray(),
			new Face(this.grid[3]).toIntArray(),
			new Face(this.grid[4]).toIntArray(),
			new Face(this.grid[5]).toIntArray()
		};
		return r;
	}

	@Override
	public Rubik rightView() {
		int[][][] r = {
			new Face(this.grid[0]).right().toIntArray(),
			new Face(this.grid[2]).toIntArray(),
			new Face(this.grid[3]).toIntArray(),
			new Face(this.grid[5]).half().toIntArray(),
			new Face(this.grid[4]).left().toIntArray(),
			new Face(this.grid[1]).half().toIntArray()
		};
		return new Rubik(r);
	}
	
	@Override
	public Rubik leftView() {
		int[][][] r = {
			new Face(this.grid[0]).left().toIntArray(),
			new Face(this.grid[5]).half().toIntArray(),
			new Face(this.grid[1]).toIntArray(),
			new Face(this.grid[2]).toIntArray(),
			new Face(this.grid[4]).right().toIntArray(),
			new Face(this.grid[3]).half().toIntArray()
		};
		return new Rubik(r);
	}

	@Override
	public Rubik upView() {
		int[][][] r = {
			new Face(this.grid[5]).toIntArray(),
			new Face(this.grid[1]).right().toIntArray(),
			new Face(this.grid[0]).toIntArray(),
			new Face(this.grid[3]).left().toIntArray(),
			new Face(this.grid[2]).toIntArray(),
			new Face(this.grid[4]).toIntArray()
		};
		return new Rubik(r);
	}

	@Override
	public Rubik downView() {
		int[][][] r = {
			new Face(this.grid[2]).toIntArray(),
			new Face(this.grid[1]).left().toIntArray(),
			new Face(this.grid[4]).toIntArray(),
			new Face(this.grid[3]).right().toIntArray(),
			new Face(this.grid[5]).toIntArray(),
			new Face(this.grid[0]).toIntArray()
		};
		return new Rubik(r);
	}

	@Override
	public Rubik backView() { return this.leftView().leftView(); }

	@Override
	public Rubik frontView() { return this; }

	@Override
	public Rubik clone() { return new Rubik(this.toIntArray()); }

	@Override
	public String toString() {
		String s = "\n";
		for (int i = 0; i < this.grid[0].length; i++) {
			s += "......";
			for (int j = 0; j < this.grid[0][i].length; j++) {
				s += String.format("%02d", this.grid[0][i][j]);
			}
			s += "......\n";
		}
		for (int i = 0; i < this.grid[1].length; i++) {
			for (int e : this.grid[1][i]) { s += String.format("%02d", e); }
			for (int e : this.grid[2][i]) { s += String.format("%02d", e); }
			for (int e : this.grid[3][i]) { s += String.format("%02d", e); }
			s += "\n";
		}
		for (int i = 0; i < this.grid[4].length; i++) {
			s += "......";
			for (int j = 0; j < this.grid[4][i].length; j++) {
				s += String.format("%02d", this.grid[4][i][j]);
			}
			s += "......\n";
		}	
		for (int i = 0; i < this.grid[5].length; i++) {
			s += "......";
			for (int j = 0; j < this.grid[5][i].length; j++) {
				s += String.format("%02d", this.grid[5][i][j]);
			}
			s += "......\n";
		}
		return s;
	}

}
