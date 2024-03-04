package bus;

import java.util.Comparator;

public class PredicateTransactionNumber implements Comparator<ITransaction> {
	
	@Override
	public int compare(ITransaction o1, ITransaction o2)
	{
		int obj1 = Integer.parseInt(o1.getTransactionNumber());
		int obj2 = Integer.parseInt(o2.getTransactionNumber());

		
		if (obj1 > obj2) {
			return 1;
		} else if (obj1 < obj2) {
			return -1;
		} else {
			return 0;
		}
	}
}
