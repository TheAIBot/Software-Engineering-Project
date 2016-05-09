package SoftwareHouse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import SoftwareHouse.ExceptionTypes.InvalidInformationException;


/**
 * Niklas
 * Class representing a period of time utilising the DateFormat class
 */
public class TimePeriod {
	
	public static final String DATE_FORMAT = "dd MM yyyy";
	
	private final Calendar startDate;
	private final Calendar endDate;
	
	/**
	 * Emil
	 * @param startDate
	 * @param endDate
	 * @throws InvalidInformationException
	 */
	public TimePeriod(Calendar startDate, Calendar endDate) throws InvalidInformationException {
		if (startDate == null) {
			throw new InvalidInformationException("Start date doesn't exist");
		} else if (endDate == null) {
			throw new InvalidInformationException("End date doesn't exist");
		} else if (startDate.after(endDate)) {
			throw new InvalidInformationException("End date has to start after start date");
		}
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	/**
	 * Niklas
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static GregorianCalendar getCalendarFromString(String dateString) throws ParseException
	{
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		Date date = dateFormat.parse(dateString);
		GregorianCalendar dateCalendar = new GregorianCalendar();
		dateCalendar.setTime(date);
		return dateCalendar;
	}

	/**
	 * Andreas
	 * @return the startDate
	 */
	public Calendar getStartDate() {
		return startDate;
	}
	
	/**
	 * Jesper
	 * @return
	 */
	public String getStartDateAsString()
	{
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		return dateFormat.format(startDate.getTime());
	}

	/**
	 * Emil
	 * @return the endDate
	 */
	public Calendar getEndDate() {
		return endDate;
	}
	
	/**
	 * Niklas
	 * @return
	 */
	public String getEndDateAsString()
	{
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		return dateFormat.format(endDate.getTime());
	}
}
