package bus;
import java.util.Comparator;

public class PredicateFirstName implements Comparator<ICustomer> {
	
	@Override
	public int compare(ICustomer o1, ICustomer o2)
	{
		if (o1.getfName().compareTo(o2.getfName()) > 0) {
			return 1;
		} else if (o1.getfName().compareTo(o2.getfName()) < 0) {
			return -1;
		} else {
			return 0;
		}
	}

}