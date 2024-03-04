package bus;

import java.util.Comparator;

public class PredicateAccountType implements Comparator<IAccount> {
	
	@Override
	public int compare(IAccount o1, IAccount o2)
	{
		String obj1 = o1.getType().name();
		String obj2 = o2.getType().name();
		
		if (obj1.compareTo(obj2) > 0) {
			return 1;
		} else if (obj1.compareTo(obj2) < 0) {
			return -1;
		} else {
			return 0;
		}
	}
}