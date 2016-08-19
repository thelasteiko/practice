package eiko.error;

public class HashKeyException extends HashException {
	
	private String key;
	
	public HashKeyException(int index, String key) {
		this.message = "Could not find key or insert value.";
		this.error_index = index;
		this.key = key;
	}

	@Override
	public String getMessage() {
		return message + " : " + error_index + " : " + key;
	}

}
