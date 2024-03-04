package bus;

import java.io.Serializable;

public class LineOfCreditAccount extends Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6566468496136630672L;
	private double currentBalance;
	private double lateFee;
	
	public LineOfCreditAccount() {
		super();
		this.currentBalance = 0;
		this.lateFee = 5;
	}
	
	
	
	public LineOfCreditAccount(String accountNumber, Date openedDate, double availableBalance,
			double totalBalance) {
		super(accountNumber, EnumAccount.LineOfCreditAccount, openedDate, availableBalance, totalBalance);
		this.currentBalance = 0;
		this.lateFee = 5;
	}



	public LineOfCreditAccount(String accountNumber, Date openedDate, double availableBalance,
			double totalBalance, double currentBalance, double lateFee) throws InsufficientFundsException {
		super(accountNumber, EnumAccount.LineOfCreditAccount, openedDate, availableBalance, totalBalance);
		this.currentBalance = currentBalance;
		this.lateFee = lateFee;
		CalculateAvailableBalance();
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public double getLateFee() {
		return lateFee;
	}

	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}

	@Override
	public String toString() {
		return super.toString() + "\n------------------------\n Current Bal: $" + currentBalance + "\n Late Fee: $" + lateFee;
	}
	
	private void CalculateAvailableBalance() throws InsufficientFundsException {
		double bal = getAvailableBalance() - currentBalance;
		setAvailableBalance(bal);
	}
	
}
