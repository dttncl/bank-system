package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static Connection getConnection()
	{
         Connection myConnection = null;		
	     String userName, password, service, url;
	
		 userName = "c##fortisbankdb" ;
		 password = "123" ;
		 service = "localhost" ;
		
		 url = "jdbc:oracle:thin:";
		
		try {
			// build connection string
			myConnection = DriverManager.getConnection(url + userName + "/" + password + "@" + service);
	     	//System.out.println(" Connection successful");
		
		} catch (SQLException err) {
			System.out.println(" Connection failed  ");
			err.printStackTrace();
		}
		
		return myConnection;		
	}
	
}
