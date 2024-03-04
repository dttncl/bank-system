package bus;

import java.sql.SQLException;
import java.util.ArrayList;

import data.AccountDB;
import data.CustomerDB;

public class BankManager extends User {


	private static final long serialVersionUID = -8620562798783655853L;
	private ArrayList<ICustomer> listOfCustomers;

	public BankManager() {
		super();
		this.listOfCustomers = new ArrayList<ICustomer>();
	}

	
	
	public BankManager(String userId, String pin, String fName, String lName, String email, String phone) {
		super(userId, pin, fName, lName, email, phone);
		this.listOfCustomers = new ArrayList<ICustomer>();

	}


	public BankManager(String userId, String pin, String fName, String lName, String email, String phone, ArrayList<ICustomer> listOfCustomers) {
		super(userId, pin, fName, lName, email, phone);
		this.listOfCustomers = listOfCustomers;
	}



	public ArrayList<ICustomer> getListOfCustomers() {
		return listOfCustomers;
	}



	public void setListOfCustomers(ArrayList<ICustomer> listOfCustomers) {
		this.listOfCustomers = listOfCustomers;
	}

	public ArrayList<IAccount> getAllCustomerAccounts(ICustomer oneCustomer) {
		return AccountsList.getAllAccountsByCustomer(oneCustomer);
	}	


	@Override
	public String toString() {
		return "Fortis Bank Manager:\n" + super.toString();
	}
	
	public void createCustomer(ICustomer oneCustomer) throws SQLException {

		IAccount oneAccount = oneCustomer.getListOfAccounts().get(0);
		
		CustomerDB.create(oneCustomer);
		AccountDB.create(oneAccount, oneCustomer);

	}
	
	public void removeCustomer(ICustomer oneCustomer) throws SQLException {
		CustomerDB.delete(oneCustomer.getUserId());
	}
	
	public ICustomer searchCustomer(String cId) throws NumberFormatException, SQLException, InsufficientFundsException {
		return CustomerDB.search(cId);
	}
	
	/*
	// used in console application, return boolean
	public boolean addAccount(ICustomer oneCustomer, IAccount oneAccount) throws InsufficientFundsException, NumberFormatException, SQLException {
		
		boolean exists = false;
		ArrayList<IAccount> listCustomerAccounts = AccountsList.getAllAccountsByCustomer(oneCustomer);
		
		for (IAccount acc : listCustomerAccounts) {
		    if (acc != null) {
		        if (acc.getType() != null && acc.getType().equals(oneAccount.getType())) {
		            exists = true;
		            break;
		        }
		    }
		}
		
		if (!exists) {
			AccountsList.add(oneAccount);
			ArrayList<IAccount> currentAccounts = oneCustomer.getListOfAccounts();
			currentAccounts.add(oneAccount);
			oneCustomer.setListOfAccounts(currentAccounts);
		}
		
		return !exists;
	}
	*/

	public void addAccount(ICustomer oneCustomer, IAccount oneAccount) throws InsufficientFundsException, NumberFormatException, SQLException {

		IAccount record = AccountDB.search(oneCustomer, oneAccount.getType());
		
		if (record == null) {
			//AccountsList.setListAccounts(oneCustomer.getListOfAccounts());
			//AccountsList.add(oneAccount);
			ArrayList<IAccount> listExistingAccs = oneCustomer.getListOfAccounts();
			listExistingAccs.add(oneAccount);
			oneCustomer.setListOfAccounts(listExistingAccs);
			
			AccountDB.create(oneAccount, oneCustomer);
		}
	}
	
	public void removeAccount(ICustomer oneCustomer, String accNum) throws NumberFormatException, SQLException, InsufficientFundsException {
		/*
		// used in console application
		IAccount record = AccountsList.searchById(accNum);
		
		if (record != null) {
			AccountsList.setListAccounts(oneCustomer.getListOfAccounts());
			AccountsList.remove(record);
		}
		*/
		
		IAccount record = AccountDB.search(oneCustomer, accNum);
		
		if (record != null) {
			AccountDB.delete(accNum);
		}
	}
	
	


}
