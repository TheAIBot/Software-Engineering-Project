package SoftwareHouse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TimePeriod {
	
	public static final String DATE_FORMAT = "dd MM yyyy";
	
	private final Calendar startDate;
	private final Calendar endDate;
	
	public TimePeriod(Calendar startDate, Calendar endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public boolean isValidTimePeriod(){
		return (endDate != null && startDate != null && endDate.after(startDate));
	}
	
	public static GregorianCalendar getCalendarFromString(String dateString) throws ParseException
	{
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		Date date = dateFormat.parse(dateString);
		GregorianCalendar dateCalendar = new GregorianCalendar();
		dateCalendar.setTime(date);
		return dateCalendar;
	}

	/**
	 * @return the startDate
	 */
	public Calendar getStartDate() {
		return startDate;
	}
	
	public String getStartDateAsString()
	{
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		return dateFormat.format(startDate.getTime());
	}

	/**
	 * @return the endDate
	 */
	public Calendar getEndDate() {
		return endDate;
	}
	
	public String getEndDateAsString()
	{
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		return dateFormat.format(endDate.getTime());
	}
}
