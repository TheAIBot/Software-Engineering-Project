package SoftwareHouse;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore.LoadStoreParameter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.activity.InvalidActivityException;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.xml.internal.fastinfoset.algorithm.BooleanEncodingAlgorithm;

import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.AddActivityException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.ProjectAlreadyClosedException;
import sun.net.www.content.audio.x_aiff;

public class Project {
	private Scheduler scheduler;
	private String name;
	private boolean isOpen = true;
	private final String REPORTS_PATH = "res/reports/";
	private final String FILE_EXTENTION = ".txt";
	
	private List<Activity> openActivities = new ArrayList<Activity>();
	private List<Activity> closedActivities = new ArrayList<Activity>();
	private List<Activity> deletedActivities = new ArrayList<Activity>();
	
	private List<Employee> employees = new ArrayList<Employee>();
	
	
	public Project(Scheduler scheduler, String projectName)
	{
		this.scheduler = scheduler;
		this.name = projectName;
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
	
	
	public MissingInformationTable addAcitvityTestMissingInformation(String title, 
			 String detailText, 
			 List<String> employeeInitials, 
			 Calendar startTime, 
			 Calendar endTime, 
			 int budgettedTime) throws EmployeeNotFoundException, EmployeeMaxActivitiesReachedException {
MissingActivityInformation tableMissingInformation = new MissingInformationTable();
		if (Tools.isNullOrEmpty(title)) {
			tableMissingInformation.setIsMissingTitle(Tools.isNullOrEmpty(title));
		}
		tableMissingInformation.setIsMissingTitle(Tools.isNullOrEmpty(title));
		tableMissingInformation.setIsMissingDetailText(Tools.isNullOrEmpty(detailText));
		tableMissingInformation.setIsMissingEmployees((employeeInitials == null || employeeInitials.size() == 0));
		tableMissingInformation.setIsMissingStartDay(startTime == null);
		tableMissingInformation.setIsMissingEndDay(endTime == null);
		tableMissingInformation.setIsNotCorrectOrderTime(startTime.after(endTime));
		tableMissingInformation.setIsBudgetedTimeNonNegative(budgettedTime < 0);
		tableMissingInformation.setIsNonexistentEmployee(employeeInitials.stream().allMatch(x -> scheduler.doesEmployeeExist(x)));
		tableMissingInformation.setIsThereEmployeesWhoCantWorkOnMoreActivities(employeesNotAbleToWorkOnMoreActivities(employeeInitials).size() != 0);
		return tableMissingInformation;
	}
	
	
	public void addAcitivity(String title, 
							 String detailText, 
							 List<String> employeeInitials, 
							 Calendar startTime, 
							 Calendar endTime, 
							 int budgettedTime) throws MissingInformationException, 
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
		} else if (budgettedTime < 0) {
			throw new InvalidInformationException("Budgetted time can't be less than 0");
		} else if (!allEmployeesCanWorkOnMoreActivities(employeeInitials)){
			
		}
		forceAddAcitivity(title, detailText, employeeInitials, startTime, endTime, budgettedTime);
	}
	
	public List<Employee> employeesNotAbleToWorkOnMoreActivities(List<String> employeeInitials) throws EmployeeNotFoundException, EmployeeMaxActivitiesReachedException{
		boolean maxActivitySurpassed = false;
		List<Employee> employeesMaxCapacityReached = new ArrayList<Employee>();
		for (String initials : employeeInitials) {
			Employee currentEmpoyee = scheduler.getEmployeeFromInitials(initials);
			if ((currentEmpoyee).canContainMoreActivities()){
				maxActivitySurpassed = true;
				employeesMaxCapacityReached.add(currentEmpoyee);				
			}			
		}
		//if (maxActivitySurpassed) throw new EmployeeMaxActivitiesReachedException(employeesMaxCapacity, "The employee(s): ", " has already reached their max capacity of assigned activitres, why they can't be assigned to more");
		return employeesMaxCapacityReached; //Will only return true, else it will throw an error. It simply looks nicer in an if statement, why bit is made this way.
	}
	
	public void forceAddAcitivity(String title, String detailText, List<String> employeeInitials, Calendar startTime, Calendar endTime, int budgettedTime) throws EmployeeNotFoundException, DuplicateNameException {
		if (Tools.containsActivity(openActivities, title)) {
			throw new DuplicateNameException("An activity with that name already exists");
		}
		
		//not sure but i think it makes sense if it throws an nullpointerexception if employeeInitials isn't initialized
		//can't use stream here because oracle fucked up http://stackoverflow.com/questions/27644361/how-can-i-throw-checked-exceptions-from-inside-java-8-streams
		List<Employee> activityEmployees = new ArrayList<Employee>();
		for (String initials : employeeInitials) {
			if (Tools.containsEmployee(employees, initials) && ) {
				activityEmployees.add(Tools.getEmployeeFromInitials(employees, initials));
			} else {
				throw new EmployeeNotFoundException("Employee with initials: " + initials + " does not exist or is not part of this project");
			}
		}
		Activity activityToAdd = new Activity(title, detailText, activityEmployees, startTime, endTime, budgettedTime, this);
		List<Employee> employeesPastMaxActivity = new ArrayList<Employee>();
		for (Employee employee2 : employeesPastMaxActivity) {
			if (employee2.addActivity(activityToAdd)) {
				employeesPastMaxActivity.add(employees.get(i));
				employees.remove(i);
			}
			try {
				employee2.addActivity(activityToAdd);				
			} catch (Exception e) {
				employeesPastMaxActivity.add(employees.get(i));
				employees.remove(i);
			}
		}
		openActivities.add(activityToAdd);	
	}

	public void addEmployee(String initials) throws EmployeeNotFoundException {
		Employee employee = scheduler.getEmployeeFromInitials(initials);
		employee.addProject(this);
		employees.add(employee);
		
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

	public String getFilePath() {
		String fileName = name.replaceAll("\\s", "_");
		fileName = fileName.replaceAll("[^\\w.-]", "");
		fileName += FILE_EXTENTION;
		return REPORTS_PATH + fileName;
	}
}
