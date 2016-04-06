package SoftwareHouse;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	private final String initials;
	private Scheduler scheduler;
	
	private List<Project> projects = new ArrayList<Project>();
	
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

	public void addProject(Project project)
	{
		projects.add(project);
	}


	/**
	 * @return the projects
	 */
	public List<Project> getProjects() {
		return projects;
	}
}
