package bus;

import java.util.Comparator;

public class PredicateOpenDate implements Comparator <IAccount> {

	@Override
	public int compare(IAccount o1, IAccount o2)
	{
		int obj1Year = o1.getOpenedDate().getYear();
		int obj1Month = o1.getOpenedDate().getMonth();
		int obj1Day = o1.getOpenedDate().getDay();

		int obj2Year = o2.getOpenedDate().getYear();
		int obj2Month = o2.getOpenedDate().getMonth();
		int obj2Day = o2.getOpenedDate().getDay();
		
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