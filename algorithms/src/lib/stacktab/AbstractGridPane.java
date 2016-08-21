package lib.stacktab;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
/**
 * An abstract class as the base for panels that go into the stack tabs.
 * In this implementation the panels access the parent's push and pop
 * methods to modify the stack.
 * @author Melinda Robertson
 * @version 20151213
 */
public abstract class AbstractGridPane extends GridPane {
	
	/**
	 * Default insets for every component.
	 */
	public final static Insets in = new Insets(5, 5, 5, 5);
	/**
	 * The parent stack tab.
	 */
	protected AbstractStackTab parent;

	/**
	 * Constructs the panel.
	 * @param parent is the stack tab that holds this panel.
	 */
	public AbstractGridPane(AbstractStackTab parent) {
		this.parent = parent;
	}
	/**
	 * Builds the panel.
	 */
	abstract protected void build();
	
	/**
	 * Checks to see if a string is numeric.
	 * Located here for convenience.
	 * @param s is the string.
	 * @return true if every character represents a number, false otherwise.
	 */
	public static boolean isNumeric(String s) {
		for(char c: s.toCharArray()) {
			if (!Character.isDigit(c)) return false;
		}
		return true;
	}
}
