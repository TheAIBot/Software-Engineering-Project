 package SoftwareHouse;

import java.util.ArrayList;
import java.util.List;

import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;

/**
 * @author Niklas
 * The basic employee at the company
 * Can peform basic tasks like register time (work period), register absence time
 * Contains functionality for subscribing to an activity or project 
 */
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
	 * Niklas
	 * Register work and absence
	 * @param projectName
	 * @param activityName
	 * @param message
	 * @param time n.o. hours
	 * @throws ProjectNotFoundException
	 * @throws NotLoggedInException
	 * @throws ActivityNotFoundException
	 * @throws InvalidInformationException
	 * @throws EmployeeNotFoundException
	 * @throws EmployeeNotAffiliatedWithProjectException
	 */
	public void registerTime(String projectName, String activityName, String message, int time)
			throws ProjectNotFoundException, NotLoggedInException, ActivityNotFoundException, EmployeeNotFoundException, InvalidInformationException {
		scheduler.getTimeVault().addTime(projectName, activityName, initials, new RegisteredTime(this, message, time));
	}

	/**
	 * Niklas
	 * @return
	 */
	public String getInitials() {
		return initials;
	}

	/**
	 * Niklas
	 * @param activity
	 * @return
	 */
	public boolean isAlreadyPartOfActivity(Activity activity){
		return activities.stream()
						 .anyMatch(x -> (x.getName().equals(activity.getName()) && 
								  		 x.getProjectName().equals(activity.getProjectName())));
	}
	
	/**
	 * Andreas
	 * @param project
	 * @return
	 */
	public boolean isAlreadyPartOfProject(Project project){
		return projects.stream()
					   .anyMatch(x -> (x.getName().equals(project.getName())));
	}
	
	/**
	 * Emil
	 * @param project
	 * @return
	 */
	public boolean addProject(Project project)
	{
		return projects.add(project);
	}
	
	/**
	 * Jesper
	 * @return
	 */
	public boolean canContainMoreActivities()
	{
		return !(activities.size() == MAX_ACTIVITIES);
	}

	/**
	 * Jesper
	 * @param activity
	 * @throws EmployeeMaxActivitiesReachedException
	 */
	public void addActivity(Activity activity) throws EmployeeMaxActivitiesReachedException {
		if (activities.size() == MAX_ACTIVITIES) {
			throw new EmployeeMaxActivitiesReachedException(
					initials + " has reached the max of " + MAX_ACTIVITIES + " activities");
		}
		activities.add(activity);
	}

	/**
	 * Jesper
	 * @return the projects
	 */
	public List<Project> getProjects() {
		return projects;
	}
	
	/**
	 * Emil
	 * @return
	 */
	public int getNumberOfProjects() {
		return projects.size();
	}

	/**
	 * Niklas
	 * @return
	 */
	public List<Activity> getActivities() {
		return activities;
	}
	
	/**
	 * Niklas
	 * @return
	 */
	public int getNumberOfActivities(){
		return activities.size();
	}

	/**
	 * Andreas
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		else if (obj instanceof String) return ((String) obj).equals(this.initials);
		else if (obj instanceof Employee) return ((Employee) obj).getInitials().equals(this.initials);
		else return false;
	}

	/**
	 * Emil
	 * @return
	 */
	public List<Activity> getAbsenceActivities() {
		return absenceActivities;
	}

	/**
	 * Andreas
	 * @param activity
	 */
	public void addAbsenceActivity(Activity activity) {
		absenceActivities.add(activity);
	}
	
}
