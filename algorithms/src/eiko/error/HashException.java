package eiko.error;

public abstract class HashException extends Exception {
	
	protected String message;
	protected int error_index;
	
	public abstract String getMessage();
}
