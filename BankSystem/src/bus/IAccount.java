package bus;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IAccount {
	public EnumAccount getType();
	public String getAccountNumber();	
	public Date getOpenedDate();
	public double getAvailableBalance();
	public double getTotalBalance(); 
	public void addTransaction(ITransaction oneTransaction, double transacFee) throws SQLException, InsufficientFundsException;
	public void setAvailableBalance(double availableBalance) throws InsufficientFundsException;
	public void setTotalBalance(double totalBalance);
	public void setListOfTransactions(ArrayList<ITransaction> listOfTransactions);
	public ArrayList<ITransaction> getListOfTransactions();
}
