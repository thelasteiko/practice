package eiko.testable;

/**
 * The callback for algorithms. When the algorithm thread completes
 * it informs the interface so that the information about the run
 * can be displayed.
 * @author Melinda Robertson
 * @version 20160826
 */
public interface CallbackInterface {
	/**
	 * Informs the listener that the test has ended and what the results were.
	 * @param num_elements are the number of test elements used.
	 * @param timer holds data about duration, start and end times.
	 * @param data is additional results pertinent to the test.
	 */
	public void test_end(int num_elements, Timer timer, String... data);
}
