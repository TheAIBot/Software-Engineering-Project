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
	private List<Activity> activities = new ArrayList<Activity>(MAX_ACTIVITIES);
	private List<Activity> absenceActivities = new ArrayList<Activity>();

	public Employee(Scheduler scheduler, String initials) {
		this.scheduler = scheduler;
		this.initials = initials;
	}

	/**
	 * @return the initials
	 */
	public String getInitials() {
		return initials;
	}

	public boolean isAlreadyPartOfActivity(Activity activity){
		return activities.stream().anyMatch(x -> (x.getName().equals(activity.getName()) && x.getProjectName().equals(activity.getProjectName())));
	}
	
	public boolean isAlreadyPartOfProject(Project project){
		return projects.stream().anyMatch(x -> (x.getName().equals(project.getName()))); //TODO Loebenummer sammenligning.
	}
	
	public boolean addProject(Project project)
	{
		return projects.add(project);
	}
	
	public boolean canContainMoreActivities()
	{
		return !(activities.size() == MAX_ACTIVITIES);
	}

	public void addActivity(Activity activity) throws EmployeeMaxActivitiesReachedException {
		if (activities.size() == MAX_ACTIVITIES) {
			throw new EmployeeMaxActivitiesReachedException(
					initials + " has reached the max of " + MAX_ACTIVITIES + " activities");
		}
		activities.add(activity);
	}

	/**
	 * @return the projects
	 */
	public List<Project> getProjects() {
		return projects;
	}

	public void registerTime(String projectName, String activityName, String message, int time)
			throws ProjectNotFoundException, NotLoggedInException, ActivityNotFoundException,
			InvalidInformationException {
		if (Tools.isNullOrEmpty(message)) {
			throw new InvalidInformationException("Invalid text");
		}
		scheduler.getTimeVault().addTime(projectName, activityName, initials, new RegisteredTime(this, message, time));
	}

	public List<Activity> getActivities() {
		return activities;
	}
	
	public int getNumberOfActivities(){
		return activities.size();
	}

	public boolean equals(Object obj) {
		if (obj == null) return false;
		else if(obj.getClass() == String.class){
			return ((String) obj).equals(this.initials);
		} else {
			return ((Employee) obj).getInitials().equals(this.initials);
		}		
	}

	public List<Activity> getAbsenceActivities() {
		return absenceActivities;
	}

	public void addAbsenceActivity(Activity activity) {
		absenceActivities.add(activity);
	}
}
