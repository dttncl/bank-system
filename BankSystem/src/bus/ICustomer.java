package bus;

import java.util.ArrayList;

public interface ICustomer {
	public String getUserId();
	public String getfName();
	public String getlName();
	public String getPin();
	public String getEmail();
	public String getPhone();
	public ArrayList<IAccount> getListOfAccounts();
	public IAccount searchAccount(EnumAccount oneAccount) throws InsufficientFundsException;
	public void setListOfAccounts(ArrayList<IAccount> listOfAccounts);
}
