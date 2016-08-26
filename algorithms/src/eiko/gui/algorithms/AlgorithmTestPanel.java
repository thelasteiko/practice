package eiko.gui.algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import eiko.error.TimerRunningException;
import eiko.testable.AbstractRunnableTest;
import eiko.testable.CallbackInterface;
import eiko.testable.Timer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import lib.stacktab.AbstractGridPane;
import lib.stacktab.AbstractStackTab;

/**
 * Displays information about the algorithm.
 * @author Melinda Robertson
 * @version 20160826
 */
public class AlgorithmTestPanel extends AbstractGridPane implements CallbackInterface {
	
	private AbstractRunnableTest testing;
	private TextArea test_data;
	private TextArea result_data;
	private TextField test_number;
//	private TextField start_time;
//	private TextField end_time;
	private TextField duration;
	private TextField num_elements;
	private TextField performance;
	
	private Button btn_back;

	public AlgorithmTestPanel(AbstractStackTab parent, AbstractRunnableTest testing) {
		super(parent);
		this.testing = testing;
		testing.setCallback(this);
	}

	@Override
	protected void build() {
		add(new Label("Test Data"), 0, 0, 4, 1);
		//---------SHOWS TEST DATA-------------
		test_data = new TextArea();
		ScrollPane sc = new ScrollPane(test_data);
		add(sc, 0, 1, 4, 10);
		
		//----------BUTTONS TO LOAD TEST DATA---------------
		Button btn_file = new Button("File");
		btn_file.setOnAction(event-> {
			FileChooser fc = new FileChooser();
			File file = fc.showOpenDialog(null);
			if (file != null) {
				try (BufferedReader br = new BufferedReader(new FileReader(file))) {
					String line;
					StringBuilder sb = new StringBuilder();
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
					test_data.setText(sb.toString());
				} catch (IOException exc) {
					exc.printStackTrace();
				}
			}
		});
		add(btn_file, 0, 11, 1, 1);
		
		HBox spacer = new HBox();
		spacer.setMinWidth(20);
		add(spacer, 1, 11, 1, 1);
		
		test_number = new TextField();
		add(test_number, 2, 11, 1, 1);
		Button btn_db = new Button("DB");
		btn_db.setOnAction(e-> {
			//TODO use the database to access test data
		});
		add(btn_db, 3, 11, 1, 1);
		
		Button btn_run = new Button("Run");
		btn_run.setOnAction(e-> {
			testing.setData(test_data.getText());
			Thread thread = new Thread(testing);
			btn_back.setDisable(true);
			thread.start();
		});
		add(btn_run, 5, 1, 1, 1);
		
		//-------------DISPLAYS RESULTS-------------------
		add(new Label("# Test Items"), 6, 1, 1, 1);
//		add(new Label("Start"), 6, 2, 1, 1);
//		add(new Label("End"), 6, 3, 1, 1);
		add(new Label("Duration"), 6, 4, 1, 1);
		add(new Label("Performance"), 6, 5, 1, 1);
		
		num_elements = new TextField();
		num_elements.setEditable(false);
		add(num_elements, 7,1,1,1);
//		start_time = new TextField();
//		add(start_time, 7,2,1,1);
//		end_time = new TextField();
//		add(end_time, 7,3,1,1);
		duration = new TextField();
		duration.setEditable(false);
		add(duration, 7,4,1,1);
		performance = new TextField();
		performance.setEditable(false);
		add(performance, 7,5,1,1);
		
		result_data = new TextArea();
		result_data.setEditable(false);
		add(result_data,8,1,4,10);
		
		btn_back = new Button("Back");
		btn_back.setOnAction(event->{
			try {
				parent.pop();
			} catch (Exception e1) {
				//this won't happen right now.
			}
		});
		add(btn_back,12,11,1,1);
	}

	@Override
	public void test_end(int num_elements, Timer timer, String... data) {
		this.num_elements.setText(String.valueOf(num_elements));
		try {
			long dur = timer.getTime();
			duration.setText(String.valueOf(dur));
			double perf = (double) dur / (double) num_elements;
			performance.setText(String.valueOf(perf));
		} catch (TimerRunningException e) {
			//TODO change the error thing to a javafx stage
		}
		if (data.length > 0) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < data.length; i++) {
				sb.append(data[i]);
				sb.append('\n');
			}
			result_data.setText(sb.toString());
		}
		btn_back.setDisable(false);
		testing.reset();
	}
}
