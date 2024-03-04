package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bus.BankManager;

public class ManagerDB {

	static private Connection fortisDBConnection;
	
	static private Statement dbStatement = null;
	static private String sqlQuery = null;
	static private ResultSet queryResultSet = null;
		
	public static BankManager searchById(String id) throws SQLException, SQLException, NumberFormatException {
		
		BankManager oneManager = null;
		
		fortisDBConnection = DBConnection.getConnection();
		
		sqlQuery = "SELECT * FROM MANAGERS WHERE MANAGERID = '" + id + "'";
		dbStatement = fortisDBConnection.createStatement();	
		queryResultSet = dbStatement.executeQuery(sqlQuery);
		
		if (queryResultSet.next()) {
			oneManager = new BankManager(queryResultSet.getString(1), queryResultSet.getString(4), queryResultSet.getString(2), queryResultSet.getString(3), queryResultSet.getString(5), queryResultSet.getString(6));
		}
		
		return oneManager;
	}
}
