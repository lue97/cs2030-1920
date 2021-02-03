import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
		String tok;
		int count;
		Rubik cube;
    	Scanner s = new Scanner(System.in);
		int[][][] grid = new int[6][3][3];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					grid[i][j][k] = s.nextInt();
				}
			}
		}
		cube = new Rubik(grid);
		while(s.hasNext()) {
			tok = s.next();
			if (tok.length() == 1) {
				switch(tok.charAt(0)) {
					case 'F': cube = new Rubik(cube).right();
					break;
					case 'R': cube = new RubikRight(cube).right();
					break;
					case 'U': cube = new RubikUp(cube).right();
					break;
					case 'L': cube = new RubikLeft(cube).right();
					break;
					case 'B': cube = new RubikBack(cube).right();
					break;
					case 'D': cube = new RubikDown(cube).right();
				}
			} else if (tok.charAt(1) == '\'') {
				switch(tok.charAt(0)) {
					case 'F': cube = new Rubik(cube).left();
					break;
					case 'R': cube = new RubikRight(cube).left();
					break;
					case 'U': cube = new RubikUp(cube).left();
					break;
					case 'L': cube = new RubikLeft(cube).left();
					break;
					case 'B': cube = new RubikBack(cube).left();
					break;
					case 'D': cube = new RubikDown(cube).left();
				}
			} else {
				switch(tok.charAt(0)) {
					case 'F': cube = new Rubik(cube).half();
					break;
					case 'R': cube = new RubikRight(cube).half();
					break;
					case 'U': cube = new RubikUp(cube).half();
					break;
					case 'L': cube = new RubikLeft(cube).half();
					break;
					case 'B': cube = new RubikBack(cube).half();
					break;
					case 'D': cube = new RubikDown(cube).half();
				}
			}
		}
		System.out.println(cube.toString());
	}
}
