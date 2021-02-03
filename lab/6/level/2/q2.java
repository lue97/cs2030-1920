import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.IntStream;

public class q2 {

	public class Pair<T> {
		T x, y;
		Pair(T x, T y) { this.x = x; this.y = y; }
	}

	public static void main(String[] args) {
		System.out.println(fibonacci(10).toArray());
	}

	public static Stream<Integer> fib(int n) {
		return IntStream
				.rangeClosed(0, n)
				.boxed();
	}
}
