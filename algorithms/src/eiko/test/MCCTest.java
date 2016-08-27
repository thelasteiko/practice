package eiko.test;

import eiko.error.TimerRunningException;
import eiko.testable.CallbackInterface;
import eiko.testable.MartianCoinChanging;
import eiko.testable.Timer;

public class MCCTest implements CallbackInterface {
	
	public static void main(String[] args) {
		new MCCTest();
	}
	
	public MCCTest() {
		MartianCoinChanging mc = new MartianCoinChanging();
		String test_data = "9\n1,3,5,8,10,12";
		mc.setCallback(this);
		mc.setData(test_data);
		Thread thread = new Thread(mc);
		thread.start();
	}

	@Override
	public void test_end(int num_elements, Timer timer, String... data) {
		StringBuilder sb = new StringBuilder();
		sb.append("Number Elements: ");
		sb.append(num_elements);
		sb.append('\n');
		sb.append("Duration: ");
		try {
			sb.append(timer.getTime());
		} catch (TimerRunningException e) {
		}
		sb.append('\n');
		sb.append("Results:\n");
		for (int i = 0; i < data.length; i++) {
			sb.append(data[i]);
			sb.append('\n');
		}
		System.out.println(sb.toString());
	}

}
