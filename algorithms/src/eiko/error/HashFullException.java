package eiko.error;

/**
 * 
 * @author Melinda Robertson
 * @version 20160819
 */
public class HashFullException extends HashException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3764688861735006061L;
	
	public HashFullException(int index) {
		message = "The table is full. Cannot insert value.";
		error_index = index;
	}

	@Override
	public String getMessage() {return message + " : " + error_index;}
}
