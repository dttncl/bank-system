package bus;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Customer extends User implements ICustomer, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<IAccount> listOfAccounts;
	
	// for random ids
	private Random random = new Random();
	private int id = random.nextInt((9999-1000) + 1) + 1000;
	
	public Customer() {
		super();
		this.listOfAccounts = new ArrayList<IAccount>();
		this.listOfAccounts.add(new CheckingAccount("A"+id,new Date(12,14,2023),0,0,0.05));
	}
	
	public String getUserId() {
		return super.getUserId();
	}
	
	public String getPin() {
		return super.getPin();
	}

	public String getEmail() {
		return super.getEmail();
	}

	public String getPhone() {
		return super.getPhone();
	}

	public Customer(String userId, String pin, String fName, String lName, String email, String phone) {
		super(userId, pin, fName, lName, email, phone);
		this.listOfAccounts = new ArrayList<IAccount>();
		this.listOfAccounts.add(new CheckingAccount("A"+id,new Date(12,14,2023),0,0,0.05));
	}

	public ArrayList<IAccount> getListOfAccounts() {
		return listOfAccounts;
	}

	public void setListOfAccounts(ArrayList<IAccount> listOfAccounts) {
		this.listOfAccounts = listOfAccounts;
	}

	@Override
	public String toString() {
		return super.toString() + "\n------------------------------------\nFortis Bank Customer: \nAccount(s): " + listOfAccounts.size() + "\n";

	}
	
	public IAccount searchAccount(EnumAccount oneAccountType) throws InsufficientFundsException {

		for (IAccount acc : listOfAccounts) {
		    if (acc != null) {
		        if (acc.getType() != null && acc.getType().equals(oneAccountType)) {
		        	return acc;
		        }
		    }
		}
		
		return null;

	}
	
}
