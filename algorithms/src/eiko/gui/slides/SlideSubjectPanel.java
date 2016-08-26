package eiko.gui.slides;

import lib.listpanel.AbstractListPanel;
import lib.stacktab.AbstractStackTab;

public class SlideSubjectPanel extends AbstractListPanel {

	public SlideSubjectPanel(AbstractStackTab parent) {
		super(parent);
		
	}

	@Override
	public void buildList() {
		// TODO Add items from the database to the list
		//SELECT DISTINCT subject FROM slide;
	}

	@Override
	public void buildBtns(int startcol, int startrow, int endrow) {
		// TODO Auto-generated method stub

	}

}
