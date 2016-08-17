package eiko.drive;

/**
 * 
 * @author Melinda Robertson
 * @version 20160817
 */
public class Util {
	
	private Util() {}

	public static <T extends Number> T max (T a, T b) {
		if (a.doubleValue() >= b.doubleValue()) return a;
		else return b;
	}
}
