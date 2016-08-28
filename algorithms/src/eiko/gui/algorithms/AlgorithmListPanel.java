package eiko.gui.algorithms;

import eiko.testable.JobSelection;
import eiko.testable.Knapsack;
import eiko.testable.MartianCoinChanging;
import eiko.testable.MergeSort;
import eiko.testable.QuickSort;
import javafx.scene.control.Button;
import lib.listpanel.AbstractListPanel;
import lib.stacktab.AbstractStackTab;

public class AlgorithmListPanel extends AbstractListPanel {

	public AlgorithmListPanel(AbstractStackTab parent) {
		super(parent);
	}

	@Override
	public void buildList() {
		addAll("Knapsack", "Quicksort", "Mergesort",
				"Job Selection", "Martian Coin Changing");
//				, "Robot Coin Collection",
//				"Dijsktra's",);
	}

	@Override
	public void buildBtns(int startcol, int startrow, int endrow) {
		int s = startrow;
		Button run = new Button("Run");
		run.setOnAction((e)-> {
			String input = getSelection();
			switch (input) {
			case "Knapsack":
				parent.push(new AlgorithmTestPanel(parent, new Knapsack()));
				break;
			case "Quicksort":
				parent.push(new AlgorithmTestPanel(parent, new QuickSort()));
				break;
			case "Mergesort":
				parent.push(new AlgorithmTestPanel(parent, new MergeSort()));
				break;
			case "Job Selection":
				parent.push(new AlgorithmTestPanel(parent, new JobSelection()));
				break;
			case "Martian Coin Changing":
				parent.push(new AlgorithmTestPanel(parent, new MartianCoinChanging()));
				break;
			}
		});
		add(run, startcol, s++);
	}
}
