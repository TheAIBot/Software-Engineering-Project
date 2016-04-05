import java.sql.Date;
import java.util.Calendar;


public class Timeperiod {
	
	private Calendar startDate;
	private Calendar endDate;
	
	public Timeperiod(Calendar starteDate, Calendar endDate) {
		this.setStartDate(starteDate);
		this.setEndDate(endDate);
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	
	

}
