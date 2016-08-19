/**
 * 
 */
package eiko.error;

/**
 * @author Melinda Robertson
 *
 */
public class TimerRunningException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1438163909360120812L;
	
	private String message = "Timer is running. Time cannot be retrieved until timer is stopped.";

	@Override
	public String getMessage() {return message;}
}
