package SoftwareHouse;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;

import org.junit.validator.PublicClassValidator;

import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.InvalidProjectInitilizationInput;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectAlreadyClosedException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;
import sun.net.www.content.audio.x_aiff;

/**
 * @author Jesper
 */
public class Project {
	private static int loebenummerPart = 0;
	private Scheduler scheduler;
	private String name;
	private String costumerName;
	private int budgetedTime;
	private Employee projectManager;
	private TimePeriod timePeriod;
	private boolean isOpen = true;
	private final String REPORTS_PATH = "res/reports/";
	private final String FILE_EXTENTION = ".txt";
	public int budgettedTime = 0;
	private boolean useAbsenceActivity = false;
	
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
	 */	
	public Project(Scheduler scheduler, String projectName, String costumerName, String detailedText, 
			    List<Employee> employeesToAdd, int budgetedTime, String initialsProjectManager, TimePeriod timePeriod)
					 throws NotLoggedInException, MissingInformationException, InvalidInformationException, EmployeeNotFoundException
	{
		validateinformation(scheduler, projectName, budgetedTime, initialsProjectManager, timePeriod);
		this.scheduler = scheduler;
		this.name = projectName;
		this.costumerName = costumerName;
		this.budgetedTime = budgetedTime;
		try {
			//It might happen that no manager is given, which would result in an error here. No will be assigned in this case.
			this.projectManager = scheduler.getEmployeeFromInitials(initialsProjectManager); 
		} catch (EmployeeNotFoundException e) {
		}
		this.timePeriod = timePeriod;	
		if (employeesToAdd != null) employeesToAdd.stream().forEach(x -> this.addEmployee(x.getInitials()));
	}
	
	public Project(Scheduler scheduler, String name, boolean isAbsenceProject) throws InvalidProjectInitilizationInput, NotLoggedInException, MissingInformationException, InvalidInformationException, EmployeeNotFoundException{
		this(scheduler,name,"","",new ArrayList<Employee>(),0,"",null);
		this.useAbsenceActivity = isAbsenceProject;
	}
	
	private void validateinformation(Scheduler scheduler, 
			String projectName, 
		   	int budgetedTime, 
		   	String initialsProjectManager, 
		   	TimePeriod timePeriod) throws MissingInformationException, InvalidInformationException, EmployeeNotFoundException
	{
		if (scheduler == null) {
			throw new InvalidInformationException("Scheduler can't be null");
		} else if (Tools.isNullOrEmpty(projectName)) {
			throw new MissingInformationException("Missing project name");
		} else if (budgetedTime < 0) {
			throw new InvalidInformationException("Budgetted time can't be negative");
		} else if (!Tools.isNullOrEmpty(initialsProjectManager)) {
			scheduler.getEmployeeFromInitials(initialsProjectManager);
		} else if (timePeriod != null &&
		timePeriod.getStartDate() == null) {
			throw new InvalidInformationException("Time periods start date is empty");
		} else if (timePeriod != null &&
		timePeriod.getEndDate() == null) {
			throw new InvalidInformationException("Time periods end date is empty");
		} else if (timePeriod != null &&
		timePeriod.getStartDate().after(timePeriod.getEndDate())) {
			throw new InvalidInformationException("Start date can't be after the end date");
		}
	}

	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
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
			throw new ActivityNotFoundException();
		}
		
	}
	
	/** Returns a MissingInformationTable object, containing information on what might be, 
	 *  or is, missing, for adding the activity to the project.
	 * @param title Activity name.
	 * @param detailText Detailed information about the activity.
	 * @param employeeInitials List of employees to be added to the activity.
	 * @param startTime Start time of the activity.
	 * @param endTime End time of the activity
	 * @param budgetedTime The budgeted time of the activity.
	 * @return MissingInformationTable containing the above mentioned information.
	 */
	public MissingInformationTable addAcitvityTestMissingInformation(String title, 
			 String detailText, 
			 List<String> employeeInitials, 
			 Calendar startTime, 
			 Calendar endTime, 
			 int budgetedTime) {		
		
		MissingInformationTable tableMissingInformation = new MissingInformationTable();
		if (Tools.isNullOrEmpty(title)) {
			tableMissingInformation.setIsMissingTitle(Tools.isNullOrEmpty(title));
		}
		tableMissingInformation.setIsMissingTitle(Tools.isNullOrEmpty(title));
		tableMissingInformation.setIsMissingDetailText(Tools.isNullOrEmpty(detailText));
		tableMissingInformation.setIsMissingEmployees((employeeInitials == null || employeeInitials.size() == 0));
		tableMissingInformation.setIsMissingStartDay(startTime == null);
		tableMissingInformation.setIsMissingEndDay(endTime == null);
		tableMissingInformation.setIsNotCorrectOrderTime(startTime.after(endTime));
		tableMissingInformation.setIsBudgetedTimeNonNegative(budgetedTime < 0);
		tableMissingInformation.setIsNonexistentEmployee(employeeInitials.stream().allMatch(x -> scheduler.doesEmployeeExist(x)));
		tableMissingInformation.setIsThereEmployeesWhoCantWorkOnMoreActivities(allEmployeesCanWorkOnMoreActivities(employeeInitials));
		return tableMissingInformation;
	}
		
	public void addAcitivity(String title, 
							 String detailText, 
							 List<String> employeeInitials, 
							 Calendar startTime, 
							 Calendar endTime, 
							 int budgetedTime) throws MissingInformationException, 
													   InvalidInformationException, 
													   EmployeeNotFoundException, 
													   DuplicateNameException, EmployeeMaxActivitiesReachedException {
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
	
	public void forceAddAcitivity(String title, String detailText, List<String> employeeInitials, Calendar startTime, Calendar endTime, int budgetedTime) 
			throws EmployeeNotFoundException, DuplicateNameException, EmployeeMaxActivitiesReachedException {
		if (Tools.containsActivity(openActivities, title)) {
			throw new DuplicateNameException("An activity with that name already exists");
		}		
		//not sure but i think it makes sense if it throws an nullpointerexception if employeeInitials isn't initialized
		//can't use stream here because oracle fucked up http://stackoverflow.com/questions/27644361/how-can-i-throw-checked-exceptions-from-inside-java-8-streams
		List<Employee> activityEmployees = new ArrayList<Employee>();
		List<Employee> employeesPastMaxActivity = new ArrayList<Employee>();
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
	 */
	public boolean addEmployee(String initials) {
		Employee employee = null;
		try {
			employee = scheduler.getEmployeeFromInitials(initials);
		} catch (Exception e) {
			return false;
		}
		if (employee.isAlreadyPartOfProject(this) || employees.contains(initials)) {
			return false;
			//This should always be true, as the check for if it is possible, is made above. It does, however make the code shorter.
		} else	return (employee.addProject(this) && employees.add(employee)); 
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
			throw new ActivityNotFoundException();
		}
	}

	/**
	 * @param isOpen the isOpen to set
	 */
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public void generateReport() {
		PrintWriter writer = null;
		String fileName = getFilePath();
		try {
			writer = new PrintWriter(fileName, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		for (Activity activity : openActivities) {
			writer.println("Activity name: " + activity.getName());
			writer.println("Bugeted time: " + activity.getBudgettedTime());
			writer.println("Detailed text: " + activity.getDetailText());
			String str = "Employee initials: ";
			for (Employee employee : activity.getAssignedEmployees()) {
				str += employee.getInitials() + " ";
			}
			writer.println(str);
			writer.println("\n");
		}
		
		writer.close();
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj == null || !(obj instanceof Project))? false: ((Project) obj).getName().equals(this.name);
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
		return budgettedTime;
	}

	/**
	 * @param budgettedTime the budgettedTime to set
	 * @throws InvalidInformationException 
	 */
	public void setBudgettedTime(int budgettedTime) throws InvalidInformationException {
		if (budgettedTime < 0) {
			throw new InvalidInformationException("Budgetted time can't be less than 0");
		}
		this.budgettedTime = budgettedTime;
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

	public String getCompanyName() {
		return costumerName;
	}
	
	public int getLoebenummerPart() {
		return loebenummerPart;
	}
}
