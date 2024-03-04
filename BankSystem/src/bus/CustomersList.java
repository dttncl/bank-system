package bus;

import java.io.IOException;
import java.util.ArrayList;

public class CustomersList {
	
	private static ArrayList<ICustomer> listCustomers = new ArrayList<ICustomer>();

	public static ArrayList<ICustomer> getListCustomers() {
		return listCustomers;
	}

	

	public static void setListCustomers(ArrayList<ICustomer> listCustomers) {
		CustomersList.listCustomers = listCustomers;
	}



	public static void add(ICustomer customer) {
		listCustomers.add(customer);
	}

	public static void remove(ICustomer customer) {
		listCustomers.remove(customer);
	}

	public static ICustomer search(String id, String pin) {
		
		for (ICustomer cust : listCustomers) {
			if (cust.getUserId().equals(id) && cust.getPin().equals(pin))
				return cust;
		}
		
		return null;		
	}
	
	public static ICustomer search(String id) {
		
		for (ICustomer cust : listCustomers) {
			if (cust.getUserId().equals(id))
				return cust;
		}
		
		return null;		
	}

	public static ArrayList<ICustomer> getAllCustomers() {
		if (listCustomers.isEmpty()) {
			return null;
		} else {
			return listCustomers;
		}
	}
	
	public static void writeToFile() throws IOException {		
		FileManager.serialize(listCustomers);
	}
	
	public static ArrayList<ICustomer> readFromFile() throws IOException, ClassNotFoundException {		
		return FileManager.deserialize();

	}
	

}
