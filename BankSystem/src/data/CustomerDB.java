package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bus.*;

public class CustomerDB {
	static private Connection fortisDBConnection;
	
	static private String sqlStatement = null;	
	static private Statement dbStatement = null;
	
	static private String sqlQuery = null;
	static private ResultSet queryResultSet = null;
	
	static private ICustomer oneCustomer = null;
	
	public static void create(ICustomer oneCustomer) throws SQLException {
		
		fortisDBConnection = DBConnection.getConnection();
		
		sqlStatement = "INSERT INTO CUSTOMERS VALUES('" + oneCustomer.getUserId() + "','" + oneCustomer.getfName() + "','" + oneCustomer.getlName() + "','" + oneCustomer.getPin() + "','" + oneCustomer.getEmail()+ "','" + oneCustomer.getPhone() + "')";
		
		dbStatement = fortisDBConnection.createStatement();
		dbStatement.executeUpdate(sqlStatement);
		fortisDBConnection.commit();
		
	}
	
	// update customer PIN
	public static void update(ICustomer oneCustomer) throws SQLException {
		
		fortisDBConnection = DBConnection.getConnection();
		
		sqlStatement = "UPDATE CUSTOMERS SET PIN = '" + oneCustomer.getPin() + "' WHERE CUSTOMERID = '" + oneCustomer.getUserId() + "'";
	
		dbStatement = fortisDBConnection.createStatement();	
		dbStatement.executeUpdate(sqlStatement);
		fortisDBConnection.commit();	
									
	}
	
	public static void delete(String id) throws SQLException {
		
		fortisDBConnection = DBConnection.getConnection();
		
		sqlStatement = "DELETE FROM CUSTOMERS WHERE CUSTOMERID = '" + id + "'";
	
		dbStatement = fortisDBConnection.createStatement();	
		dbStatement.executeUpdate(sqlStatement);
		fortisDBConnection.commit();	

	}
	
	
	public static ICustomer search(String id) throws SQLException, SQLException, NumberFormatException, InsufficientFundsException{
		
		ICustomer oneCustomer = null;
		fortisDBConnection = DBConnection.getConnection();
		
		sqlQuery = "SELECT * FROM CUSTOMERS INNER JOIN CUSTOMERACCOUNTS USING (CUSTOMERID) WHERE CUSTOMERID = '" + id + "'";
		
		dbStatement = fortisDBConnection.createStatement();	
		queryResultSet = dbStatement.executeQuery(sqlQuery);
		
		if(queryResultSet.next()) {			
			oneCustomer = new Customer(queryResultSet.getString(1), queryResultSet.getString(4), queryResultSet.getString(2), queryResultSet.getString(3), queryResultSet.getString(5), queryResultSet.getString(6));
		}
		
		// select all accounts of customer
		ArrayList<IAccount> listAccs = AccountDB.selectAll(oneCustomer);
		//oneCustomer.setListOfAccounts(listAccs);
		
		// populate transactions by account
		//ArrayList<ITransaction> listTransacs = new ArrayList<ITransaction>();
		ArrayList<IAccount> listAccs2 = new ArrayList<IAccount>();

		for(IAccount x : listAccs) {
			x.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(x));
			//listTransacs.addAll(TransactionDB.selectAllTransactionsByAccount(x));	
			listAccs2.add(x);
		}
		
		oneCustomer.setListOfAccounts(listAccs2);
		
		/*
		ArrayList<ITransaction> listTransacs = new ArrayList<ITransaction>();
		for(IAccount x : listAccs) {
			listTransacs.addAll(TransactionDB.selectAllTransactionsByAccount(x));			
		}
		
		oneCustomer.setListOfTransactions(listTransacs);
		*/
		
		return oneCustomer;
	}
	
	
	public static ArrayList<ICustomer> selectAll() throws SQLException, SQLException, NumberFormatException, InsufficientFundsException{
		
		fortisDBConnection = DBConnection.getConnection();
		
		ArrayList<ICustomer> listAllCustomers = new ArrayList<ICustomer>();

		sqlQuery = "SELECT * FROM CUSTOMERS";
		
		dbStatement = fortisDBConnection.createStatement();	
		queryResultSet = dbStatement.executeQuery(sqlQuery);
		
		
		while(queryResultSet.next()) {		
			oneCustomer = new Customer(queryResultSet.getString(1), queryResultSet.getString(4), queryResultSet.getString(2), queryResultSet.getString(3), queryResultSet.getString(5), queryResultSet.getString(6));

			listAllCustomers.add(oneCustomer);			
		}	
		
		for(ICustomer cust : listAllCustomers) {
			cust.setListOfAccounts(AccountDB.selectAll(cust));
			
			ArrayList<IAccount> listAccs2 = new ArrayList<IAccount>();
			for(IAccount x : cust.getListOfAccounts()) {
				//listTransacs.addAll(TransactionDB.selectAllTransactionsByAccount(x));
				x.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(x));
				//listTransacs.addAll(TransactionDB.selectAllTransactionsByAccount(x));	
				listAccs2.add(x);
			}
			
			oneCustomer.setListOfAccounts(listAccs2);
			
			//cust.setListOfTransactions(TransactionDB.selectAllTransactionsByCustomer(cust));
		}
		
		/*
		ArrayList<ITransaction> listTransacs = new ArrayList<ITransaction>();
		for(IAccount x : oneCustomer.getListOfAccounts()) {
			listTransacs.addAll(TransactionDB.selectAllTransactionsByAccount(x));			
		}
		*/
		//oneCustomer.setListOfTransactions(listTransacs);
		
		
		return listAllCustomers;
	}	
}
