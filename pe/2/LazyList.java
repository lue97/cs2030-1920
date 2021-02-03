import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Map;
import java.util.SortedMap;
import java.util.ArrayList;

class LazyList<T> {
	List<T> list;
    UnaryOperator<T> f;

    private LazyList(int n, T seed, UnaryOperator<T> f) {
        this.list = new ArrayList<>();
        list.add(seed);
		this.f = f;
    }

    static <T> LazyList<T> generate(int n, T seed, UnaryOperator<T> f) {
        return new LazyList<T>(n, seed, f);
    }

    public T get(int i) { 
        if (i >= list.size()) {
            list.addAll(
                Stream.iterate(list.get(list.size() - 1), x -> f.apply(x))
                .skip(1)
                .limit(i - list.size() + 1)
                .collect(Collectors.toList()));
        }
        return list.get(i);
    }

    public int indexOf(T v) {
        if (list.indexOf(v) == -1) {
            list.addAll(Stream.iterate(list.get(list.size() - 1), x -> f.apply(x))
                .skip(1)
                .takeWhile(x -> !x.equals(v))
                .collect(Collectors.toList()));
            list.add(v);
        }
        return list.indexOf(v);
    }
}
