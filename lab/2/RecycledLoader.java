class RecycledLoader extends Loader {

	public RecycledLoader(int id) {
		super(id);
	}

	public RecycledLoader(int id, Cruise c) {
		super(id, c);
	}

	@Override
	public final Loader serve(Cruise cruise) {
		return (
			this.cruise == null ||
			this.cruise.getServiceCompletionTime() + 60 <= cruise.getArrivalTime()
		) ? new RecycledLoader(this.id, cruise) : null;
	}

	@Override
	public final String toString() {
		return String.format("Loader %d (recycled)%s", this.id,
			this.cruise != null ? String.format(" serving %s", this.cruise.toString()) : ""
		);
	}
}
