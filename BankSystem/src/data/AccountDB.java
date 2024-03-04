package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bus.*;

public class AccountDB {
	static private Connection fortisDBConnection;
	
	static private String sqlStatement = null;	
	static private Statement dbStatement = null;
	
	static private String sqlQuery = null;
	static private ResultSet queryResultSet = null;
	
	static private IAccount oneAccount = null;
	
	public static void create(IAccount oneAccount, ICustomer oneCustomer) throws SQLException {
		
		fortisDBConnection = DBConnection.getConnection();
		
		if (oneAccount.getType() == EnumAccount.CurrencyAccount) {
			EnumCurrency currency = ((CurrencyAccount)oneAccount).getCurrency();

			sqlStatement = "INSERT INTO ACCOUNTS VALUES('" + oneAccount.getAccountNumber() + "','" + oneAccount.getType() + "', TO_DATE('" + oneAccount.getOpenedDate() + "', 'DD-MM-YYYY'), " + oneAccount.getAvailableBalance() + "," + oneAccount.getTotalBalance() + ",'" + currency + "')";
			dbStatement = fortisDBConnection.createStatement();		
			dbStatement.executeUpdate(sqlStatement);
			fortisDBConnection.commit();
		} else {
			// add to ACCOUNT table
			sqlStatement = "INSERT INTO ACCOUNTS VALUES('" + oneAccount.getAccountNumber() + "','" + oneAccount.getType() + "', TO_DATE('" + oneAccount.getOpenedDate() + "', 'DD-MM-YYYY'), " + oneAccount.getAvailableBalance() + "," + oneAccount.getTotalBalance() + ",'CAD')";
			dbStatement = fortisDBConnection.createStatement();		
			dbStatement.executeUpdate(sqlStatement);
			fortisDBConnection.commit();
			
		}
		
		// add to CUSTOMERACCOUNTS table
		sqlStatement = "INSERT INTO CUSTOMERACCOUNTS VALUES('" + oneCustomer.getUserId() + "','" + oneAccount.getAccountNumber() + "')";
		dbStatement = fortisDBConnection.createStatement();		
		dbStatement.executeUpdate(sqlStatement);
		fortisDBConnection.commit();
		
	}
	
	public static void update(IAccount oneAccount) throws SQLException {
		
		fortisDBConnection = DBConnection.getConnection();
		
		sqlStatement = "UPDATE ACCOUNTS SET AVAILABLEBALANCE = " + oneAccount.getAvailableBalance() +" WHERE ACCOUNTID = '" + oneAccount.getAccountNumber() + "'";
		dbStatement = fortisDBConnection.createStatement();	
		dbStatement.executeUpdate(sqlStatement);
		fortisDBConnection.commit();
		
		sqlStatement = "UPDATE ACCOUNTS SET TOTALBALANCE = " + oneAccount.getTotalBalance() +" WHERE ACCOUNTID = '" + oneAccount.getAccountNumber() + "'";
		dbStatement = fortisDBConnection.createStatement();	
		dbStatement.executeUpdate(sqlStatement);
		fortisDBConnection.commit();	
									
	}
	
	public static void delete(String id) throws SQLException {
		
		fortisDBConnection = DBConnection.getConnection();
		
		sqlStatement = "DELETE FROM ACCOUNTS WHERE ACCOUNTID = '" + id + "'" ;
	
		dbStatement = fortisDBConnection.createStatement();	
		dbStatement.executeUpdate(sqlStatement);
		fortisDBConnection.commit();	

	}
	
	
	public static IAccount search(ICustomer oneCustomer, EnumAccount type) throws SQLException, SQLException, NumberFormatException, InsufficientFundsException{
		
		IAccount oneAccount = null;
		fortisDBConnection = DBConnection.getConnection();
		
		sqlQuery = "SELECT ACCOUNTID, CUSTOMERID,ACCOUNTTYPE, TO_CHAR(OPENEDDATE,'DD-MM-YYYY'),AVAILABLEBALANCE,TOTALBALANCE,CURRENCY FROM CUSTOMERACCOUNTS INNER JOIN ACCOUNTS USING (ACCOUNTID) WHERE CUSTOMERID = '" + oneCustomer.getUserId() + "' AND ACCOUNTTYPE = '" + type + "'";
		dbStatement = fortisDBConnection.createStatement();	
		queryResultSet = dbStatement.executeQuery(sqlQuery);
		
		if (queryResultSet.next()) {
			
			EnumAccount acctype = EnumAccount.valueOf(queryResultSet.getString(3));
			
			switch (acctype) {
			case CheckingAccount:
				oneAccount = new CheckingAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)),0.05);
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));
				break;
			case SavingsAccount:
				oneAccount = new SavingsAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)));
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			case CurrencyAccount:
				EnumCurrency currency = EnumCurrency.valueOf(queryResultSet.getString(7));
				double exrate = 1;
				
				switch (currency) {
				case USD:
					exrate = 0.74;
					break;
				case JPY:
					exrate = 105.95;
					break;
				case EUR:
					exrate = 0.68;
					break;
				default:
					break;
				}
				
				oneAccount = new CurrencyAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)), currency, exrate);
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			case LineOfCreditAccount:
				oneAccount = new LineOfCreditAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)));
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			case CreditAccount:
				oneAccount = new CreditAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)));
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			default:
				break;
			}
		}
		
		
		
		return oneAccount;
	}
	
	
	public static IAccount search(ICustomer oneCustomer, String accNum) throws SQLException, SQLException, NumberFormatException, InsufficientFundsException{
		
		IAccount oneAccount = null;
		fortisDBConnection = DBConnection.getConnection();
		
		sqlQuery = "SELECT ACCOUNTID, CUSTOMERID,ACCOUNTTYPE, TO_CHAR(OPENEDDATE,'DD-MM-YYYY'),AVAILABLEBALANCE,TOTALBALANCE,CURRENCY FROM CUSTOMERACCOUNTS INNER JOIN ACCOUNTS USING (ACCOUNTID) WHERE CUSTOMERID = '" + oneCustomer.getUserId() + "' AND ACCOUNTID = '" + accNum + "'";
		dbStatement = fortisDBConnection.createStatement();	
		queryResultSet = dbStatement.executeQuery(sqlQuery);
		
		if (queryResultSet.next()) {
			EnumAccount acctype = EnumAccount.valueOf(queryResultSet.getString(3));
			
			switch (acctype) {
			case CheckingAccount:
				oneAccount = new CheckingAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)),0.05);
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			case SavingsAccount:
				oneAccount = new SavingsAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)));
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			case CurrencyAccount:
				EnumCurrency currency = EnumCurrency.valueOf(queryResultSet.getString(7));
				double exrate = 1;
				
				switch (currency) {
				case USD:
					exrate = 0.74;
					break;
				case JPY:
					exrate = 105.95;
					break;
				case EUR:
					exrate = 0.68;
					break;
				default:
					break;
				}
				
				oneAccount = new CurrencyAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)), currency, exrate);
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			case LineOfCreditAccount:
				oneAccount = new LineOfCreditAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)));
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			case CreditAccount:
				oneAccount = new CreditAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)));
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			default:
				break;
			}
		}
		
		return oneAccount;
	}
	
	
	public static ArrayList<IAccount> selectAll(ICustomer oneCustomer) throws SQLException, NumberFormatException, SQLException, InsufficientFundsException{
		
		fortisDBConnection = DBConnection.getConnection();
		
		sqlQuery = "SELECT ACCOUNTID, CUSTOMERID,ACCOUNTTYPE, TO_CHAR(OPENEDDATE,'DD-MM-YYYY'),AVAILABLEBALANCE,TOTALBALANCE,CURRENCY FROM CUSTOMERACCOUNTS INNER JOIN ACCOUNTS USING (ACCOUNTID) WHERE CUSTOMERID = '" + oneCustomer.getUserId() + "'";
		
		dbStatement = fortisDBConnection.createStatement();	
		queryResultSet = dbStatement.executeQuery(sqlQuery);
		
		ArrayList<IAccount> listAllAccounts = new ArrayList<IAccount>();
		
		while(queryResultSet.next()) {
			
			EnumAccount type = EnumAccount.valueOf(queryResultSet.getString(3));
			
			switch (type) {
			case CheckingAccount:
				oneAccount = new CheckingAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)),0.05);
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			case SavingsAccount:
				oneAccount = new SavingsAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)));
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			case CurrencyAccount:
				EnumCurrency currency = EnumCurrency.valueOf(queryResultSet.getString(7));
				double exrate = 1;
				
				switch (currency) {
				case USD:
					exrate = 0.74;
					break;
				case JPY:
					exrate = 105.95;
					break;
				case EUR:
					exrate = 0.68;
					break;
				default:
					break;
				}
				
				oneAccount = new CurrencyAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)), currency, exrate);
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			case LineOfCreditAccount:
				oneAccount = new LineOfCreditAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)));
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			case CreditAccount:
				oneAccount = new CreditAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)));
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			default:
				break;
			}
			
			
			listAllAccounts.add(oneAccount);
		}
		
		return listAllAccounts;
	}
	
	public static ArrayList<IAccount> selectAllAccounts() throws SQLException, NumberFormatException, SQLException, InsufficientFundsException{
		
		fortisDBConnection = DBConnection.getConnection();
		
		sqlQuery = "SELECT ACCOUNTID, CUSTOMERID,ACCOUNTTYPE, TO_CHAR(OPENEDDATE,'DD-MM-YYYY'),AVAILABLEBALANCE,TOTALBALANCE,CURRENCY FROM CUSTOMERACCOUNTS INNER JOIN ACCOUNTS USING (ACCOUNTID)";
		
		dbStatement = fortisDBConnection.createStatement();	
		queryResultSet = dbStatement.executeQuery(sqlQuery);
		
		ArrayList<IAccount> listAllAccounts = new ArrayList<IAccount>();
		
		while(queryResultSet.next()) {
			
			EnumAccount type = EnumAccount.valueOf(queryResultSet.getString(3));
			
			switch (type) {
			case CheckingAccount:
				oneAccount = new CheckingAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)),0.05);
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			case SavingsAccount:
				oneAccount = new SavingsAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)));
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			case CurrencyAccount:
				EnumCurrency currency = EnumCurrency.valueOf(queryResultSet.getString(7));
				double exrate = 1;
				
				switch (currency) {
				case USD:
					exrate = 0.74;
					break;
				case JPY:
					exrate = 105.95;
					break;
				case EUR:
					exrate = 0.68;
					break;
				default:
					break;
				}
				
				oneAccount = new CurrencyAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)), currency, exrate);
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			case LineOfCreditAccount:
				oneAccount = new LineOfCreditAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)));
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			case CreditAccount:
				oneAccount = new CreditAccount(queryResultSet.getString(1), new Date(queryResultSet.getString(4)), Double.parseDouble(queryResultSet.getString(5)),
						Double.parseDouble(queryResultSet.getString(6)));
				oneAccount.setListOfTransactions(TransactionDB.selectAllTransactionsByAccount(oneAccount));

				break;
			default:
				break;
			}
			
			
			listAllAccounts.add(oneAccount);
		}
		
		return listAllAccounts;
	}
	
}
