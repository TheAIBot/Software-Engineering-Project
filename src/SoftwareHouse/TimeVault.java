package SoftwareHouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;


/**
 * @author Jesper
 * Helping class to handle time manipulation operations
 */
public class TimeVault {
	
	private Scheduler scheduler;
		
	private Map<String, List<Integer>> employeeTimes = new HashMap<String,List<Integer>>();
	private HashMap<Project, HashMap<Activity,ArrayList<Integer>>> projectTimes = new HashMap<Project, HashMap<Activity, ArrayList<Integer>>>();
	private List<RegisteredTime> times = new ArrayList<RegisteredTime>();
	
	public TimeVault(Scheduler scheduler)
	{
		this.scheduler = scheduler;
	}
	
	/**
	 * Emil
	 * @param projectName
	 * @param activityName
	 * @param employeeInitials
	 * @param registeredTime
	 * @throws ProjectNotFoundException
	 * @throws NotLoggedInException
	 * @throws ActivityNotFoundException
	 */
	public void addTime(String projectName, String activityName, String employeeInitials, RegisteredTime registeredTime) throws ProjectNotFoundException, NotLoggedInException, ActivityNotFoundException
	{
		int timeIndex = times.size();
		times.add(registeredTime);
		addTimeIndexToEmployeeTime(employeeInitials, timeIndex);
		addTimeIndexToProjectTimes(projectName, activityName, timeIndex);
	}
	
	/**
	 * Niklas
	 * @param employeeInitials
	 * @param timeIndex
	 */
	private void addTimeIndexToEmployeeTime(String employeeInitials, int timeIndex)
	{
		if (!employeeTimes.containsKey(employeeInitials)) {
			employeeTimes.put(employeeInitials, new ArrayList<Integer>());
		}
		employeeTimes.get(employeeInitials).add(timeIndex);
	}

	/**
	 * Jesper
	 * @param projectName
	 * @param activityName
	 * @param timeIndex
	 * @throws ProjectNotFoundException
	 * @throws NotLoggedInException
	 * @throws ActivityNotFoundException
	 */
	private void addTimeIndexToProjectTimes(String projectName, String activityName, int timeIndex) throws ProjectNotFoundException, NotLoggedInException, ActivityNotFoundException
	{
		Project project = scheduler.getProject(projectName);
		Activity activity = scheduler.getActivity(projectName, activityName);
		if (!projectTimes.containsKey(project)) {
			projectTimes.put(project, new HashMap<Activity, ArrayList<Integer>>());
		}
		if (!projectTimes.get(project).containsKey(activity)) {
			projectTimes.get(project).put(activity, new ArrayList<Integer>());
		}
		projectTimes.get(project).get(activity).add(timeIndex);
	}

	/**
	 * Jesper
	 * @param employeeInitials
	 * @return
	 */
	public List<RegisteredTime> getEmployeeTime(String employeeInitials)
	{
		if (employeeTimes.containsKey(employeeInitials)) {
			return employeeTimes.get(employeeInitials).stream()
													  .map(x -> times.get(x.intValue()))
													  .collect(Collectors.toList());
		} else {
			return new ArrayList<RegisteredTime>();
		}
	}

	/**
	 * Emil
	 * @param projectName
	 * @return
	 * @throws ProjectNotFoundException
	 * @throws NotLoggedInException
	 */
	public List<RegisteredTime> getProjectTime(String projectName) throws ProjectNotFoundException, NotLoggedInException
	{
		Project project = scheduler.getProject(projectName);
		if (projectTimes.containsKey(project)) {
			ArrayList<RegisteredTime> registeredTimes = new ArrayList<RegisteredTime>();
			projectTimes.get(project).values().stream()
											  .forEach(x -> x.stream()
													  		 .forEach(y -> registeredTimes.add(times.get(y.intValue()))));
			return registeredTimes;
		} else {
			return new ArrayList<RegisteredTime>();
		}
	}
	
	/**
	 * Niklas
	 * @param projectName
	 * @param activityName
	 * @return
	 * @throws ProjectNotFoundException
	 * @throws NotLoggedInException
	 * @throws ActivityNotFoundException
	 */
	public List<RegisteredTime> getActivityTime(String projectName, String activityName) throws ProjectNotFoundException, NotLoggedInException, ActivityNotFoundException
	{
		Project project = scheduler.getProject(projectName);
		Activity activity = scheduler.getActivity(projectName, activityName);
		if (projectTimes.containsKey(project) &&
			projectTimes.get(project).containsKey(activity)) {
			return projectTimes.get(project).get(activity).stream()
														  .map(x -> times.get(x.intValue()))
														  .collect(Collectors.toList());
		} else {
			return new ArrayList<RegisteredTime>();
		}
	}
}
