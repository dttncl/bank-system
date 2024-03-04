package bus;

import java.io.Serializable;

public class CreditAccount extends LineOfCreditAccount implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1617258126867537288L;
	private double transactionFee;

	public CreditAccount() {
		super();
		this.transactionFee = 0.25;
	}
	
	public CreditAccount(String accountNumber, Date openedDate, double availableBalance, double totalBalance) {
		super(accountNumber, openedDate, availableBalance, totalBalance);
		super.setType(EnumAccount.CreditAccount);
		this.transactionFee = 0.25;
	}

	public CreditAccount(String accountNumber, Date openedDate, double availableBalance, double totalBalance,
			double currentBalance, double lateFee, double transactionFee) throws InsufficientFundsException {
		super(accountNumber, openedDate, availableBalance, totalBalance, currentBalance, lateFee);
		super.setType(EnumAccount.CreditAccount);
		this.transactionFee = transactionFee;
	}

	public double getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(double transactionFee) {
		this.transactionFee = transactionFee;
	}

	@Override
	public String toString() {
		return super.toString() + "\n Transaction Fee: $" + transactionFee;
	}
	
	

}
