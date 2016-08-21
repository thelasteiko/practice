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
	
	public static final String FN_SLIDES = "./resource/browser.png";
	public static final String FN_ALGORITHMS = "./resource/chalkboard.png";
	public static final String FN_COLLECTIONS = "./resource/shopping-bag.png";
	public static final String FN_HOME = "./resource/ufo.png";
	public static final String FN_STYLE = "./resource/style.css";
	
	public static final String SC_TITLE = "title1";
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
}
