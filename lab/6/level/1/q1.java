import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.IntStream;

public class q1 {
	public static void main(String[] args) {
		for (int i = 1; i <= 10; i++)
			System.out.println(omega(i).count());
	}

	public static boolean isPrime(int n) {
		return IntStream
				.range(2, n)
				.noneMatch(x -> n % x == 0);
	}

	public static IntStream omega(int n) {
		return IntStream
			.range(2, n + 1)
			.filter(x -> isPrime(x) && n % x == 0)
			.distinct();
	}
}
