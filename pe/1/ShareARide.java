class ShareARide extends Service {
    public ShareARide() {
        super(50);
    }
    
    @Override
    public int computeFare(Request r) {
        int total = 0;
        if (r.time >= 600 && r.time <= 900) {
            total += 500;
        }
        total += this.fare * r.dist;
        return total / r.pax;
    }
	@Override
	public String toString() {
		return "ShareARide";
	}
}
