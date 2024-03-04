package bus;

import java.util.Comparator;

public class PredicateTotalBalance implements Comparator<IAccount> {
	
	@Override
	public int compare(IAccount o1, IAccount o2)
	{
		
		if (o1.getTotalBalance() > o2.getTotalBalance()) {
			return 1;
		} else if (o1.getTotalBalance() < o2.getTotalBalance()) {
			return -1;
		} else {
			return 0;
		}
	}
}