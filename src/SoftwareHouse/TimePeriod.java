package SoftwareHouse;
import java.sql.Date;
import java.util.Calendar;


public class TimePeriod {
	
	private Calendar startDate;
	private Calendar endDate;
	
	public TimePeriod(Calendar startDate, Calendar endDate) {
		this.startDate = startDate;
		this.setEndDate(endDate);
	}

	/**
	 * @return the startDate
	 */
	public Calendar getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Calendar getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
}
