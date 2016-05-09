package SoftwareHouse;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeAlreadyAssignedException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectAlreadyClosedException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotPartOfEmployeesAdded;

/**
 * @author Jesper
 */
public class Project {
	private static int serialNumber = 0;
	private final int projectNumber;
	private Scheduler scheduler;
	private String name;
	private String costumerName;
	private int budgetedTime;
	private Employee projectManager;
	private TimePeriod timePeriod;
	private boolean isOpen = true;
	private final String REPORTS_PATH = "res/reports/";
	private final String FILE_EXTENTION = ".txt";
	private boolean useAbsenceActivity = false;
	private String detailedText;
	
	private List<Activity> openActivities = new ArrayList<Activity>();
	private List<Activity> closedActivities = new ArrayList<Activity>();
	private List<Activity> deletedActivities = new ArrayList<Activity>();
	private List<Employee> employees = new ArrayList<Employee>();
	
	
	/** Creates a new project from the given parameters. Throws an InvalidProjectInitilizationInput if the input is not valid, 
	 *  which's message contains what is wrong. Must be logged in to create a new project.
	 * @param scheduler The scheduler to attach the project. Must not be null.
	 * @param projectName The project name. Must be unique for the scheduler, not null or "".
	 * @param companyName The name of the company requesting the project. Is allowed to be "" or null.
	 * @param detailedText The detailed description associated with the project. Is allowed to be "" or null.
	 * @param employeesToAdd The employees to add to the project, at the start of it. Is allowed to be empty, or null. All employees must exist.
	 * 				                They can of course not already be part of the project.
	 * @param budgetedTime  The budgeted time for the project. Is not allowed to be negative.
	 * @param initialsProjectManager Initials of the project manager. Is allowed to be empty or null. 
	 *                               If not, the project manager needs to be amongst the list of employees given.
	 * @param timePeriod The time period the project stretches over. Is allowed to be null. 
	 *                   The time period need to be legal, ie. the end date must not be before the start date.
	 * @throws NotLoggedInException To create a project, one needs to be logged in.
	 * @throws EmployeeNotFoundException 
	 * @throws InvalidInformationException 
	 * @throws MissingInformationException 
	 * @throws EmployeeAlreadyAssignedException 
	 * @throws ProjectManagerNotPartOfEmployeesAdded 
	 */	
	public Project(Scheduler scheduler, 
				   String projectName, 
				   String costumerName, 
				   String detailedText, 
				   List<Employee> employeesToAdd, 
				   int budgetedTime, 
				   String initialsProjectManager, 
				   TimePeriod timePeriod)
					 throws NotLoggedInException,
					 		MissingInformationException, 
					 		InvalidInformationException, 
					 		EmployeeNotFoundException, 
					 		EmployeeAlreadyAssignedException, 
					 		ProjectManagerNotPartOfEmployeesAdded
	{
		List<String> employeesInitials = null;
		if (employeesToAdd != null) {
			employeesInitials = employeesToAdd.stream()
					   .map(x -> x.getInitials())
					   .collect(Collectors.toList());
		}
		
		validateinformation(scheduler, projectName, budgetedTime, initialsProjectManager, timePeriod, employeesToAdd, employeesInitials);
		this.scheduler = scheduler;
		this.name = projectName;
		this.costumerName = costumerName;
		this.budgetedTime = budgetedTime;
		this.detailedText = detailedText;
		this.timePeriod = timePeriod;	
		try {
			//It might happen that no manager is given, which would result in an error here. No will be assigned in this case.
			this.projectManager = scheduler.getEmployeeFromInitials(initialsProjectManager); 
		} catch (EmployeeNotFoundException e) {}
		if (employeesInitials != null) {
			for (String employeeInitials : employeesInitials) {
				this.addEmployee(employeeInitials);
			}
		} else employees = new ArrayList<Employee>();
		this.projectNumber = Calendar.getInstance().get(Calendar.YEAR) + serialNumber;
		serialNumber++;
	}
	
	public Project(Scheduler scheduler, String name, boolean isAbsenceProject) throws NotLoggedInException, MissingInformationException, InvalidInformationException, EmployeeNotFoundException, EmployeeAlreadyAssignedException, ProjectManagerNotPartOfEmployeesAdded, EmployeeMaxActivitiesReachedException {
		this(scheduler,name,"","",new ArrayList<Employee>(),0,"",null);
		this.useAbsenceActivity = isAbsenceProject;
		
		openActivities.add(new AbsenceActivity("sygdom", "", null, null, null, 0, this));
		openActivities.add(new AbsenceActivity("ferie", "", null, null, null, 0, this));
		openActivities.add(new AbsenceActivity("kursus", "", null, null, null, 0, this));
	}
	
	private void validateinformation(Scheduler scheduler, 
			String projectName,
		   	int budgetedTime, 
		   	String initialsProjectManager, 
		   	TimePeriod timePeriod,
			List<Employee> employeesToAdd,
		   	List<String> employees) throws MissingInformationException, InvalidInformationException, EmployeeNotFoundException, ProjectManagerNotPartOfEmployeesAdded
	{
		if (scheduler == null) {
			throw new InvalidInformationException("Scheduler can't be null");
		} else if (Tools.isNullOrEmpty(projectName)) {
			throw new MissingInformationException("Missing project name");
		} else if (budgetedTime < 0) {
			throw new InvalidInformationException("Budgetted time can't be negative");
		} else	if(!isProperProjectManagerToAdd(scheduler,initialsProjectManager, employeesToAdd)){
				throw new ProjectManagerNotPartOfEmployeesAdded("The given manager " + initialsProjectManager + " is not a part of the list of employees given." );
		} //And the errors connected to a TimePeriod is handled by the class itself. 
		
	}
	
	public boolean isProperProjectManagerToAdd(Scheduler scheduler,String initialsProjectManager, List<Employee> employeesToAdd) throws EmployeeNotFoundException{
		if (!Tools.isNullOrEmpty(initialsProjectManager)) {
			scheduler.getEmployeeFromInitials(initialsProjectManager); //Throws an error if the manager do not exist
			if (employeesToAdd == null) {
				return false;
			} else return employeesToAdd.stream().anyMatch(x -> x.getInitials().equals(initialsProjectManager));
		} else return true;
	}
		
	public void addAcitivity(String title, 
							 String detailText, 
							 List<String> employeeInitials, 
							 Calendar startTime, 
							 Calendar endTime, 
							 int budgetedTime) throws MissingInformationException, 
													   InvalidInformationException, 
													   EmployeeNotFoundException, 
													   DuplicateNameException, EmployeeMaxActivitiesReachedException, ProjectManagerNotLoggedInException {
		if (Tools.isNullOrEmpty(title)) {
			throw new MissingInformationException("Missing title");
		} else if (Tools.isNullOrEmpty(detailText)) {
			throw new MissingInformationException("Missing detailText");
		} else if (employeeInitials == null || employeeInitials.size() == 0) {
			throw new MissingInformationException("Missing employees");
		} else if (startTime == null) {
			throw new MissingInformationException("Missing start date");
		} else if (endTime == null) {
			throw new MissingInformationException("Missing end date");
		} else if (startTime.after(endTime)) {
			throw new InvalidInformationException("End date has to start after start date");
		} else if (budgetedTime < 0) {
			throw new InvalidInformationException("Budgetted time can't be less than 0");
		} else if (!allEmployeesCanWorkOnMoreActivities(employeeInitials)){
			//TODO Check here(*)
		}
		forceAddAcitivity(title, detailText, employeeInitials, startTime, endTime, budgetedTime);
	}
	
	/** Returns whether or not there are any employees that can work on more activities 
	 *  (ie. if there is an employee that has reached the maximum number of activites that the person can handle).
	 *  If there is an employee amongst the list, that does not exist in the system, false is returned
	 * @param employeeInitials List of initials of emplyees that needs to be checked
	 * @return True if there are no employees that has reached the limit (assuming that all employees exist), else false.
	 */
	private boolean allEmployeesCanWorkOnMoreActivities(List<String> employeeInitials) {
		try {
			return employeesNotAbleToWorkOnMoreActivities(employeeInitials).size() == 0;
		} catch (Exception e) {
			return false;
		} 
	}

	/** Returns a list of employees from a given list of employees, no able to work on more projects. The limit is 20 activities concurrently.
	 *  The employees in the list need to exist.
	 * @param employeeInitials
	 * @return
	 * @throws EmployeeNotFoundException
	 */
	public List<Employee> employeesNotAbleToWorkOnMoreActivities(List<String> employeeInitials) throws EmployeeNotFoundException{
		List<Employee> employeesMaxCapacityReached = new ArrayList<Employee>();
		for (String initials : employeeInitials) {
			Employee currentEmpoyee = scheduler.getEmployeeFromInitials(initials);
			if (!currentEmpoyee.canContainMoreActivities()){
				employeesMaxCapacityReached.add(currentEmpoyee);				
			}			
		}		
		return employeesMaxCapacityReached; 
	}
	
	public void forceAddAcitivity(String title, 
								  String detailText, 
								  List<String> employeeInitials, 
								  Calendar startTime, 
								  Calendar endTime, 
								  int budgetedTime) throws EmployeeNotFoundException, 
														   DuplicateNameException, 
														   EmployeeMaxActivitiesReachedException, 
														   ProjectManagerNotLoggedInException, 
														   InvalidInformationException, 
														   MissingInformationException {	
		if (Tools.isNullOrEmpty(title)) {
			throw new MissingInformationException("Missing activity name");
		} else if (!isNewValidActivityName(title)) {
			throw new DuplicateNameException("An activity with that name already exists");
		} 
		hasPermissionToEdit(); //Throws an error if not true.
		//can't use stream here because oracle fucked up http://stackoverflow.com/questions/27644361/how-can-i-throw-checked-exceptions-from-inside-java-8-streams
		List<Employee> activityEmployees = new ArrayList<Employee>();
		List<Employee> employeesPastMaxActivity = new ArrayList<Employee>();
		if (employeeInitials != null) {
			for (String initials : employeeInitials) {
				if (Tools.containsEmployee(employees, initials)) { //TODO (*) check here.
					Employee currentEmployee = Tools.getEmployeeFromInitials(employees, initials);
					if (currentEmployee.canContainMoreActivities()) {
						activityEmployees.add(Tools.getEmployeeFromInitials(employees, initials));
					}  else employeesPastMaxActivity.add(currentEmployee);
				} else {
					throw new EmployeeNotFoundException("Employee with initials: " + initials + " does not exist or is not part of this project");
				}
			}
			if (employeesPastMaxActivity.size() != 0) {
				throw new EmployeeMaxActivitiesReachedException(employeesPastMaxActivity, "The employees: ", " cannot work on more activities");
			}
		}
		Activity activity;
		if (useAbsenceActivity) {
			activity = new AbsenceActivity(title, detailText, activityEmployees, startTime, endTime, budgetedTime, this);	
		} else {
			activity = new Activity(title, detailText, activityEmployees, startTime, endTime, budgetedTime, this);
		}
		openActivities.add(activity);
	} 
	

	/** Adds employee to the project. Returns true if possible, but if an employee with those initials do not exist,
	 *  or if the employee is already a part of the project, false is returned instead.
	 * @param initials
	 * @return True if the employee exist, and is added to the project, else false.
	 * @throws EmployeeNotFoundException 
	 * @throws EmployeeAlreadyAssignedException 
	 */
	public boolean addEmployee(String initials) throws EmployeeNotFoundException, EmployeeAlreadyAssignedException {
		Employee employee = scheduler.getEmployeeFromInitials(initials);
		boolean employeeKnowsProject = employee.isAlreadyPartOfProject(this);
		boolean projectKnowsEmployee = employees.stream().anyMatch(x -> x.getInitials().equals(initials));
		//A little convoluted if statement, corresponding to an or between the non negated boolean function.
		//Found neccesary to get extra code coverage
		if (!(!employeeKnowsProject && !projectKnowsEmployee)) {
			throw new EmployeeAlreadyAssignedException(initials + " is already a part of the project " + this.name);
		} else	{
			employee.addProject(this);
			employees.add(employee);
			return true;
		}
	}
	
	/**
	 * @return the employees
	 */
	public List<Employee> getEmployees() {
		return employees;
	}

	public void deleteActivity(String activityName) throws ActivityNotFoundException {
		if (!Tools.containsActivity(openActivities, activityName)) {
			throw new ActivityNotFoundException();
		}
		Activity activity = Tools.getActivityFromName(openActivities, activityName);
		openActivities.remove(activity);
		deletedActivities.add(activity);		
	}

	public void closeActivity(String activityName) throws ActivityNotFoundException {
		if (!Tools.containsActivity(openActivities, activityName)) {
			throw new ActivityNotFoundException();
		}
		Activity activity = Tools.getActivityFromName(openActivities, activityName);
		openActivities.remove(activity);
		closedActivities.add(activity);
	}
	
	public void close() throws ProjectAlreadyClosedException {
		if (isOpen) {
			isOpen = false;			
			openActivities.stream().forEach(x -> closedActivities.add(x));
			openActivities.clear();
			
		} else {
			throw new ProjectAlreadyClosedException("The project \"" + name + "\" is already closed");
		}
	}

	public boolean isNewValidActivityName(String activityName)
	{
		if (activityName == null) return false;
		final String lowerCaseActivityName = activityName.toLowerCase().trim();
		return !Tools.isNullOrEmpty(lowerCaseActivityName) && 
				!openActivities.stream()
						 	   .anyMatch(x -> x.getName().toLowerCase().trim().equals(lowerCaseActivityName));
	}
	
	/**
	 * @return the isOpen
	 */
	public boolean isOpen() {
		return isOpen;
	}
	
	public Activity getOpenActivityFromName(String activityName) throws ActivityNotFoundException{
		try {
			//There can only be one activity with a given name, for a given project.
			return openActivities.stream().filter(x -> x.getName().equals(activityName)).findFirst().get(); 		
		} catch (Exception e) {
			throw new ActivityNotFoundException("The open activity: " + activityName + " is not a part of the project: " + this.name);
		}
	}

	public void generateReport() {
		String fileName = getFilePath();
		try (PrintWriter writer = new PrintWriter(fileName, "UTF-8"))
		{
			StringBuilder sBuilder = new StringBuilder();
			openActivities.stream().forEach(x -> sBuilder.append(x.toString()));
			writer.println(sBuilder.toString());
		} catch (Exception e) {	}
	}

	public boolean isProjectManagerLoggedIn()
	{
		if (projectManager == null) {
			return false;
		} else {
			return projectManager.equals(scheduler.getLoggedInEmployee());
		}
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/** Sets the name of the project to a new given name. Throws an error in case of the new name being null, 
	 *  an empty String or if there exist another project, with that name.
	 * @param name the name to set
	 * @throws DuplicateNameException If a project with that name already exists.
	 * @throws MissingInformationException If the name is null or empty/it is not "specified"
	 * @throws ProjectManagerNotLoggedInException 
	 */
	public void setName(String name) throws DuplicateNameException, MissingInformationException, ProjectManagerNotLoggedInException {
		hasPermissionToEdit(); //Throws an error if one does not have permission to edit.
		if (Tools.isNullOrEmpty(name)) {
			throw new MissingInformationException("No name was specified");
		} else if (!scheduler.isNewValidProjectName(name) && !name.equals(getName())) {
			throw new DuplicateNameException("A project with that name already exist");
		}
		this.name = name;
	}

	/**
	 * @return the openActivities
	 */
	public List<Activity> getOpenActivities() {
		return openActivities;
	}

	/**
	 * @return the closedActivities
	 */
	public List<Activity> getClosedActivities() {
		return closedActivities;
	}

	/**
	 * @return the deletedActivities
	 */
	public List<Activity> getDeletedActivities() {
		return deletedActivities;
	}
	
	public Activity getActivity(String name) throws ActivityNotFoundException{
		try{
			return openActivities.stream().filter(x -> x.getName().equals(name)).findAny().get();
		} catch(Exception e){
			throw new ActivityNotFoundException("The activity: " + name + " is not a part of the project: " + this.name);
		}
		
	}
	
	/**
	 * @param isOpen the isOpen to set
	 */
//	public void setOpen(boolean isOpen) {
//		this.isOpen = isOpen;
//	}
	
	/**
	 * @param costumerName the costumerName to set
	 * @throws ProjectManagerNotLoggedInException 
	 */
	public void setCostumerName(String costumerName) throws ProjectManagerNotLoggedInException {
		hasPermissionToEdit(); //Throws an error if one does not have permission to edit.
		this.costumerName = costumerName;
	}

	/** Assigns a new project manager, based on the initials given. If the initials are null or empty, 
	 * the project manager is set to null/no project manager is assigned, and if the new project manager is not part of the project,
	 * an error is thrown
	 * @param projectManager the projectManager to set
	 * @throws ProjectManagerNotPartOfEmployeesAdded The new project manager needs to be a part of the project.
	 * @throws ProjectManagerNotLoggedInException To change the project mangager, 
	 *         one either needs to be the project manager, or there needs to be no project manager.
	 */
	public void setProjectManager(String initialProjecManager) throws ProjectManagerNotPartOfEmployeesAdded, ProjectManagerNotLoggedInException {
		hasPermissionToEdit(); //Throws an error if one does not have permission to edit.
		if (initialProjecManager == null || initialProjecManager == "" ) {
			projectManager = null;			
		} else {
			if (!employees.stream().anyMatch(x -> x.getInitials().equals(initialProjecManager))) {
				throw new ProjectManagerNotPartOfEmployeesAdded("The project manager that is trying to be assignes, " + 
			initialProjecManager + " is not part of the project");
			} else	this.projectManager = employees.stream().filter(x -> x.getInitials().equals(initialProjecManager)).findFirst().get();
		}
	}

	/**
	 * @param timePeriod the timePeriod to set
	 * @throws InvalidInformationException 
	 * @throws ProjectManagerNotLoggedInException 
	 */
	public void setTimePeriod(TimePeriod timePeriod) throws InvalidInformationException, ProjectManagerNotLoggedInException {
		hasPermissionToEdit(); //Throws an error if one does not have permission to edit.
		this.timePeriod = timePeriod;
	}

	/**
	 * @return the detailedText
	 */
	public String getDetailedText() {
		return detailedText;
	}

	/**
	 * @param detailedText the detailedText to set
	 * @throws ProjectManagerNotLoggedInException 
	 */
	public void setDetailedText(String detailedText) throws ProjectManagerNotLoggedInException {
		hasPermissionToEdit(); //Throws an error if one does not have permission to edit.
		this.detailedText = detailedText;
	}

	/**
	 * @param employees the employees to set
	 * @throws InvalidInformationException 
	 * @throws ProjectManagerNotLoggedInException 
	 */
	public void setEmployees(List<Employee> employees) throws InvalidInformationException, ProjectManagerNotLoggedInException {
		hasPermissionToEdit(); //Throws an error if one does not have permission to edit.
		if (employees == null) {
			this.employees = new ArrayList<Employee>();
		} else {
			for (Employee employee : employees) {
				try {
					scheduler.getEmployeeFromInitials(employee.getInitials());
				} catch (EmployeeNotFoundException e) {
					throw new InvalidInformationException("There cannot be added employees to a project, when some of them do not exist");
				}
			}
			this.employees = employees;
			for (Employee employee : employees) {
				if (!employee.isAlreadyPartOfProject(this)) {
					employee.addProject(this);
				}
			}
		}
		
	}
	
	public boolean hasPermissionToEdit() throws ProjectManagerNotLoggedInException{
		if (isProjectManagerLoggedIn() || projectManager == null) {
			return true;
		} else throw new ProjectManagerNotLoggedInException("Either there needs to be no project manager for the project" +
                " or the person needs to be logged in, for edits to be made");

	}

	public String getFilePath() {
		String fileName = name.replaceAll("\\s", "_");
		fileName = fileName.replaceAll("[^\\w.-]", "");
		fileName += FILE_EXTENTION;
		return REPORTS_PATH + fileName;
	}

	/**
	 * @return the budgettedTime
	 */
	public int getBudgettedTime() {
		return budgetedTime;
	}
	
	/**
	 * @param budgettedTime the budgettedTime to set
	 * @throws InvalidInformationException 
	 * @throws ProjectManagerNotLoggedInException 
	 */
	public void setBudgettedTime(int budgetedTime) throws InvalidInformationException, ProjectManagerNotLoggedInException {
		hasPermissionToEdit(); //Throws an error if one does not have permission to edit.
		if (budgetedTime < 0) {
			throw new InvalidInformationException("Budgetted time can't be less than 0");
		}
		this.budgetedTime = budgetedTime;
	}
	/** Gets the TimePeriod associated with this project.
	 * @return
	 */
	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	/** Gets the project manager associated with this project.
	 * @return
	 */
	public Employee getProjectManager() {
		return projectManager;
	}

	public int getBudgetedTime() {
		return budgetedTime;
	}

	public String getCostumerName() {
		return costumerName;
	}
	
	/**
	 * @return the projectNumber
	 */
	public int getProjectNumber() {
		return projectNumber;
	}
}
