package eiko.drive;

import java.io.File;

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
}
