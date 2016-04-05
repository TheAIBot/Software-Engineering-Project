package SoftwareHouse;

public class Project {
	private String name;
	
	public Project(String projectName)
	{
		name = projectName;
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
