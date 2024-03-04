package bus;

import java.util.ArrayList;

public class TransactionsList {
	
	private static ArrayList<ITransaction> listTransactions = new ArrayList<ITransaction>();

	public static void setListTransactions(ArrayList<ITransaction> listTransactions) {
		TransactionsList.listTransactions = listTransactions;
	}

	public static void add(ITransaction transaction) {
		listTransactions.add(transaction);
	}
	
	public static ITransaction search(String id) {
		
		for (ITransaction trns : listTransactions) {
			if (trns.getTransactionNumber().equals(id))
				return trns;
		}
		
		return null;		
	}
	
	public static ArrayList<ITransaction> getAllTransactions() {
		if (listTransactions.isEmpty()) {
			return null;
		} else {
			return listTransactions;
		}
	}
	
}
