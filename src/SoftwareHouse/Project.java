package SoftwareHouse;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.activity.InvalidActivityException;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.ProjectAlreadyClosedException;

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

	
	public void addAcitivity(String title, 
							 String detailText, 
							 List<String> employeeInitials, 
							 Calendar startTime, 
							 Calendar endTime, 
							 int budgettedTime) throws MissingInformationException, 
													   InvalidInformationException, 
													   EmployeeNotFoundException, 
													   DuplicateNameException {
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
		}
		forceAddAcitivity(title, detailText, employeeInitials, startTime, endTime, budgettedTime);
	}
	
	public void forceAddAcitivity(String title, String detailText, List<String> employeeInitials, Calendar startTime, Calendar endTime, int budgettedTime) throws EmployeeNotFoundException, DuplicateNameException {
		if (Tools.containsActivity(openActivities, title)) {
			throw new DuplicateNameException("An activity with that name already exists");
		}
		
		//not sure but i think it makes sense if it throws an nullpointerexception if employeeInitials isn't initialized
		//can't use stream here because oracle fucked up http://stackoverflow.com/questions/27644361/how-can-i-throw-checked-exceptions-from-inside-java-8-streams
		List<Employee> activityEmployees = new ArrayList<Employee>();
		for (String initials : employeeInitials) {
			if (Tools.containsEmployee(employees, initials)) {
				activityEmployees.add(Tools.getEmployeeFromInitials(employees, initials));
			} else {
				throw new EmployeeNotFoundException("Employee with initials: " + initials + " does not exist or is not part of this project");
			}
		}
		openActivities.add(new Activity(title, detailText, activityEmployees, startTime, endTime, budgettedTime, this));	
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
