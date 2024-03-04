package bus;

import java.util.Comparator;

public class PredicateAvailableBalance implements Comparator<IAccount> {
	
	@Override
	public int compare(IAccount o1, IAccount o2)
	{
		
		if (o1.getAvailableBalance() > o2.getAvailableBalance()) {
			return 1;
		} else if (o1.getAvailableBalance() < o2.getAvailableBalance()) {
			return -1;
		} else {
			return 0;
		}
	}
}
