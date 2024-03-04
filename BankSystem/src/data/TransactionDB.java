package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bus.*;


public class TransactionDB {
	static private Connection fortisDBConnection;
	
	static private String sqlStatement = null;	
	static private Statement dbStatement = null;
	
	static private String sqlQuery = null;
	static private ResultSet queryResultSet = null;
	
	static private ITransaction oneTransaction = null;

	
	public static void create(ITransaction oneTransaction) throws SQLException {
		
		fortisDBConnection = DBConnection.getConnection();
				
		// add to TRANSACTIONS table
		sqlStatement = "INSERT INTO TRANSACTIONS VALUES ('" + oneTransaction.getTransactionNumber() + "','" + oneTransaction.getTransactionType() + "','" + oneTransaction.getAccountNumber() + "', TO_DATE('"+ oneTransaction.getTransactionDate() +"','DD-MM-YYYY'),'" + oneTransaction.getDescription() + "',"+ oneTransaction.getAmount() + ")";		

		dbStatement = fortisDBConnection.createStatement();		
		dbStatement.executeUpdate(sqlStatement);
		fortisDBConnection.commit();
		
		
		// add to ACCOUNTTRANSACTIONS table
		sqlStatement = "INSERT INTO ACCOUNTTRANSACTIONS VALUES('" + oneTransaction.getAccountNumber() + "','" + oneTransaction.getTransactionNumber() + "')";
		
		dbStatement = fortisDBConnection.createStatement();		
		dbStatement.executeUpdate(sqlStatement);
		fortisDBConnection.commit();
		
	}
	
	public static ArrayList<ITransaction> selectAllTransactionsByAccount(IAccount oneAccount) throws SQLException, NumberFormatException, SQLException, InsufficientFundsException{
		
		fortisDBConnection = DBConnection.getConnection();
		
		sqlQuery = "SELECT ACCOUNTID,TRANSACTIONID, TRANSACTIONTYPE, TO_CHAR(TRANSACTIONDATE,'DD-MM-YYYY'), DESCRIPTION,AMOUNT FROM TRANSACTIONS INNER JOIN ACCOUNTS USING (ACCOUNTID) WHERE ACCOUNTID = '"+ oneAccount.getAccountNumber() + "'";
		
		dbStatement = fortisDBConnection.createStatement();	
		queryResultSet = dbStatement.executeQuery(sqlQuery);
		
		ArrayList<ITransaction> listOfTransactions = new ArrayList<ITransaction>();
		
		while(queryResultSet.next()) {
			EnumTransaction type = EnumTransaction.valueOf(queryResultSet.getString(3));
			
			if (type == EnumTransaction.CheckBalance) {
				oneTransaction = new Transaction(queryResultSet.getString(1),queryResultSet.getString(2),new Date(queryResultSet.getString(4)), queryResultSet.getString(5));
			} else {
				oneTransaction = new Transaction(type, queryResultSet.getString(1),queryResultSet.getString(2),new Date(queryResultSet.getString(4)), queryResultSet.getString(5), Double.parseDouble(queryResultSet.getString(6)));
			}
			
			listOfTransactions.add(oneTransaction);
		}
		
		return listOfTransactions;
	}
	
	public static ArrayList<ITransaction> selectAllTransactionsByCustomer(ICustomer oneCustomer) throws SQLException, NumberFormatException, SQLException, InsufficientFundsException{
		
		fortisDBConnection = DBConnection.getConnection();
		
		sqlQuery = "SELECT DISTINCT ACCOUNTID,TRANSACTIONS.TRANSACTIONID, TRANSACTIONTYPE, TO_CHAR(TRANSACTIONDATE,'DD-MM-YYYY'), DESCRIPTION,AMOUNT FROM TRANSACTIONS INNER JOIN CUSTOMERACCOUNTS USING (ACCOUNTID) WHERE CUSTOMERID = '"+ oneCustomer.getUserId() + "'";
		
		dbStatement = fortisDBConnection.createStatement();	
		queryResultSet = dbStatement.executeQuery(sqlQuery);
		
		ArrayList<ITransaction> listOfTransactions = new ArrayList<ITransaction>();
		
		while(queryResultSet.next()) {
			EnumTransaction type = EnumTransaction.valueOf(queryResultSet.getString(3));
			
			if (type == EnumTransaction.CheckBalance) {
				oneTransaction = new Transaction(queryResultSet.getString(1),queryResultSet.getString(2),new Date(queryResultSet.getString(4)), queryResultSet.getString(5));
			} else {
				oneTransaction = new Transaction(type, queryResultSet.getString(1),queryResultSet.getString(2),new Date(queryResultSet.getString(4)), queryResultSet.getString(5), Double.parseDouble(queryResultSet.getString(6)));
			}
			
			listOfTransactions.add(oneTransaction);
		}
		
		return listOfTransactions;
	}	
}
