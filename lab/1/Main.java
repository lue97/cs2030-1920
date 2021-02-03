import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		Circle c;
		int max_ = 0, count, n = s.nextInt();
		Point points[] = new Point[n];
		for (int i = 0; i < n; i++) points[i] = new Point(s.nextDouble(), s.nextDouble());
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				count = 0;
				c = createCircle(points[i], points[j], 1);
				if (c == null) continue;
				count = countCoverage(c, points);
				if (max_ < count) max_ = count;
				c = createCircle(points[j], points[i], 1);
				count = countCoverage(c, points);
				if (max_ < count) max_ = count;
			}
		}
		System.out.printf("Maximum Disc Coverage: %d\n", max_);
	}

	public static int countCoverage(Circle c, Point[] points) {
		int count = 0;
		for (Point p : points) {
			if (c.getOrigin().distanceTo(p) <= c.getRadius()) count++;
		}
		return count;
	}

	public static Circle createCircle(Point p1, Point p2, double r) {
		if (p1.getX() == p2.getX() && p1.getY() == p2.getY()) return null;
		Point mid = p1.midPoint(p2);
		double angle = p1.angleTo(p2) + (Math.PI / 2);
		double pm = p1.distanceTo(mid);
		if (pm > r) return null;
		double mc = Math.sqrt((r * r) - (pm * pm));
		return Circle.getCircle(mid.moveTo(angle, mc), r);
	}
}
