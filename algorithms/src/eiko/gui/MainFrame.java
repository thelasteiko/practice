package eiko.gui;

import java.io.File;

import eiko.drive.Util;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainFrame extends Application {


	/**
	 * The current size of the screen used for positioning this frame.
	 */
	private static final Rectangle2D screensize = Screen.getPrimary().getBounds();
	/**
	 * Width of this frame. 
	 */
	public static double WIDTH = 800;
	/**
	 * Height of this frame.
	 */
	public static double HEIGHT = 500;
	
	/**
	 * Sets the horizontal position of this frame in the screen.
	 */
	public static final double x = ((screensize.getWidth()-WIDTH) / 2);
	/**
	 * Sets the vertical position of this frame in the screen.
	 */
	public static final double y = ((screensize.getHeight()-HEIGHT) / 2);
	
	private TabPane tabs;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Code Study Tool");
		Group root = new Group();
		Scene scene = new Scene(root, WIDTH, HEIGHT, Color.ALICEBLUE);
		scene.getStylesheets().add(new File(Util.FN_STYLE).toURI().toString());
		tabs = new TabPane();
		tabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tabs.setSide(Side.LEFT);
		build();
		BorderPane bp = new BorderPane();
		bp.prefHeightProperty().bind(scene.heightProperty());
		bp.prefWidthProperty().bind(scene.widthProperty());
		bp.setCenter(tabs);
		root.getChildren().add(bp);
		stage.setScene(scene);
		stage.show();
	}
	
	private void build() {
		//TODO add tabs to pane here
		HomeTab home = new HomeTab();
		tabs.getTabs().add(home);
	}

}
