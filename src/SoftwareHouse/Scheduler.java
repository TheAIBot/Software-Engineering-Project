package SoftwareHouse;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;
import sun.font.FileFontStrike;
import sun.net.www.content.audio.x_aiff;

public class Scheduler {

	private List<Project> projects = new ArrayList<Project>();
	private Map<String, Employee> employees = new HashMap<String, Employee>();
	private boolean anyoneLoggedIn = false;
	private Employee loggedInEmployee;
	private TimeVault timeVault = new TimeVault(this);

	public void createProject(String projectName) throws MissingInformationException, DuplicateNameException, NotLoggedInException {
		if (isAnyoneLoggedIn()) {
			if (Tools.isNullOrEmpty(projectName)) {
				throw new MissingInformationException("Missing project name");
			}
			if (Tools.containsProject(projects, projectName.trim())) {
				throw new DuplicateNameException("A project with that title already exists");
			}
			projects.add(new Project(this, projectName));
		} else {
			throw new NotLoggedInException();
		}
	}
	
	public List<Employee> getEmployeesContainingString(String partOfInitials){
		return employees.entrySet().stream()
								   .filter(x-> x.getKey().contains(partOfInitials.toUpperCase()))
								   .map(x -> x.getValue())
								   .collect(Collectors.toList());
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
	 * @throws NotLoggedInException 
	 */
	public List<Project> getProjects() throws NotLoggedInException {
		if (isAnyoneLoggedIn()) {
			return projects;
		} else {
			throw new NotLoggedInException();
		}
	}

	public Project getProject(String projectName) throws ProjectNotFoundException, NotLoggedInException {
		if (isAnyoneLoggedIn()) {
			if (Tools.containsProject(projects, projectName)) {
				return Tools.getProjectFromName(projects, projectName);
			} else {
				throw new ProjectNotFoundException();
			}
		} else {
			throw new NotLoggedInException();
		}
	}

	public void addEmployee(String initials) throws MissingInformationException, DuplicateNameException {
		if (Tools.isNullOrEmpty(initials)) {
			throw new MissingInformationException("Missing employee initials");
		}
		if (employees.containsKey(initials)) {
			throw new DuplicateNameException("An employee with those initials already exist");
		}	
		employees.put(initials, new Employee(this, initials));
	}

	public Activity getActivity(String projectName, String activityName) throws ProjectNotFoundException, ActivityNotFoundException, NotLoggedInException {
		if (isAnyoneLoggedIn()) {
			if (Tools.containsProject(projects, projectName)) {
				Project project = Tools.getProjectFromName(projects, projectName);
				
				//need to handle that the activity is deleted or archived
				//will add that functionality later
				if (Tools.containsActivity(project.getOpenActivities(), activityName)) {
					return Tools.getActivityFromName(project.getOpenActivities(), activityName);
				} else {
					throw new ActivityNotFoundException();
				}
			} else {
				throw new ProjectNotFoundException();
			}
		} else {
			throw new NotLoggedInException();
		}
	}
	
	public boolean isAnyoneLoggedIn() {
		return anyoneLoggedIn;
	}

	public void login(String initials) throws EmployeeNotFoundException {
		if (employees.containsKey(initials)) {
			Employee employee = employees.get(initials);
			loggedInEmployee = employee;
			anyoneLoggedIn = true;
		} else {
			throw new EmployeeNotFoundException("No employee with those initials exists");
		}
	}

	public Employee getLoggedInEmployee() {
		return loggedInEmployee;
	}

	public TimeVault getTimeVault()
	{
		return timeVault;
	}
	
	public List<Employee> getEmployees()
	{
		return employees.values().stream().collect(Collectors.toList());
	}
	
}
