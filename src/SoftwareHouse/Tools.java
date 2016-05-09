package SoftwareHouse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Andreas
 * Helping class to handle general static operations used through out every other class
 */
public class Tools {

	/**
	 * Andreas
	 * @param string
	 * @return
	 */
	public static boolean isNullOrEmpty(String string)
	{
		return (string == null || string.trim().isEmpty());
	}
	
	/**
	 * Andreas
	 * @param projects
	 * @param projectName
	 * @return
	 */
	public static boolean containsProject(List<Project> projects, String projectName)
	{
		return (projects.stream()
					   .anyMatch(x -> x.getName().equals(projectName)));
	}
	
	/**
	 * Andreas
	 * @param activities
	 * @param activityName
	 * @return
	 */
	public static boolean containsActivity(List<Activity> activities, String activityName)
	{
		return activities.stream().anyMatch(x -> x.getName().equals(activityName));
	}
	
	/**
	 * Andreas
	 * @param employees
	 * @param initials
	 * @return
	 */
	public static boolean containsEmployee(List<Employee> employees, String initials)
	{
		
		return employees.stream()
		   		 		.anyMatch(x -> x.getInitials().equals(initials));
	}
	
	/**
	 * Andreas
	 * @param projects
	 * @param projectName
	 * @return
	 */
	public static Project getProjectFromName(List<Project> projects, String projectName)
	{
		return projects.stream()
					   .filter(x -> x.getName().equals(projectName))
					   .collect(Collectors.toList())
					   .get(0);
	}	
	
	/**
	 * Andreas
	 * @param activities
	 * @param activityName
	 * @return
	 */
	public static Activity getActivityFromName(List<Activity> activities, String activityName)
	{
		return activities.stream()
				   .filter(x -> x.getName().equals(activityName))
				   .collect(Collectors.toList())
				   .get(0);
	}
	
	/**
	 * Andreas
	 * @param employees
	 * @param initials
	 * @return
	 */
	public static Employee getEmployeeFromInitials(List<Employee> employees, String initials)
	{
		return employees.stream()
				   		.filter(x -> x.getInitials().equals(initials))
				   		.collect(Collectors.toList())
				   		.get(0);
	}
	
}
