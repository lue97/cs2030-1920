class Loader {
	protected final int id;
	protected final Cruise cruise;

	public Loader(int id) {
		this.id = id;
		this.cruise = null;
	}
	public Loader(int id, Cruise c) {
		this.id = id;
		this.cruise = c;
	}

	public Loader serve(Cruise cruise) {
		return (
				this.cruise == null ||
				this.cruise.getServiceCompletionTime() <= cruise.getArrivalTime()
			   ) ? new Loader(this.id, cruise) : null;
	}

	@Override
		public String toString() {
			return String.format("Loader %d%s", this.id,
					this.cruise != null ? String.format(" serving %s", this.cruise.toString()) : ""
					);
		}
}
