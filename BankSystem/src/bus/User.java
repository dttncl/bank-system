package bus;

import java.io.Serializable;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String pin;
	private String fName;
	private String lName;
	private String email;
	private String phone;
	
	public User() {
		super();
		this.userId = "XXXXX";
		this.pin = "1234";
		this.fName = "Unknown";
		this.lName = "Unknown";
		this.email = "Unknown";
		this.phone = "Unknown";
	}

	public User(String userId, String pin, String fName, String lName, String email, String phone) {
		super();
		this.userId = userId;
		this.pin = pin;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.phone = phone;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) throws NotInRangeException {
		
		if (Validator.isInvalidPIN(pin)) {
			NotInRangeException e = new NotInRangeException();
			throw e;	
		}
		
		this.pin = pin;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Account Holder: " + fName + " " + lName + "\nId: " + userId + "\nPIN: " + pin + "\nEmail: " + email + "\nPhone: " + phone;
	}
}
