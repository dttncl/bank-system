package bus;

public interface ITransaction {
	public void setTransactionNumber(String transactionNumber);
	public String getTransactionNumber();
	public void setTransactionType(EnumTransaction transactionType);
	public EnumTransaction getTransactionType();
	public Date getTransactionDate();
	public String getDescription();

	public String getAccountNumber();
	public void setAccountNumber(String accountNumber);
	
	public void setAmount(double amount);
	public double getAmount();
}
