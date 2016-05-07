package SoftwareHouse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.AlreadyLoggedInException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeAlreadyAssignedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.IllegalCharException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.InvalidProjectInitilizationInput;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotPartOfEmployeesAdded;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;
import SoftwareHouse.ExceptionTypes.TooManyCharsException;

/**
 * The main part of the application layer
 */
public class Scheduler {

	private List<Project> projects = new ArrayList<Project>();
	private Map<String, Employee> employees = new HashMap<String, Employee>();
	private boolean anyoneLoggedIn = false;
	private Employee loggedInEmployee = null;
	private TimeVault timeVault = new TimeVault(this);	
	private Project absenceProject;
	
	public Scheduler() {
		anyoneLoggedIn = true; //One needs to be logged in to make a project.
		try {
			absenceProject = new Project(this, "absenceProject", true); //TODO test creation of absence project if it hasn't been done already
		} catch (Exception e) { 
			//Cann't be reached
		}
		anyoneLoggedIn = false;
	}

	public void createProject(String projectName) throws MissingInformationException, DuplicateNameException, NotLoggedInException, InvalidInformationException, EmployeeNotFoundException, EmployeeAlreadyAssignedException, ProjectManagerNotPartOfEmployeesAdded 
	{
		createProject(projectName, "", "", null, 0, "", null);
	}
	
	public void createProject(String projectName, 
							  String costumerName, 
							  String detailedText, 
							  List<Employee> employeesToAdd, 
							  int budgettedTime, 
							  String initialsProjectManager, 
							  TimePeriod timePeriod) throws NotLoggedInException, MissingInformationException, InvalidInformationException, EmployeeNotFoundException, DuplicateNameException, EmployeeAlreadyAssignedException, ProjectManagerNotPartOfEmployeesAdded
	{
		if (isAnyoneLoggedIn()) {
			if (projectName == null) {
				throw new MissingInformationException("Missing project name");
			} else if (Tools.containsProject(projects, projectName.trim())) {
				throw new DuplicateNameException("A project with that title already exists");
			} 
			projects.add(new Project(this, projectName,  costumerName, detailedText, employeesToAdd, budgettedTime, initialsProjectManager, timePeriod));
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
			} else if (Tools.containsProject(java.util.Collections.singletonList(absenceProject), projectName)) {
				return Tools.getProjectFromName(java.util.Collections.singletonList(absenceProject), projectName);
			}else {
				throw new ProjectNotFoundException();
			}
		} else {
			throw new NotLoggedInException();
		}
	}

	public List<Project> getProjectsContainingStringInName(String partOfProjectName) throws NotLoggedInException
	{
		if (isAnyoneLoggedIn()) {
			return projects.stream()
					   	   .filter(x -> x.getName().contains(partOfProjectName))
					   	   .collect(Collectors.toList());
		} else {
			throw new NotLoggedInException();
		}

	}
	
 	public void addEmployee(String initials) throws MissingInformationException, DuplicateNameException, TooManyCharsException, IllegalCharException {
		if (tryIsValidEmployeeInitials(initials)) {
			employees.put(initials, new Employee(this, initials));
		}
	}

	public Activity getActivity(String projectName, String activityName) throws ProjectNotFoundException, ActivityNotFoundException, NotLoggedInException {
		if (isAnyoneLoggedIn()) {
			if (Tools.containsProject(projects, projectName) || Tools.containsProject(java.util.Collections.singletonList(absenceProject), projectName)) {
				Project project = getProject(projectName);
				//need to handle that the activity is deleted or archived
				//will add that functionality later
				if (Tools.containsActivity(project.getOpenActivities(), activityName)) {
					return Tools.getActivityFromName(project.getOpenActivities(), activityName);
				}else if (Tools.containsActivity(absenceProject.getOpenActivities(), activityName)) {
					return Tools.getActivityFromName(absenceProject.getOpenActivities(), activityName);
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

	public void login(String initials) throws EmployeeNotFoundException {
		if (doesEmployeeExist(initials)) {
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
	
	public boolean tryIsValidEmployeeInitials(String initials) throws MissingInformationException, DuplicateNameException, TooManyCharsException, IllegalCharException
	{
		if (Tools.isNullOrEmpty(initials)) {
			throw new MissingInformationException("Missing employee initials");
		}
		if (employees.containsKey(initials)) {
			throw new DuplicateNameException("An employee with those initials already exist");
		}
		if (initials.length() > 4) {
			throw new TooManyCharsException("Number of characters has exceeded the maximum of 4");
		}
		if(!initials.matches("\\p{L}+")){
			throw new IllegalCharException("Only letters are allowed for initials");
		}
		if (employees.containsKey(initials)) {
			throw new DuplicateNameException("An employee with those initial already exist");
		}
		return true;
	}
	
	public Project getAbsenceProject() {
		return absenceProject;
	}
}
