package bus;

import java.util.Comparator;

public class PredicateAccountNumber implements Comparator<IAccount> {
	
	@Override
	public int compare(IAccount o1, IAccount o2)
	{
		if (o1.getAccountNumber().compareTo(o2.getAccountNumber()) > 0) {
			return 1;
		} else if (o1.getAccountNumber().compareTo(o2.getAccountNumber()) < 0) {
			return -1;
		} else {
			return 0;
		}
	}
}
