import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String type;
		String name;
		List<Integer> ids;
		List<Integer> orders = new ArrayList<Integer>();
		int[] orders_;
		Menu menu = new Menu();
		while((scanner.next()).equals("add")) {
			type = scanner.next();
			name = scanner.next();
			if (type.equals("Combo")) {
				ids = new ArrayList<Integer>();
				while (scanner.hasNextInt()) {
					ids.add(scanner.nextInt());
				}
				menu.add(type, name, ids);
			} else {
				menu.add(type, name, scanner.nextInt());
			}
		}
		menu.print();
		while (scanner.hasNextInt()) {
			orders.add(scanner.nextInt());
		}
		System.out.println("\n--- Order ---");
		orders_ = new int[orders.size()];
		for (int i = 0; i < orders.size(); i++) {
			orders_[i] = orders.get(i);
		}
		System.out.println(new Order(menu).add(orders_));
		
	}
}
