package bus;

import java.util.Comparator;

public class PredicateTransactionDate implements Comparator <ITransaction> {

	@Override
	public int compare(ITransaction o1, ITransaction o2)
	{
		int obj1Year = o1.getTransactionDate().getYear();
		int obj1Month = o1.getTransactionDate().getMonth();
		int obj1Day = o1.getTransactionDate().getDay();

		int obj2Year = o2.getTransactionDate().getYear();
		int obj2Month = o2.getTransactionDate().getMonth();
		int obj2Day = o2.getTransactionDate().getDay();
		
		if (obj1Year > obj2Year) {
			return +1;
		} else if (obj1Year < obj2Year) {
			return -1;
		} else {
			if (obj1Month > obj2Month) {
				return +1;
			} else if (obj1Month < obj2Month) {
				return -1;
			} else {
				if (obj1Day > obj2Day) {
					return +1;
				} else if (obj1Day < obj2Day) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}
}
