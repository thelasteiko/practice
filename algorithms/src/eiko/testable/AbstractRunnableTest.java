package eiko.testable;

/**
 * An abstract class for testing algorithms.
 * @author Melinda Robertson
 * @version 20160826
 */
public abstract class AbstractRunnableTest implements Runnable {
	/**
	 * Once the test is finished, the callback object is notified.
	 */
	protected CallbackInterface cb;
	/**
	 * The timer keeps track of how long the test lasts.
	 */
	protected Timer timer;
	/**
	 * Sets the object to callback to once the test completes.
	 * @param cb is the listener.
	 */
	public void setCallback(CallbackInterface cb) {
		this.cb = cb;
	}
	/**
	 * Sets the test data. The test data format is determined by
	 * the inheriting class.
	 * @param parsable is a string that contains the data to use.
	 */
	abstract public void setData(String parsable);
	/**
	 * Resets the test object in order to run the test again.
	 * New test data must be loaded after a reset.
	 */
	abstract public void reset();
}
