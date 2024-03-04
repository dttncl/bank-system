package bus;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import data.AccountDB;
import data.TransactionDB;

public class Account implements IAccount, Serializable {

	private static final long serialVersionUID = 8609326661658977428L;
	private String accountNumber;
	private EnumAccount type;
	private Date openedDate;
	private double availableBalance;
	private double totalBalance;
	private ArrayList<ITransaction> listOfTransactions;
	
	public Account() {
		super();
		this.accountNumber = "AXXXX";
		this.type = EnumAccount.Undefined;
		this.openedDate = new Date();
		this.availableBalance = 0;
		this.totalBalance = 0;
		this.listOfTransactions = new ArrayList<ITransaction>();
	}

	public Account(String accountNumber, EnumAccount type, Date openedDate, double availableBalance,
			double totalBalance) {
		super();
		this.accountNumber = accountNumber;
		this.type = type;
		this.openedDate = openedDate;
		this.availableBalance = availableBalance;
		this.totalBalance = totalBalance;
		this.listOfTransactions = new ArrayList<ITransaction>();
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public EnumAccount getType() {
		return type;
	}

	public void setType(EnumAccount type) {
		this.type = type;
	}

	public Date getOpenedDate() {
		return openedDate;
	}

	public void setOpenedDate(Date openedDate) {
		this.openedDate = openedDate;
	}

	public double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(double availableBalance) throws InsufficientFundsException {
				
		if (Validator.hasInsufficientFunds(availableBalance)) {
			InsufficientFundsException e = new InsufficientFundsException();
			throw e;
			
		}
		
		this.availableBalance = availableBalance;
		
	}

	public double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}
	
	public ArrayList<ITransaction> getListOfTransactions() {
		return listOfTransactions;
	}
	

	public void setListOfTransactions(ArrayList<ITransaction> listOfTransactions) {
		this.listOfTransactions = listOfTransactions;
	}

	@Override
	public String toString() {
		return "\nAccount Number: " + accountNumber + "\nAccount Type: " + type + "\n Opened: " + openedDate
				+ "\n Available Bal: $" + availableBalance + "\n Total Bal: $" + totalBalance + "\nTransaction(s): " + listOfTransactions.size();
	}
	
	public void addTransaction(ITransaction oneTransaction, double transacFee) throws SQLException, InsufficientFundsException {
		
		Double amt = oneTransaction.getAmount() + transacFee;
		EnumTransaction trans = oneTransaction.getTransactionType();
		String accNum = oneTransaction.getAccountNumber();
		
		if (this.getAccountNumber().equals(accNum)) {

			if (this.getType() == EnumAccount.LineOfCreditAccount || this.getType() == EnumAccount.CreditAccount) {
				if (trans == EnumTransaction.Withdraw) {
					makeCredit(amt);
				} else if (trans == EnumTransaction.Deposit) {
					makeCreditPayment(amt);
				}
			} else {
				if (trans == EnumTransaction.Withdraw) {
					makeWithdraw(amt);
				} else if (trans == EnumTransaction.Deposit) {
					makeDeposit(amt);
				}
			}

			if (trans == EnumTransaction.CheckBalance) {
				checkBalance(this);
			} 				
		}
				
		TransactionDB.create(oneTransaction);
		this.listOfTransactions = TransactionDB.selectAllTransactionsByAccount(this);
	}
	
	private void makeDeposit(double amount) throws InsufficientFundsException, SQLException {

		this.setAvailableBalance(this.getAvailableBalance() + amount);
		this.setTotalBalance(this.getAvailableBalance());
		
		AccountDB.update(this);
	}
	
	private void makeCreditPayment(double amount) throws InsufficientFundsException, SQLException {
		this.setAvailableBalance(this.getAvailableBalance() + amount);
		
		AccountDB.update(this);
	}
	
	private void makeCredit(double amount) throws InsufficientFundsException, SQLException {
		this.setAvailableBalance(this.getAvailableBalance() - amount);
		((LineOfCreditAccount) this).setCurrentBalance(amount);
		
		AccountDB.update(this);
	}
	
	private void makeWithdraw(double amount) throws InsufficientFundsException, SQLException {

		this.setAvailableBalance(this.getAvailableBalance() - amount);
		this.setTotalBalance(this.getAvailableBalance());
		
		AccountDB.update(this);
	}
	
	private String checkBalance(IAccount updateAccount) {
		return updateAccount.toString();
	}
	
}
