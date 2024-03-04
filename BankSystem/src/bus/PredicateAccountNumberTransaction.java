package bus;

import java.util.Comparator;

public class PredicateAccountNumberTransaction implements Comparator<ITransaction> {
	
	@Override
	public int compare(ITransaction o1, ITransaction o2)
	{
		int obj1 = Integer.parseInt(o1.getAccountNumber());
		int obj2 = Integer.parseInt(o2.getAccountNumber());

		
		if (obj1 > obj2) {
			return 1;
		} else if (obj1 < obj2) {
			return -1;
		} else {
			return 0;
		}
	}
}
