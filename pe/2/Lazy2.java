import java.util.function.Supplier;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

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

    @Override
    public String toString() {
        return this.value.map(v -> String.valueOf(v))
            .orElse("?");
    }
}
