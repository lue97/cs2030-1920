class Cruise {
	protected final String id;
	protected final int arrival;

	public Cruise(String id, int arrival) {
		this.id = id;
		this.arrival = arrival;
	}

	public int getArrivalTime() {	
		int hours = this.arrival / 100;
		int minutes = this.arrival - hours * 100;
		return hours * 60 + minutes;
	}

	public int getNumLoadersRequired() {
		return 1;
	}

	public int getServiceCompletionTime() {
		int hours = this.arrival / 100;
		int minutes = this.arrival - hours * 100;
		return hours * 60 + minutes + 30;
	}

	@Override
		public String toString() {
			return String.format("%s@%04d", this.id, this.arrival);
		}
}
