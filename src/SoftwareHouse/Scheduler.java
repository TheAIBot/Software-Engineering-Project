package SoftwareHouse;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeAlreadyAssignedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.IllegalCharException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
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
	private final Project absenceProject;
	
	/**
	 * Creates the absence project used to recording absence activites
	 */
	public Scheduler() {
		anyoneLoggedIn = true; //One needs to be logged in to make a project.
		try {
			this.absenceProject = new Project(this, "Fraværs Project", true); 
			//TODO test creation of absence project if it hasn't been done already
		} catch (Exception e) { 
			throw new NullPointerException("Couldn't create the absense project");
		}
		anyoneLoggedIn = false;
	}

	/**
	 * Creates a blank project
	 * It is valid only to specify the project name, but the other fields will need to be given before the project can be manipulated properly
	 * @param projectName
	 * @throws MissingInformationException
	 * @throws DuplicateNameException
	 * @throws NotLoggedInException
	 * @throws InvalidInformationException
	 * @throws EmployeeNotFoundException
	 * @throws EmployeeAlreadyAssignedException
	 */
public void createProject(String projectName) throws MissingInformationException, DuplicateNameException, NotLoggedInException, InvalidInformationException, EmployeeNotFoundException, EmployeeAlreadyAssignedException, ProjectManagerNotPartOfEmployeesAdded 
	{
		createProject(projectName, "", "", null, 0, "", null);
	}
	
	/**
	 * Creates a full project with all information specified
	 * @param projectName
	 * @param costumerName
	 * @param detailedText
	 * @param employeesToAdd
	 * @param budgettedTime
	 * @param initialsProjectManager
	 * @param timePeriod
	 * @throws NotLoggedInException
	 * @throws MissingInformationException
	 * @throws InvalidInformationException
	 * @throws EmployeeNotFoundException
	 * @throws DuplicateNameException
	 * @throws EmployeeAlreadyAssignedException
	 */
	public void createProject(String projectName, 
							  String costumerName, 
							  String detailedText, 
							  List<Employee> employeesToAdd, 
							  int budgettedTime, 
							  String initialsProjectManager, 
							  TimePeriod timePeriod) throws NotLoggedInException, MissingInformationException, InvalidInformationException, EmployeeNotFoundException, DuplicateNameException, EmployeeAlreadyAssignedException, ProjectManagerNotPartOfEmployeesAdded
	{
		if (isAnyoneLoggedIn()) {
			if (Tools.isNullOrEmpty(projectName)) {
				throw new MissingInformationException("Missing project name");
			} else if (!isNewValidProjectName(projectName)) {
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
	
	/**
	 * @param initials
	 * @return Employee
	 * @throws EmployeeNotFoundException
	 */
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

	/** Return whether or not all the given employees exist. 
	 * @param employees The employees
	 * @return True if all exists, else false.
	 */
	public boolean doAllEmployeesExist(List<Employee> employees) {
		return (employees.stream().allMatch(x -> this.doesEmployeeExist(x.getInitials())));			
	}
	
	/**
	 * @param projectName
	 * @return Project
	 * @throws ProjectNotFoundException
	 * @throws NotLoggedInException
	 */
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
	
	public boolean isNewValidProjectName(String projectName)
	{
		final String lowerCaseProjectName = projectName.toLowerCase().trim();
		return !Tools.isNullOrEmpty(lowerCaseProjectName) && 
				!projects.stream()
						 .anyMatch(x -> x.getName().toLowerCase().trim().equals(lowerCaseProjectName));
	}
	
	/**
	 * @param partOfProjectName
	 * @return List<Project>
	 * @throws NotLoggedInException
	 */
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
	
 	/**
 	 * @param initials
 	 * @throws MissingInformationException
 	 * @throws DuplicateNameException
 	 * @throws TooManyCharsException
 	 * @throws IllegalCharException
 	 */
 	public void addEmployee(String initials) throws MissingInformationException, DuplicateNameException, TooManyCharsException, IllegalCharException {
		if (tryIsValidEmployeeInitials(initials)) {
			employees.put(initials, new Employee(this, initials));
		}
	}

	/**
	 * @param projectName
	 * @param activityName
	 * @return Activity
	 * @throws ProjectNotFoundException
	 * @throws ActivityNotFoundException
	 * @throws NotLoggedInException
	 */
	public Activity getActivity(String projectName, String activityName) throws ProjectNotFoundException, ActivityNotFoundException, NotLoggedInException {
		if (isAnyoneLoggedIn()) {
			if (Tools.containsProject(projects, projectName) || Tools.containsProject(java.util.Collections.singletonList(absenceProject), projectName)) {
				Project project = getProject(projectName);
				//need to handle that the activity is deleted or archived
				//will add that functionality later
				if (Tools.containsActivity(project.getOpenActivities(), activityName)) {
					return Tools.getActivityFromName(project.getOpenActivities(), activityName);
				//}else if (Tools.containsActivity(absenceProject.getOpenActivities(), activityName)) { //not needed because getProejct() can also return an absence project
					//return Tools.getActivityFromName(absenceProject.getOpenActivities(), activityName);
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
	
	/**
	 * Helping method used in the process of adding employees in the internal system.
	 * Tests that the initials are valid
	 * @param initials
	 * @return true if employee initials are valid, false otherwise
	 * @throws MissingInformationException
	 * @throws DuplicateNameException
	 * @throws TooManyCharsException
	 * @throws IllegalCharException
	 */
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
		//match all unicode characters that are part of a alphabet and the initials has atleast 1 character in it
		if(!initials.matches("\\p{L}+")){
			throw new IllegalCharException("Only letters are allowed for initials");
		}
		return true;
	}
	
	public Project getAbsenceProject() {
		return absenceProject;
	}
}
