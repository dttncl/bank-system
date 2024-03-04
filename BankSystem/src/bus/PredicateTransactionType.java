package bus;

import java.util.Comparator;

public class PredicateTransactionType implements Comparator<ITransaction> {
	
	@Override
	public int compare(ITransaction o1, ITransaction o2)
	{
		String obj1 = o1.getTransactionType().name();
		String obj2 = o2.getTransactionType().name();
		
		if (obj1.compareTo(obj2) > 0) {
			return 1;
		} else if (obj1.compareTo(obj2) < 0) {
			return -1;
		} else {
			return 0;
		}
	}
}
