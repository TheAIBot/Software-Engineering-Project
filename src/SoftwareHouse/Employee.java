package SoftwareHouse;

import java.util.ArrayList;
import java.util.List;

import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;

public class Employee {
	private final String initials;
	private Scheduler scheduler;
	public static final int MAX_ACTIVITIES = 20;
	private List<Project> projects = new ArrayList<Project>();
	private List<Activity>activities = new ArrayList<Activity>(MAX_ACTIVITIES); 
	
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
	
	public void addActivity(Activity activity) throws EmployeeMaxActivitiesReachedException
	{
		if (activities.size() == MAX_ACTIVITIES) {
			throw new EmployeeMaxActivitiesReachedException(initials + " has reached the max of " + MAX_ACTIVITIES + " activities");
		}
		activities.add(activity);
	}

	/**
	 * @return the projects
	 */
	public List<Project> getProjects() {
		return projects;
	}
<<<<<<< HEAD

	public void registerTime(String projectName, String activityName, String message, int time) throws ProjectNotFoundException, NotLoggedInException, ActivityNotFoundException, InvalidInformationException {
		scheduler.getTimeVault().addTime(projectName, activityName, initials, new RegisteredTime(this, message, time));
=======
	
	public List<Activity> getActivities() {
		return activities;
>>>>>>> refs/remotes/origin/Jesper
	}
}
