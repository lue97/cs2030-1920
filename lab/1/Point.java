class Point {
	private final double x;
	private final double y;
	Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public double getX() { return this.x; }
	public double getY() { return this.y; }
	public Point midPoint(Point p) {
		return new Point((p.x + this.x) / 2, ((p.y + this.y) / 2));
	}
	public double distanceTo(Point p) {
		return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
	}
	public double angleTo(Point p) {
		return Math.atan2(p.y - this.y, p.x - this.x);
	}
	public Point moveTo(double r, double d) {
		return new Point(this.x + d * Math.cos(r), this.y + d * Math.sin(r));
	}
	@Override
	public String toString() {
		return String.format("point (%.3f, %.3f)", this.x, this.y);
	}
}
