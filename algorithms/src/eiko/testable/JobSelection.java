package eiko.testable;

import eiko.drive.Util;

/**
 * This job selection algorithm starts with a list of payments for consecutive
 * days but jobs can only be completed after certain intervals, say every other day.
 * @author Melinda Robertson
 * @version 20160826
 */
public class JobSelection extends AbstractRunnableTest {
	
	private Integer[] data;
	private Integer[] jobs;
	private int interval;

	@Override
	public void run() {
		timer.start();
		job();
		timer.stop();
		cb.test_end(data.length, timer, jobs.toString());
	}

	@Override
	public void setData(String parsable) {
		int i = parsable.indexOf('\n');
		if(i > 0) interval = Integer.parseInt(parsable.substring(0, i));
		data = Util.parsecsv(parsable.substring(i+1));
	}

	@Override
	public void reset() {
		data = null;
		jobs = null;
		timer.reset();
		interval = 0;
	}
	/**
	 * The job selection algorithm which chooses optimal job choices
	 * based on max income constrained by intervals.
	 * The algorithm chooses the max between the current day, plus the
	 * profits from [interval] days ago, and the previous day. 
	 */
	private void job() {
		if (data == null) return;
		jobs = new Integer[data.length];
		int i = 0;
		jobs[i] = data[i];
		i++;
		while (i < interval) {
			jobs[i] = Util.max(jobs[i-1], data[i]);
			i++;
		}
		for (; i < data.length; i++) {
			int a = jobs[i-interval] + data[i];
			int b = jobs[i-1];
			jobs[i] = Util.max(a, b);
		}
	}
}
