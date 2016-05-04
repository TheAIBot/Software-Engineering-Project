package SoftwareHouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.AlreadyLoggedInException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidProjectInitilizationInput;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;

public class Scheduler {

	private List<Project> projects = new ArrayList<Project>();
	private Map<String, Employee> employees = new HashMap<String, Employee>();
	private boolean anyoneLoggedIn = false;
	private Employee loggedInEmployee = null;
	private TimeVault timeVault = new TimeVault(this);

	public void createProject(String projectName) throws MissingInformationException, DuplicateNameException, NotLoggedInException, InvalidProjectInitilizationInput {
		if (isAnyoneLoggedIn()) {
			if (Tools.isNullOrEmpty(projectName)) {
				throw new MissingInformationException("Missing project name");
			}
			if (Tools.containsProject(projects, projectName.trim())) {
				throw new DuplicateNameException("A project with that title already exists");
			}
			projects.add(new Project(this, projectName, "","",null,0,null,null));
		} else {
			throw new NotLoggedInException();
		}
	}
	
	public void createProject(String projectName, String companyName, String detailedText, 
		    List<Employee> employeesToAdd, int budgettedTime, String initialsProjectManager, TimePeriod timePeriod)
				 throws InvalidProjectInitilizationInput, NotLoggedInException{
		if (isAnyoneLoggedIn()) {
			if (Project.isValidProjectInformation(this, projectName, companyName, detailedText, employeesToAdd, 
					                                   budgettedTime, initialsProjectManager, timePeriod)) {
				projects.add(new Project(this, projectName,  companyName,  detailedText, 
					    employeesToAdd, budgettedTime, initialsProjectManager,  timePeriod));
			} else {
				throw new InvalidProjectInitilizationInput("The information given will not make a valid project");
			}
		} else {
			throw new NotLoggedInException();
		}
	}
	
	public List<Employee> getEmployeesContainingString(String partOfInitials){
		return employees.entrySet().stream()
								   .filter(x-> x.getKey().contains(partOfInitials))
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

	/** Return whether or not all the given employees exist. If the list is null, it returns true. 
	 * @param employees The employees
	 * @return True if all exists, else false.
	 */
	public boolean doAllEmployeesExist(List<Employee> employees) {
		if (employees != null) {
			return (employees.stream().allMatch(x -> this.doesEmployeeExist(x.getInitials())));			
		} else {
			return true;
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
	
	public boolean doesEmployeeExist(String initials){
		if (employees == null) return false; 
		return employees.containsKey(initials);
	}

	public void login(String initials) throws EmployeeNotFoundException, AlreadyLoggedInException {
		if (doesEmployeeExist(initials)) {
			if (loggedInEmployee != null && loggedInEmployee.getInitials().equals(initials)) {
				throw new AlreadyLoggedInException(initials + " is already logged in");
			} else{
				Employee employee = employees.get(initials);
				loggedInEmployee = employee;
				anyoneLoggedIn = true;
			}
			
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
	
}
