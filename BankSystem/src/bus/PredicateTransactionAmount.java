package bus;

import java.util.Comparator;

public class PredicateTransactionAmount implements Comparator<ITransaction> {
	
	@Override
	public int compare(ITransaction o1, ITransaction o2)
	{
		
		if (o1.getAmount() > o2.getAmount()) {
			return 1;
		} else if (o1.getAmount() < o2.getAmount()) {
			return -1;
		} else {
			return 0;
		}
	}
}
