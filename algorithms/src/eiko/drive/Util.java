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
	
	public static String matrix_toString(int[][] matrix) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < matrix.length; i++) {
			sb.append("[ ");
			for (int j = 0; j < matrix[0].length; j++) {
				sb.append(matrix[i][j] + " ");
			}
			sb.append("]\n");
		}
		return sb.toString();
	}
}
