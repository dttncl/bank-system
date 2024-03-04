package bus;

import java.io.Serializable;

public class CurrencyAccount extends Account implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4399608348788906274L;
	private EnumCurrency currency;
	private double exchangeRate;
	
	public CurrencyAccount() {
		super();
	}

	public CurrencyAccount(String accountNumber, Date openedDate, double availableBalance,
			double totalBalance, EnumCurrency currency, double exchangeRate) throws InsufficientFundsException {
		super(accountNumber, EnumAccount.CurrencyAccount, openedDate, availableBalance, totalBalance);
		this.currency = currency;
		this.exchangeRate = exchangeRate;
		ConvertBalance();
	}

	public EnumCurrency getCurrency() {
		return currency;
	}

	public void setCurrency(EnumCurrency currency) {
		this.currency = currency;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	

	@Override
	public String toString() {
		return super.toString() + "\n------------------------\n Currency: " + currency + "\n Exchange Rate: " + exchangeRate + "CAD";
	}
	
	
	private void ConvertBalance() throws InsufficientFundsException {
		
		switch(currency) {
		case USD:
			setAvailableBalance(getAvailableBalance()*exchangeRate);
			setTotalBalance(getTotalBalance()*exchangeRate);
			break;
		case JPY:
			setAvailableBalance(getAvailableBalance()*exchangeRate);
			setTotalBalance(getTotalBalance()*exchangeRate);
			break;
		case EUR:
			setAvailableBalance(getAvailableBalance()*exchangeRate);
			setTotalBalance(getTotalBalance()*exchangeRate);
			break;
		default:
			
			break;
		}
		
	}
	
	
}
