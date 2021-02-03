import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		int loaderCount = 0;
		int index;
		String id;
		Loader loaders[] = new Loader[0];
		Cruise cruises[] = new Cruise[n];
		for (int i = 0; i < n; i++) {
			id = s.next();
			if (id.charAt(0) != 'B') {
				cruises[i] = new Cruise(id, s.nextInt());
			} else {
				cruises[i] = new BigCruise(id, s.nextInt(), s.nextInt(), s.nextInt());
			}
		}
		for (int i = 0; i < cruises.length; i++) {
			for (int j = 0; j < cruises[i].getNumLoadersRequired(); j++) {
				if ((index = getFreeLoaderIndex(loaders, cruises[i])) == -1) {
					if ((loaderCount + 1) % 3 == 0)
						loaders = addLoader(loaders, new RecycledLoader(++loaderCount, cruises[i]));
					else
						loaders = addLoader(loaders, new Loader(++loaderCount, cruises[i]));
					index = loaders.length - 1;
				} else {
					loaders[index] = loaders[index].serve(cruises[i]);
				}	
				System.out.println(loaders[index].toString());
			}
		}
	}

	public static int getFreeLoaderIndex(Loader[] loaders, Cruise c) {
		for (int i = 0; i < loaders.length; i++)
			if (loaders[i].serve(c) != null) return i;
		return -1;
	}

	public static Loader[] addLoader(Loader[] loaders, Loader loader) {
		Loader[] loaders_ = new Loader[loaders.length + 1];
		for (int i = 0; i < loaders.length; i++) loaders_[i] = loaders[i];
		loaders_[loaders_.length - 1] = loader;
		return loaders_;
	}
}
