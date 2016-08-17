/**
 * 
 */
package eiko.drive;

import java.util.Scanner;

/**
 * @author Melinda Robertson
 * @version 20160817
 */
public class Driver {
	
	private static final String MENU_MAIN = "a. Algorithms\nc. Collections\nt. Threads\nq. Quit";
	private static final String MENU_ALGORITHMS = "k. Knapsack\nr. Return\nq. Quit";

	/**
	 * @param args are args.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		pr("Algorithm Testing Utility");
		pr("Select an option (type just the leading character):");
		char user_choice = 'q';
		
		do {
			pr(MENU_MAIN);
			user_choice = in(sc);
			switch(user_choice) {
			case 'a':
				pr(MENU_ALGORITHMS);
				user_choice = in(sc);
				break;
			case 'b':
				break;
			case 'c':
				break;
			case 't':
				break;
			}
		} while (user_choice != 'q');
	}
	
	public static void pr(String line) {
		System.out.println(line);
	}
	
	public static char in(Scanner sc) {
		return sc.nextLine().charAt(0);
	}
}
