import java.util.function.Supplier;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.BiFunction;

class Lazy<T> {

    public Optional<T> value;
    public Optional<T> mapped;
    public Supplier<T> supplier;

    public Lazy(T value) {
        this.value = Optional.of(value);
        this.supplier = () -> value;
    }

    public Lazy(Supplier<T> supplier) {
        this.value = Optional.empty();
        this.supplier = supplier;
    }

    static <T> Lazy<T> of(T v) {
        return new Lazy<T>(v);
    }

    static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<T>(supplier);
    }

    public T get() {
        T rv = this.value.orElseGet(this.supplier);
        this.value = Optional.ofNullable(rv);
        return rv;
    }

    public <U> Lazy<U> map(Function<? super T, ? extends U> mapper) {
        return Lazy.of(() -> mapper.apply(this.get()));
    }

    public <U> Lazy<U> flatMap(Function<? super T, Lazy<U>> mapper) {
        return Lazy.of(() -> mapper.apply(this.get()).get());
    }

    public <U, R> Lazy<R> combine(Lazy<U> other, BiFunction<? super T, ? super U, ? extends R> combiner) {
        return Lazy.of(() -> combiner.apply(this.get(), other.get()));
    }

    public Lazy<Boolean> test(Predicate<T> pred) {
        return Lazy.of(() -> pred.test(this.get()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Lazy && ((Lazy) other).get().equals(this.get())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.value.map(v -> String.valueOf(v))
            .orElse("?");
    }
}
