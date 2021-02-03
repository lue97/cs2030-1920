import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		Point p1 = new Point(s.nextDouble(), s.nextDouble());
		Point p2 = new Point(s.nextDouble(), s.nextDouble());
		Circle c = createCircle(p1, p2, s.nextDouble());
		if (c == null) {
			System.out.println("No valid circle can be created");
		} else {
			System.out.printf("Created: %s\n", c.toString());
		}
	}

	public static Circle createCircle(Point p1, Point p2, double r) {
		if (p1.getX() == p2.getX() && p1.getY() == p2.getY()) return null;
		Point mid = p1.midPoint(p2);
		// angle of line pq wrt x-axis
		double angle = p1.angleTo(p2) + (Math.PI / 2);
		double pm = p1.distanceTo(mid);
		if (pm > r) return null;
		double mc = Math.sqrt((r * r) - (pm * pm));
		return Circle.getCircle(mid.moveTo(angle, mc), r);
	}
}
