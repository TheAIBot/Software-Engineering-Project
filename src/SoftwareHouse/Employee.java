package SoftwareHouse;

public class Employee {
	private final String initials;
	private Scheduler scheduler;
	
	public Employee(Scheduler scheduler, String initials)
	{
		this.scheduler = scheduler;
		this.initials = initials;
	}

	/**
	 * @return the initials
	 */
	public String getInitials() {
		return initials;
	}
}
