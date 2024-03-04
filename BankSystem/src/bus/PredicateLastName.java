package bus;
import java.util.Comparator;

public class PredicateLastName implements Comparator<ICustomer> {
	
	@Override
	public int compare(ICustomer o1, ICustomer o2)
	{
		if (o1.getlName().compareTo(o2.getlName()) > 0) {
			return 1;
		} else if (o1.getlName().compareTo(o2.getlName()) < 0) {
			return -1;
		} else {
			return 0;
		}
	}

}
