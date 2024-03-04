package bus;

public class InsufficientFundsException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String message = "Transaction cancelled due to insufficient balance. \nModify withdrawal amount.";

	public InsufficientFundsException() {
		super(message);
	}
	
	public InsufficientFundsException(String newMessage) {
		super(newMessage);
	}

	
	
}
