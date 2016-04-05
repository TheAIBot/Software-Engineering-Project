package SoftwareHouse;

public class Project {
	private Scheduler scheduler;
	private String name;
	
	public Project(Scheduler scheduler, String projectName)
	{
		this.scheduler = scheduler;
		this.name = projectName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
