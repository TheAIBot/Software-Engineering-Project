
public class Activity {
	
	private String name;
	private String description;
	private int budgetedTime;
	private Timeperiod timePeriod;
	
	public Activity(String name, String description, int budgetedTime, Timeperiod timeperiod) {
		this.setName(name);
		this.setDescription(description);
		this.setBudgetedTime(budgetedTime);
		this.setTimePeriod(timeperiod);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getBudgetedTime() {
		return budgetedTime;
	}

	public void setBudgetedTime(int budgetedTime) {
		this.budgetedTime = budgetedTime;
	}

	public Timeperiod getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(Timeperiod timePeriod) {
		this.timePeriod = timePeriod;
	}
	
	
	

}
