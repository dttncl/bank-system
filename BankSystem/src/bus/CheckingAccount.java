package bus;

import java.io.Serializable;

public class CheckingAccount extends Account implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8255514640688434193L;
	private int freeTransactions;
	private int nbTransactions;
	private double transactionFee;
	
	public CheckingAccount() {
		super();
		this.freeTransactions = 3;
		this.nbTransactions = 0;
		this.transactionFee = 0.05;
	}

	public CheckingAccount(String accountNumber, Date openedDate, double availableBalance,
			double totalBalance, double transactionFee) {
		super(accountNumber, EnumAccount.CheckingAccount, openedDate, availableBalance, totalBalance);
		this.freeTransactions = 3;
		this.nbTransactions = 0;
		this.transactionFee = transactionFee;
	}

	
	public int getFreeTransactions() {
		return freeTransactions;
	}

	public void setFreeTransactions(int freeTransactions) {
		this.freeTransactions = freeTransactions;
	}

	
	public int getNbTransactions() {
		return nbTransactions;
	}

	public void setNbTransactions(int nbTransactions) {
		this.nbTransactions = nbTransactions;
	}
	
	public double getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(double transactionFee) {
		this.transactionFee = transactionFee;
	}

	@Override
	public String toString() {
		return super.toString() + "\n------------------------\n Free Transactions: " + freeTransactions + "\n Transaction Fee: $" + transactionFee;
	}
	
	
	
}
