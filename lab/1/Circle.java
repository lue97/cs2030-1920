class Circle {
	private final Point origin;
	private final double radius;
	private Circle(Point origin, double radius) {
		this.origin = origin;
		this.radius = radius;
	}
	public static Circle getCircle(Point origin, double r) {
		if (r <= 0) return null;
		return new Circle(origin, r);
	}
	public Point getOrigin() { return this.origin; }
	public double getRadius() { return this.radius; }
	@Override
	public String toString() {
		return String.format("circle of radius %.1f centered at point (%.3f, %.3f)", this.radius, this.origin.getX(), this.origin.getY());
	}
}
