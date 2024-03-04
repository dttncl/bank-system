package bus;

import java.io.Serializable;
import java.util.Calendar;


public class Date implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8333059279183043165L;
	private int month;
	private int day;
	private int year;
		
	public Date() {
		super();
	    Calendar cal=Calendar.getInstance();

		this.month = cal.get(Calendar.MONTH)+1;
		this.day = cal.get(Calendar.DATE);
		this.year = cal.get(Calendar.YEAR);
	}

	public Date(int month, int day, int year) {
		super();
		this.month = month;
		this.day = day;
		this.year = year;
	}
	
	public Date(String dateString) {
		super();
		String[] arrOfDate = dateString.split("-");
		this.month = Integer.parseInt(arrOfDate[1]);
		this.day = Integer.parseInt(arrOfDate[0]);
		this.year = Integer.parseInt(arrOfDate[2]);
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return day + "-" + month + "-" + year;
	}
	
	
	
}
