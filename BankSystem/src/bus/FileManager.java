package bus;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileManager {
	private static String filePath = "src//data//listOfCustomers.ser";
	private static String filePathManager = "src//data//listOfManagers.ser";


	public static void serialize(ArrayList<ICustomer> listConsoleCustomers) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(filePath);			
		ObjectOutputStream oos = new ObjectOutputStream(fos);		
		oos.writeObject(listConsoleCustomers);		
		oos.close();
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<ICustomer> deserialize() throws IOException, ClassNotFoundException
	{
		ArrayList<ICustomer> listFromFile;
		FileInputStream fis = new FileInputStream(filePath);
		ObjectInputStream ois = new ObjectInputStream(fis);		
		listFromFile = (ArrayList<ICustomer>) ois.readObject();
		ois.close();
		return listFromFile;		
	}

	public static void serializeManagers(ArrayList<BankManager> listConsoleManagers) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(filePathManager);			
		ObjectOutputStream oos = new ObjectOutputStream(fos);		
		oos.writeObject(listConsoleManagers);		
		oos.close();
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<BankManager> deserializeManagers() throws IOException, ClassNotFoundException
	{
		ArrayList<BankManager> listFromFile;
		FileInputStream fis = new FileInputStream(filePathManager);
		ObjectInputStream ois = new ObjectInputStream(fis);		
		listFromFile = (ArrayList<BankManager>) ois.readObject();
		ois.close();
		return listFromFile;		
	}
}
