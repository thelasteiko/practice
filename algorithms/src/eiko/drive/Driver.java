/**
 * 
 */
package eiko.drive;

import java.util.Scanner;

import eiko.dynamic.Knapsack;
import eiko.error.TimerRunningException;
import eiko.gui.MainFrame;

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
	public static void console(String[] args) {
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
				runAlgorithm(user_choice);
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
	
	public static void runAlgorithm(char choice) {
		switch(choice) {
		case 'k':
			pr("Initializing timer...");
			Timer time = new Timer();
			pr("Creating data...");
			int limit = 10;
			int[] weight = {3, 6, 1, 2, 4};
			int[] value = {3, 9, 4, 5, 1};
			int[] quantity = {1,1,1,1,1};
			pr("Initializing knapsack...");
			Knapsack ks = new Knapsack();
			ks.manual_load(limit, weight, value, quantity);
			pr("Beginning timer...starting run.");
			time.start();
			ks.zero_one2();
			time.stop();
			pr("Results...");
			pr(ks.toString());
			try {
				pr("Time: " + time.getTime());
			} catch (TimerRunningException e) {
				pr(e.getMessage());
			}
			break;
		default:
			pr("Returning...");
		}
	}
	
	public static void pr(String line) {
		System.out.println(line);
	}
	
	public static char in(Scanner sc) {
		return sc.nextLine().charAt(0);
	}
}
