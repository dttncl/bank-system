package bus;

import java.io.Serializable;

public class SavingsAccount extends Account implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5500776181950945679L;
	private double interestRate;
	private double annualGain;
	
	public SavingsAccount() {
		super();
		this.interestRate = 5;
		this.annualGain = 5;
	}
	
	
	public SavingsAccount(String accountNumber, Date openedDate, double availableBalance,
			double totalBalance) {
		super(accountNumber, EnumAccount.SavingsAccount, openedDate, availableBalance, totalBalance);
		this.interestRate = 5;
		this.annualGain = 5;
	}



	public SavingsAccount(String accountNumber, Date openedDate, double availableBalance,
			double totalBalance, double interestRate, double annualGain) {
		super(accountNumber, EnumAccount.SavingsAccount, openedDate, availableBalance, totalBalance);
		this.interestRate = interestRate;
		this.annualGain = annualGain;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getAnnualGain() {
		return annualGain;
	}

	public void setAnnualGain(double annualGain) {
		this.annualGain = annualGain;
	}

	@Override
	public String toString() {
		return super.toString() + "\n------------------------\n Interest Rate(%): " + interestRate + "\n Annual Gain(%): " + annualGain;
	}
	
	
}
