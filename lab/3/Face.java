class Face implements Cloneable {
    private final int[][] elements;

    public Face(int[][] elements) {
        this.elements = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.elements[i][j] = elements[i][j];
            }
        }
    }

    Face right() {
        int[][] elements = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                elements[j][2 - i] = this.elements[i][j];
            }
        }
        return new Face(elements);
    }

    Face left() {
        int[][] elements = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                elements[2 - j][i] = this.elements[i][j];
            }
        }
        return new Face(elements);
    }

    Face half() {
        return this.left().left();
    }

    public int[][] toIntArray() {
        int[][] e = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                e[i][j] = this.elements[i][j];
            }
        }
        return e;
    }

    @Override
    public Face clone() {
        int[][] elements = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                elements[i] = this.elements[i];
            } 
        }
        return new Face(elements);
    }

    @Override
    public String toString() {
        String s = "\n";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                s += String.format("%02d", this.elements[i][j]);
            }
            s += "\n";
        }
        return s;
    }
}
