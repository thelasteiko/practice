package lib.stacktab;

import java.util.ArrayList;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
/**
 * Defines a tab that holds panels in a stack. The top-most panel
 * is the one that is visible.
 * @author Melinda Robertson
 * @version 20151213
 */
public abstract class AbstractStackTab extends Tab {
	/**
	 * The stack pane on top of this tab that switches between views.
	 * When a new item should be added, this tab can push and pop panels.
	 */
	private StackPane stack;
	
	//private ArrayList<Class<AbstractGridPane>> listing;

	/**
	 * The constructor. Makes an instance of TabSearch with a database
	 * connector that accesses a tutoring database.
	 */
	public AbstractStackTab() {
		stack = new StackPane();
		//listing = new ArrayList<Class<AbstractGridPane>>();
		this.setContent(stack);
	}
	
	/**
	 * Sets the size of the tab in pixels.
	 * @param width is the width.
	 * @param height is the height.
	 */
	public void setSize(double width, double height) {
		stack.setMinSize(width, height);
	}
	/**
	 * Pushes the given panel onto the stack and displays it.
	 * @param gp is the panel to add.
	 */
	public void push(AbstractGridPane gp) {
		if (stack.getChildren().size() < 1) {
			stack.getChildren().add(gp);
			gp.setVisible(true);
			return;
		}
//		if (stack.getChildren().size() > 2) {
//			listing.add((Class<AbstractGridPane>) stack.getChildren().get(1).getClass());
//			stack.getChildren().remove(1);
//		}
		this.stack.getChildren().add(0, new ScrollPane(gp));
		stack.getChildren().get(1).setVisible(false);
	}
	/**
	 * Pops the most current panel off the stack and disposes it.
	 * Shows the next most current panel.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void pop() throws InstantiationException, IllegalAccessException {
		if (stack.getChildren().size() < 1)	return;
		stack.getChildren().remove(0);
//		if (!listing.isEmpty()) {
//			if (stack.getChildren().size() < 1)
//				stack.getChildren().add(listing.remove(listing.size()-1).newInstance());
//			else
//				stack.getChildren().add(1,listing.remove(listing.size()-1).newInstance());
//		}
		if (stack.getChildren().size() < 1)	return;
		stack.getChildren().get(0).setVisible(true);
	}
	
	/**
	 * Pop panels off the stack until there is only one left.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void top() throws InstantiationException, IllegalAccessException {
		while(stack.getChildren().size() > 1) {
			pop();
		}
	}

}
