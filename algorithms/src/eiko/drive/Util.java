package eiko.drive;

import java.io.File;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * @author Melinda Robertson
 * @version 20160817
 */
public class Util {
	//<div>Icons made by <a href="http://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
	public static final String FN_SLIDES = "./resource/browser.png";
	public static final String FN_ALGORITHMS = "./resource/chalkboard.png";
	public static final String FN_COLLECTIONS = "./resource/shopping-bag.png";
	public static final String FN_HOME = "./resource/ufo.png";
	public static final String FN_THREADS = "./resource/needle.png";
	public static final String FN_STYLE = "./resource/style.css";
	
	public static final String SC_TITLE1 = "title1";
	public static final String SC_TITLE2 = "title2";
	public static final String SC_GRID = "grid";
	public static final String SC_PANE = "pane";
	
	public static final int IMG_PXSIZE = 64;
	
	private Util() {}

	public static <T extends Number> T max (T... a) {
		T max = a[0];
		for (int i = 1; i < a.length; i++) {
			if (a[i].doubleValue() > max.doubleValue()) max = a[i];
		}
		return max;
	}
	
	public static <T extends Number> T min (T... a) {
		T min = a[0];
		for (int i = 1; i < a.length; i++) {
			if (a[i].doubleValue() < min.doubleValue()) min = a[i];
		}
		return min;
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
	
	/**
	 * http://stackoverflow.com/questions/17018562/how-to-create-tabs-with-icons-in-javafx
	 * RichardK
	 * @param filename is the filename.
	 * @return an image.
	 */
	public static ImageView buildImage(String filename) {
		Image i = new Image(new File(filename).toURI().toString());
        ImageView imageView = new ImageView();
        //You can set width and height
        imageView.setFitHeight(IMG_PXSIZE);
        imageView.setFitWidth(IMG_PXSIZE);
        imageView.setImage(i);
        return imageView;
	}
	
	public static Image getImage(String filename) {
		return new Image(new File(filename).toURI().toString());
	}
	
	public static Integer[] createSortTestList(int size, int min, int max) {
		Integer[] data = new Integer[size];
		Random r = new Random();
		for(int i = 0; i < size; i++) {
			data[i] = r.nextInt(max-min)+min;
		}
		return data;
	}
	public static void shuffle(Integer[] data) {
		if (data == null) return;
		Random r = new Random();
		for (int i = 0; i < data.length; i++) {
			int n = r.nextInt(data.length);
			int m = r.nextInt(data.length);
			swap(data, n, m);
		}
	}
	
	public static <T extends Comparable<? super T>> void swap(T[] data, int a, int b) {
		T temp = data[a];
		data[a] = data[b];
		data[b] = temp;
	}
	
	public static Integer[] parsecsv(String parsable) {
		String[] parsed_data = parsable.split(",");
		Integer[] data = new Integer[parsed_data.length];
		for(int i = 0; i < data.length; i++) {
			data[i] = Integer.parseInt(parsed_data[i]);
		}
		return data;
	}
	
	public static String intlist_toString(Integer[] list) {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		if (list.length > 0)
			sb.append(list[0]);
		for (int i = 1; i < list.length; i++) {
			sb.append(',');
			sb.append(list[i]);
		}
		sb.append('}');
		return sb.toString();
	}
}
