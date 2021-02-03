import java.util.function.Function;
import java.util.List;
import java.util.ArrayList;

public class Trace<T> {
    protected final T head;
    protected final List<T> history;

    @SafeVarargs
    protected Trace(T head, T... items) {
        this.history = new ArrayList<T>();
        this.head = head;
        for (T item : items) {
            this.history.add(item);
        }
    }

    @SafeVarargs
    public static <T> Trace<T> of(T head, T... items) {
        return new Trace<T>(head, items);
    }

    public T get() {
        return this.head;
    }

    public List<T> history() {
		List<T> hist = new ArrayList<T>();
		hist.addAll(this.history);
		hist.add(this.head);
		return hist;
    }

    @SuppressWarnings("unchecked")
    public Trace<T> back(int i) {
        if (i == 0)
            return this;
        if (i >= this.history.size())
            i = this.history.size();
        T head_ = this.history.get(this.history.size() - i);
        T hist_[] = (T[]) new Object[this.history.size() - i];
        hist_ = this.history.subList(0, this.history.size() - i).toArray(hist_);
        return new Trace<T>(head_, hist_);
	}

    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
        if (!(o instanceof Trace)) {
            return false;
		}
        Trace<T> t = (Trace<T>)o;
        if (t.get() != this.get()) {
            return false;
        }
        if (t.history().size() != this.history().size()) {
            return false;
        }
        if (t.history().equals(this.history()) == false) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public Trace<T> map(Function<? super T, ? extends T> mapper) {
        T head_ = mapper.apply(this.head);
        T hist_[] = (T[]) new Object[this.history.size() + 1];
        List<T> histList = new ArrayList<T>(this.history);
        histList.add(this.head);
        hist_ = histList.toArray(hist_);
        return new Trace<T>(head_, hist_);
    }

    @SuppressWarnings("unchecked")
    public <R> Trace<T> flatMap(Function<? super T, ? extends R> mapper) {
        Trace<T> trace = (Trace<T>)mapper.apply(this.head);
        trace.history.addAll(0, this.history);
        return trace;
    }
}
