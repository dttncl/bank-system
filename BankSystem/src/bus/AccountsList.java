package bus;

import java.util.ArrayList;

public class AccountsList {
	
	private static ArrayList<IAccount> listAccounts = new ArrayList<IAccount>();

	public static void setListAccounts(ArrayList<IAccount> listAccounts) {
		AccountsList.listAccounts = listAccounts;
	}

	public static void add(IAccount account) {
		listAccounts.add(account);
	}

	public static void remove(IAccount account) {
		listAccounts.remove(account);
	}
	
	public static IAccount searchById(String id) {
		
		for (IAccount acc : listAccounts) {
			if (acc.getAccountNumber().equals(id))
				return acc;
		}
		
		return null;		
	}
	
	
	public static IAccount searchByType(EnumAccount oneAccount) throws InsufficientFundsException {
		
		IAccount found = null;
		
		for (IAccount acc : listAccounts) {
			if (acc.getType().equals(oneAccount))
				found = acc;
		}

		return found;		
	}
	
	public static ArrayList<IAccount> getAllAccounts() {
		if (listAccounts.isEmpty()) {
			return null;
		} else {
			return listAccounts;
		}
	}
	
	public static ArrayList<CheckingAccount> getCheckingAccounts() {
		if (listAccounts.isEmpty()) {
			return null;
		} else {
			ArrayList<CheckingAccount> listFilteredAccounts = new ArrayList<CheckingAccount>();
			for (IAccount acc : listAccounts) {
				if (acc instanceof CheckingAccount) {
					listFilteredAccounts.add((CheckingAccount) acc);
				}
			}
			
			return listFilteredAccounts;
		}
	}
	
	public static ArrayList<SavingsAccount> getSavingsAccounts() {
		if (listAccounts.isEmpty()) {
			return null;
		} else {
			ArrayList<SavingsAccount> listFilteredAccounts = new ArrayList<SavingsAccount>();
			for (IAccount acc : listAccounts) {
				if (acc instanceof SavingsAccount) {
					listFilteredAccounts.add((SavingsAccount) acc);
				}
			}
			
			return listFilteredAccounts;
		}
	}

	public static ArrayList<CurrencyAccount> getCurrencyAccounts() {
		if (listAccounts.isEmpty()) {
			return null;
		} else {
			ArrayList<CurrencyAccount> listFilteredAccounts = new ArrayList<CurrencyAccount>();
			for (IAccount acc : listAccounts) {
				if (acc instanceof CurrencyAccount) {
					listFilteredAccounts.add((CurrencyAccount) acc);
				}
			}
			
			return listFilteredAccounts;
		}
	}

	public static ArrayList<LineOfCreditAccount> getLineOfCreditAccounts() {
		if (listAccounts.isEmpty()) {
			return null;
		} else {
			ArrayList<LineOfCreditAccount> listFilteredAccounts = new ArrayList<LineOfCreditAccount>();
			for (IAccount acc : listAccounts) {
				if (acc instanceof LineOfCreditAccount) {
					listFilteredAccounts.add((LineOfCreditAccount) acc);
				}
			}
			
			return listFilteredAccounts;
		}
	}

	public static ArrayList<CreditAccount> getCreditAccounts() {
		if (listAccounts.isEmpty()) {
			return null;
		} else {
			ArrayList<CreditAccount> listFilteredAccounts = new ArrayList<CreditAccount>();
			for (IAccount acc : listAccounts) {
				if (acc instanceof CreditAccount) {
					listFilteredAccounts.add((CreditAccount) acc);
				}
			}
			
			return listFilteredAccounts;
		}
	}
	
	
	public static ArrayList<IAccount> getAllAccountsByCustomer(ICustomer oneCustomer) {
		
		ArrayList<IAccount> listCustomerAccounts = oneCustomer.getListOfAccounts();
		
		if (listCustomerAccounts.isEmpty()) {
			return null;
		} else {
			return listCustomerAccounts;
		}
	}
		
}

