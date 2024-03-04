package bus;

import java.util.regex.Pattern;

public class Validator {
	
	public static boolean hasInsufficientFunds(double value) throws InsufficientFundsException {
		
		return value < 0;
	}
	
	public static boolean isInvalidPIN(String value) throws NotInRangeException {
		
		return !Pattern.matches("^\\d{4}$", value);
	}
}