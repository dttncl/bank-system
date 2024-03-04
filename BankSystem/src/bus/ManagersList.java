package bus;

import java.io.IOException;
import java.util.ArrayList;

public class ManagersList {

	private static ArrayList<BankManager> listManagers = new ArrayList<BankManager>();

	public static void add(BankManager manager) {
		listManagers.add(manager);
	}
	
	
	public static ArrayList<BankManager> getListManagers() {
		return listManagers;
	}


	public static void setListManagers(ArrayList<BankManager> listManagers) {
		ManagersList.listManagers = listManagers;
	}


	public static BankManager search(String id, String pin) {
		
		for (BankManager manager : listManagers) {
			if (manager.getUserId().equals(id) && manager.getPin().equals(pin))
				return manager;
		}
		
		return null;		
	}
	
	public static void writeToFile() throws IOException {		
		FileManager.serializeManagers(listManagers);
	}
	
	public static ArrayList<BankManager> readFromFile() throws IOException, ClassNotFoundException {		
		return FileManager.deserializeManagers();
	}
	
}
