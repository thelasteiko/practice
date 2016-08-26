package eiko.testable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import eiko.error.TimerRunningException;

/**
 * Calculates the number of nanoseconds between two times.
 * Useful for algorithm analysis.
 * @author Melinda Robertson
 * @version 20160818
 */
public class Timer {
	
	private LocalDateTime start;
	private LocalDateTime stop;
	private boolean running;
	private long until;

	/**
	 * Creates a new timer set at zero.
	 */
	public Timer() {
		running = false;
		until = 0;
	}
	/**
	 * Starts the timer; saves the current time.
	 */
	public void start() {
		start = LocalDateTime.now();
		running = true;
	}
	/**
	 * Stops the timer; saves the stop time and calculates the amount
	 * of nanoseconds that have passed.
	 */
	public void stop() {
		if (!running) return;
		stop = LocalDateTime.now();
		until += start.until(stop, ChronoUnit.NANOS);
		running = false;
	}
	/**
	 * Gets the amount of time currently on the timer.
	 * @return the time passed in nanoseconds.
	 * @throws TimerRunningException if the timer is still running.
	 */
	public long getTime() throws TimerRunningException {
		if (running) throw new TimerRunningException();
		return until;
	}
	/**
	 * Resets the timer to it's initial state.
	 */
	public void reset() {
		start = null;
		stop = null;
		running = false;
		until = 0;
	}
}
