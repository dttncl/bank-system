package bus;

import java.util.Random;
import java.io.Serializable;


public class Transaction implements ITransaction, Serializable {
	
	//private static int counter = 10;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String transactionNumber;
	private EnumTransaction transactionType;
	private String accountNumber;

	private Date transactionDate;
	private String description;
	private double amount;
	
	private Random random = new Random();
	private int id = random.nextInt((999-100) + 1) + 100;
	
	public Transaction() {
		super();
		this.transactionNumber = "T"+id;
		this.transactionType = EnumTransaction.Undefined;
		this.accountNumber = "AXXXX";
		this.transactionDate = new Date();
		this.description = "Successful Transaction";
		this.amount = 0;
	}
	
	
	// check balance
	public Transaction(String accountNumber,String transactionNumber,Date transactionDate, String description) {
		super();
		this.transactionNumber = transactionNumber;
		this.transactionType = EnumTransaction.CheckBalance;
		this.accountNumber = accountNumber;
		this.transactionDate = transactionDate;
		this.description = description;
	}


	// withdraw and deposit
	public Transaction(EnumTransaction transactionType, String accountNumber,String transactionNumber,Date transactionDate, String description, double amount) {
		super();
		this.transactionNumber = transactionNumber;
		this.transactionType = transactionType;
		this.accountNumber = accountNumber;
		this.transactionDate = transactionDate;
		this.description = description;
		this.amount = amount;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public EnumTransaction getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(EnumTransaction transactionType) {
		this.transactionType = transactionType;
	}
	
	public Date getTransactionDate() {
		return transactionDate;
	}


	public String getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "\nTransaction " + transactionNumber + " - " + transactionType
				+ "\n From: " + accountNumber + "\n Date: " + transactionDate + "\n "
				+ description + "\n Amount: $" + amount;
	}
	
	
}
