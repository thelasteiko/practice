package eiko.gui;

import eiko.drive.Util;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * Displays welcome info and directions.
 * @author Melinda Robertson
 * @version 20160820
 */
public class HomeTab extends Tab {

	public HomeTab() {
		setGraphic(Util.buildImage(Util.FN_HOME));
		build();
	}
	
	private void build() {
		GridPane gp = new GridPane();
		gp.getStyleClass().add(Util.SC_GRID);
		gp.getStyleClass().add(Util.SC_PANE);
		Label welcome = new Label("Welcome to the Code Study Tool.");
		welcome.getStyleClass().add(Util.SC_TITLE);
		//add a key, some type of table with icon then label
		ImageView img_slides = Util.buildImage(Util.FN_SLIDES);
		Label lbl_slides = new Label("Lecture");
		ImageView img_algorithms = Util.buildImage(Util.FN_ALGORITHMS);
		Label lbl_algorithms = new Label("Algorithm Analysis");
		ImageView img_collections = Util.buildImage(Util.FN_COLLECTIONS);
		Label lbl_collections = new Label("Arrays, Lists, etc.");
		
		//child, colindex, rowindex, colspan, rowspan
		gp.add(welcome, 0, 0, 5, 1);
		gp.add(img_slides, 1, 1,1,1);
		gp.add(lbl_slides, 2, 1,2,1);
		gp.add(img_algorithms, 1, 2,1,1);
		gp.add(lbl_algorithms, 2, 2,2,1);
		gp.add(img_collections, 1, 3,1,1);
		gp.add(lbl_collections, 2, 3,2,1);
		
		this.setContent(gp);
	}

}
