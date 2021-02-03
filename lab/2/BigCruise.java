class BigCruise extends Cruise {
	private final int numLoader;
	private final int timeReq;

	public BigCruise(String id, int arrival, int numLoader, int timeReq) {
		super(id, arrival);
		this.numLoader = numLoader;
		this.timeReq = timeReq;
	}

	@Override
		public int getNumLoadersRequired() {
			return this.numLoader;
		}

	@Override
		public int getServiceCompletionTime() {
			int hours = this.arrival / 100;
			int minutes = this.arrival - hours * 100;
			return hours * 60 + minutes + this.timeReq;
		}
}
