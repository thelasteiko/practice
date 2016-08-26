package eiko.gui.slides;

import eiko.drive.Util;
import lib.stacktab.AbstractStackTab;

public class SlideTab extends AbstractStackTab {

	public SlideTab() {
		super();
		setGraphic(Util.buildImage(Util.FN_SLIDES));
	}
}
