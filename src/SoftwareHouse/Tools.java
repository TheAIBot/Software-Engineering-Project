package SoftwareHouse;

import java.util.List;
import java.util.stream.Collectors;

public class Tools {

	public static boolean isNullOrEmpty(String string)
	{
		return (string == null || string.trim().isEmpty());
	}
	
	public static boolean containsProject(List<Project> projects, String projectName)
	{
		return projects.stream()
					   .anyMatch(x -> x.getName().equals(projectName));
	}
	
	public static boolean containsActivity(List<Activity> activities, String activityName)
	{
		return activities.stream().anyMatch(x -> x.getName().equals(activityName));
	}
	
	public static boolean containsEmployee(List<Employee> employees, String initials)
	{
		
		return employees.stream()
		   		 		.anyMatch(x -> x.getInitials().equals(initials));
	}
	
	
	
	public static Project getProjectFromName(List<Project> projects, String projectName)
	{
		return projects.stream()
					   .filter(x -> x.getName().equals(projectName))
					   .collect(Collectors.toList())
					   .get(0);
	}	
	
	public static Activity getActivityFromName(List<Activity> activities, String activityName)
	{
		return activities.stream()
				   .filter(x -> x.getName().equals(activityName))
				   .collect(Collectors.toList())
				   .get(0);
	}
	
	public static Employee getEmployeeFromInitials(List<Employee> employees, String initials)
	{
		return employees.stream()
				   		.filter(x -> x.getInitials().equals(initials))
				   		.collect(Collectors.toList())
				   		.get(0);
	}
}
