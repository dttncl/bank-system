package bus;

import java.util.Comparator;

public class PredicateCustomerId implements Comparator<ICustomer> {
	
	@Override
	public int compare(ICustomer o1, ICustomer o2)
	{
		if (o1.getUserId().compareTo(o2.getUserId()) > 0) {
			return -1;
		} else if (o1.getUserId().compareTo(o2.getUserId()) < 0) {
			return 1;
		} else {
			return 0;
		}
	}
}
