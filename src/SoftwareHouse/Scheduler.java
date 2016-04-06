package SoftwareHouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.MissingProjectException;
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
			throw new EmployeeNotFoundException();
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

}
