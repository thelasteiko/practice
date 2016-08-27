package eiko.testable;

import eiko.drive.Util;

/**
 * Martian coin changing chooses the least amount of coins needed
 * to add up to a value, given the list of coin values available.
 * @author Melinda Robertson
 * @version 20160826
 */
public class MartianCoinChanging extends AbstractRunnableTest {
	
	private Integer[] data;
	private int value;
	//private Integer[] results;
	private int result;
	
	public MartianCoinChanging() {
		timer = new Timer();
	}

	@Override
	public void run() {
		if (data == null) return;
		timer.start();
		mcc();
		timer.stop();
		cb.test_end(data.length, timer, String.valueOf(result));
				//Util.intlist_toString(results));
	}

	@Override
	public void setData(String parsable) {
		int i = parsable.indexOf('\n');
		if (i > 0) value = Integer.parseInt(parsable.substring(0, i));
		data = Util.parsecsv(parsable.substring(i+1));
	}

	@Override
	public void reset() {
		data = null;
		timer.reset();
		value = 0;
	}
	
	private void mcc() {
//		results = new Integer[value+1];
//		for (int i = 0; i <= value; i++) {
//			results[i] = mcc(data, i);
//		}
		result = mcc(data, value);
	}
	
	private int mcc(Integer c[], int v) {
		if (v <= 0) return 0;
		Integer[] r = new Integer[v+1];
		r[0] = 0;
		int st_min = mcc(c, v-c[0]);
		for (int i = 1; i <= v; i++) {
			int min = st_min;
			for (int j = 1; j < c.length; j++) {
				if (v == c[j]) return 1;
				if (v-c[j] < 0) break;
				int curr = mcc(c, v-c[j]);
				if (min > curr) min = curr;
			}
			r[i] = min+1;
		}
		return r[v];
	}

}
