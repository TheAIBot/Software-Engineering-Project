package SoftwareHouse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Activity {
	
	private String title;
	private String detailText;
	private List<Employee> assignedEmployees = new ArrayList<Employee>();
	private TimePeriod timePeriod;
	private int budgettedTime;
	
	public Activity(String title, String detailText, List<Employee> employees, Calendar startDate, Calendar endDate, int budgettedTime) {
		this.title = title;
		this.detailText = detailText;
		this.assignedEmployees.addAll(employees);
		this.setTimePeriod(new TimePeriod(startDate, endDate));
		this.budgettedTime = budgettedTime;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the detailText
	 */
	public String getDetailText() {
		return detailText;
	}

	/**
	 * @param detailText the detailText to set
	 */
	public void setDetailText(String detailText) {
		this.detailText = detailText;
	}

	/**
	 * @return the budgettedTime
	 */
	public int getBudgettedTime() {
		return budgettedTime;
	}

	/**
	 * @param budgettedTime the budgettedTime to set
	 */
	public void setBudgettedTime(int budgettedTime) {
		this.budgettedTime = budgettedTime;
	}

	/**
	 * @return the assignedEmployees
	 */
	public List<Employee> getAssignedEmployees() {
		return assignedEmployees;
	}

	/**
	 * @return the timePeriod
	 */
	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	/**
	 * @param timePeriod the timePeriod to set
	 */
	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}
}
