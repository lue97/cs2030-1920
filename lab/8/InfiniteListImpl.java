import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class InfiniteListImpl<T> implements InfiniteList<T> {

	private class CachedSupplier<T> {
		private final Supplier<T> supplier;
		private T cache;

		CachedSupplier(Supplier<T> supplier) {
			this.supplier = supplier;
			this.cache = null;
		}

		public T get() {
			if (this.cache == null) {
				this.cache = this.supplier.get();
			}
			return this.cache;
		}
	}

	public final boolean empty;

	private final CachedSupplier<Optional<? extends T>> head;
	private final CachedSupplier<InfiniteListImpl<? extends T>> tail;

	public InfiniteListImpl(Supplier<Optional<? extends T>> head, Supplier<InfiniteListImpl<? extends T>> tail) {
		this.head = new CachedSupplier<Optional<? extends T>>(head);
		this.tail = new CachedSupplier<InfiniteListImpl<? extends T>>(tail);
		this.empty = this.head == null ? true : false;
	}

	public InfiniteListImpl(CachedSupplier<Optional<? extends T>> head, Supplier<InfiniteListImpl<? extends T>> tail) {
		this.head = head;
		this.tail = new CachedSupplier<InfiniteListImpl<? extends T>>(tail);
		this.empty = this.head == null ? true : false;
	}	

	public InfiniteListImpl() {
		this.head = new CachedSupplier<Optional<? extends T>>(() -> Optional.empty());
		this.tail = null;
		this.empty = true;
	}

	public static <T> InfiniteListImpl<T> generate(Supplier<? extends T> supplier) {
		return new InfiniteListImpl<T>(
					() -> Optional.of(supplier.get()),
					() -> InfiniteListImpl.generate(supplier));
	}

	public static <T> InfiniteListImpl<T> iterate(T seed, Function<? super T, ? extends T> next) {
		return new InfiniteListImpl<T>(
					() -> Optional.of(seed),
					() -> InfiniteListImpl.iterate(next.apply(seed), next));
	}

	public <R> InfiniteListImpl<R> map(Function<? super T, ? extends R> mapper) {
		if (this.isEmptyList()) return new InfiniteListImpl<R>();
		return new InfiniteListImpl<R>(
				   () -> this.head.get().map(mapper),
				   () -> this.tail.get().map(mapper));
	}

	public InfiniteListImpl<T> filter(Predicate<? super T> predicate) {
		if (this.isEmptyList()) return new InfiniteListImpl<T>();
		return new InfiniteListImpl<T>(
				   () -> head.get().filter(predicate),
				   () -> tail.get().filter(predicate)
			   );
	}

	public InfiniteListImpl<T> limit(long n) {
		return new InfiniteListImpl<T>(
			() -> {
				if (n <= 0) return Optional.empty();
				return this.head.get();
			},
			() -> {
				if (n <= 0 || (n == 1 && this.head.get().isPresent())) return new InfiniteListImpl<T>();
				InfiniteListImpl<? extends T> t = this.tail.get();
				if (t.isEmptyList()) return new InfiniteListImpl<T>();
				if (this.head.get().isPresent()) {
					return t.limit(n - 1);
				} else {
					return t.limit(n);
				}
			}
		);
	}

	
	public void forEach(Consumer<? super T> action) {
		InfiniteListImpl<? extends T> list = this;
		while (!list.isEmptyList()) {
			list.head.get().ifPresent(action);
			list = list.tail.get();
		}
	}

	@SuppressWarnings("unchecked")
	public Object[] toArray() {
		InfiniteListImpl<? extends T> list = this;
        List<T> vals = new ArrayList<>();
        while (!list.isEmptyList()) {
            if (list.head.get().isPresent()) {
                vals.add(list.head.get().get());
            }
            
            list = list.tail.get();
        }
        return vals.toArray();
	}

	public long count() {
		if (this.isEmptyList()) return 0;
		if (this.head.get().isPresent())
			return 1 + this.tail.get().count();
		return this.tail.get().count();
	}

	public boolean isEmptyList() {
		return this.empty;
	}

	@SuppressWarnings("unchecked")
	public <U> U reduce (U identity, BiFunction<U, ? super T, U> accumulator) {
		U result = identity;
		InfiniteListImpl<T> list = this;
		while (!list.isEmptyList()) {
			if (list.head.get().isPresent()) {
				result = accumulator.apply(result, list.head.get().get());
			}
			list = (InfiniteListImpl<T>)list.tail.get();
		}
		return result;
	}

	public InfiniteListImpl<T> takeWhile(Predicate<? super T> predicate) {
		CachedSupplier<Boolean> result = new CachedSupplier<Boolean>(
				() -> predicate.test(this.head.get().get())
			);
		if (this.isEmptyList()) return new InfiniteListImpl<T>();
		return new InfiniteListImpl<T>(
				() -> {
					Optional<? extends T> temp = this.head.get();
					if (temp.isPresent()) {
						if (result.get())
							return temp;
						else
							return Optional.empty();
					}
					return Optional.empty();
				},
				() -> {
					Optional<? extends T> temp = this.head.get();
					if (!temp.isPresent())
						return this.tail.get().takeWhile(predicate);
					if (result.get())
						return this.tail.get().takeWhile(predicate);
					return new InfiniteListImpl<T>();
				}
			);
	}

	@SuppressWarnings("unchecked")
	public Optional<T> reduce (BinaryOperator<T> accumulator) {
		T result = null;
		boolean ifAny = false;
		InfiniteListImpl<T> list = this;
		while (!list.isEmptyList()) {
			if (!ifAny && list.head.get().isPresent()) {
				ifAny = true;
				result = list.head.get().get();
			}
			else if (ifAny && !list.isEmptyList()) {
				if (list.head.get().isPresent()) {
					result = accumulator.apply(result, list.head.get().get());
				}
			}
			list = (InfiniteListImpl<T>)list.tail.get();
		}
		return ifAny ? Optional.of(result) : Optional.empty();
	}

	@SuppressWarnings("unchecked")
	public InfiniteListImpl<T> get() {
		this.head.get().ifPresent(System.out::println);
		return (InfiniteListImpl<T>)this.tail.get();
	}
}
