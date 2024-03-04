package bus;

public class NotInRangeException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String message = "Invalid PIN \nEnter a valid 4 digit PIN";

	public NotInRangeException() {
		super(message);
	}
	
	public NotInRangeException(String newMessage) {
		super(newMessage);
	}


}
