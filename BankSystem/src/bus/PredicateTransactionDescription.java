package bus;

import java.util.Comparator;

public class PredicateTransactionDescription implements Comparator<ITransaction> {
	
	@Override
	public int compare(ITransaction o1, ITransaction o2)
	{
		if (o1.getDescription().compareTo(o2.getDescription()) > 0) {
			return 1;
		} else if (o1.getDescription().compareTo(o2.getDescription()) < 0) {
			return -1;
		} else {
			return 0;
		}
	}

}
