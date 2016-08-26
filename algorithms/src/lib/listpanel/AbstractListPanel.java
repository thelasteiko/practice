package lib.listpanel;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import lib.stacktab.AbstractGridPane;
import lib.stacktab.AbstractStackTab;

/**
 * Creates a panel with a list of strings on the left and
 * a panel for buttons on the right.
 * @author Melinda Robertson
 * @version 20160824
 */
public abstract class AbstractListPanel extends  AbstractGridPane {
	
	private ListView<String> list_gui;
	protected String selection;
	protected ObservableList<String> list_items =
			FXCollections.observableArrayList();
	
	public AbstractListPanel(AbstractStackTab parent) {
		super(parent);
	}
	
	public void build() {
		list_gui = new ListView<String>();
		buildList();
		list_gui.setItems(list_items);
		//don't need a listener on the list itself
		add(list_gui,1,1,2,10);
		buildBtns(2,1,11);
	}
	
	public String getSelection() {
		return list_gui.getSelectionModel().getSelectedItem();
	}
	public void addItem(String item) {
		list_items.add(item);
	}
	public void addAll(Collection<? extends String> items) {
		list_items.addAll(items);
	}
	public void addAll(String... all) {
		for (int i = 0; i < all.length; i++) {
			addItem(all[i]);
		}
	}
	
	abstract public void buildList();
	abstract public void buildBtns(int startcol, int startrow, int endrow);

}
