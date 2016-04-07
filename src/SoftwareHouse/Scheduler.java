package SoftwareHouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.MissingProjectException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;

public class Scheduler {

	private List<Project> projects = new ArrayList<Project>();
	private Map<String, Employee> employees = new HashMap<String, Employee>();
	
	public void createProject(String projectName) throws MissingInformationException, DuplicateNameException {
		if (projectName == null || projectName.trim().isEmpty()) {
			throw new MissingInformationException("Missing project name");
		}
		if (projects.stream().anyMatch(x -> x.getName().equals(projectName.trim()))) {
			throw new DuplicateNameException("A project with that title already exists");
		}
		projects.add(new Project(this, projectName));
	}

	public Employee getEmployeeFromInitials(String initials) throws EmployeeNotFoundException
	{
		if (employees.containsKey(initials)) {
			return employees.get(initials);
		} else {
			throw new EmployeeNotFoundException("No employee with those initials exists");
		}
	}
	
	/**
	 * @return the projects
	 */
	public List<Project> getProjects() {
		return projects;
	}

	public Project getProject(String projectName) throws MissingProjectException {
		for (Project project : projects) {
			if (project.getName().equals(projectName)) {
				return project;
			}
		}
		throw new MissingProjectException();
	}

	public void addEmployee(String initials) throws MissingInformationException, DuplicateNameException {
		if (initials == null || initials.trim().isEmpty()) {
			throw new MissingInformationException("Missing employee initials");
		}
		if (employees.containsKey(initials)) {
			throw new DuplicateNameException("An employee with those initials already exist");
		}	
		employees.put(initials, new Employee(this, initials));
	}

	public Activity getActivity(String projectName, String ActivityName) throws ProjectNotFoundException, ActivityNotFoundException {
		if (projects.stream().anyMatch(x -> x.getName().equals(projectName))) {
			Project project = projects.stream().filter(x -> x.getName().equals(projectName)).collect(Collectors.toList()).get(0);
			
			//need to handle that the activity is deleted or archived
			//will add that functionality later
			if (project.getOpenActivities().stream().anyMatch(x -> x.getName().equals(ActivityName))) {
				return project.getOpenActivities().stream().filter(x -> x.getName().equals(ActivityName)).collect(Collectors.toList()).get(0);
			} else {
				throw new ActivityNotFoundException();
			}
		} else {
			throw new ProjectNotFoundException();
		}
	}

}
