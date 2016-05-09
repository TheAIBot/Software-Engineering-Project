package SoftwareHouse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeAlreadyAssignedException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotLoggedInException;
import sun.net.www.content.audio.x_aiff;

public class Activity {
	
	protected String name;
	protected String detailText;
	protected List<Employee> assignedEmployees = new ArrayList<Employee>();
	protected TimePeriod timePeriod;
	protected int budgettedTime;
	protected final Project inProject;
	
	public Activity(String name, String detailText, List<Employee> employees, Calendar startDate, Calendar endDate, int budgettedTime, Project inProject) throws EmployeeMaxActivitiesReachedException, InvalidInformationException {
		this.name = name;
		this.detailText = detailText;
		if (employees != null) {
			this.assignedEmployees.addAll(employees);
			for (Employee employee : employees) {
				employee.addActivity(this);
			}
		}
		if (startDate != null & endDate != null) {
			this.timePeriod = new TimePeriod(startDate, endDate);
		}
		this.budgettedTime = budgettedTime;
		this.inProject = inProject;
	}

	/**
	 * @return the title
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param title the title to set
	 * @throws MissingInformationException 
	 * @throws ProjectManagerNotLoggedInException 
	 */
	public void setName(String name) throws MissingInformationException, DuplicateNameException, ProjectManagerNotLoggedInException {
		inProject.hasPermissionToEdit(); //Throws an error if one (the logged in user) does not have permission to edit.
		if (Tools.isNullOrEmpty(name)) {
			throw new MissingInformationException("Missing name");
		} else if (!inProject.isNewValidActivityName(name) && !name.equals(getName())) {
			throw new DuplicateNameException("An activity with the specified name already exists");
		}
		this.name = name;
	}

	/**
	 * @return the detailText
	 */
	public String getDetailText() {
		return detailText;
	}

	/**
	 * @param detailText the detailText to set
	 * @throws ProjectManagerNotLoggedInException 
	 */
	public void setDetailText(String detailText) throws MissingInformationException, ProjectManagerNotLoggedInException {
		inProject.hasPermissionToEdit(); //Throws an error if one (the logged in user) does not have permission to edit.
		this.detailText = detailText;
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
	 * @throws ProjectManagerNotLoggedInException 
	 */
	public void setBudgettedTime(int budgettedTime) throws InvalidInformationException, ProjectManagerNotLoggedInException {
		inProject.hasPermissionToEdit(); //Throws an error if one (the logged in user) does not have permission to edit.
		if (budgettedTime < 0) {
			throw new InvalidInformationException("Budgetted time can't be less than 0");
		}
		this.budgettedTime = budgettedTime;
	}

	/**
	 * @return the assignedEmployees
	 */
	public List<Employee> getAssignedEmployees() {
		return assignedEmployees;
	}

	/**
	 * @return the timePeriod
	 */
	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	/**
	 * @param timePeriod the timePeriod to set
	 * @throws ProjectManagerNotLoggedInException 
	 */
	public void setTimePeriod(TimePeriod timePeriod) throws ProjectManagerNotLoggedInException {
		inProject.hasPermissionToEdit(); //Throws an error if one (the logged in user) does not have permission to edit.
		this.timePeriod = timePeriod; //The timeperiod itself handles possible errors.
	}

	public void addEmployee(String initials) throws EmployeeMaxActivitiesReachedException, EmployeeNotFoundException, EmployeeAlreadyAssignedException, ProjectManagerNotLoggedInException {
		inProject.hasPermissionToEdit();
		if (Tools.containsEmployee(inProject.getEmployees(), initials)) {
			if (!Tools.containsEmployee(assignedEmployees, initials)) {
				Employee employee = Tools.getEmployeeFromInitials(inProject.getEmployees(), initials);
				employee.addActivity(this);
				assignedEmployees.add(employee);
			} else {
				throw new EmployeeAlreadyAssignedException(initials + " is already assigned to this activity");
			}
		} else {
			throw new EmployeeNotFoundException("Employee does not exists or is not part of this project");
		}
	}

	public String getProjectName() {
		return inProject.getName();
	}

	/**
	 * @return the inProject
	 */
	public Project getInProject() {
		return inProject;
	}

	/**
	 * @param assignedEmployees the assignedEmployees to set
	 * @throws InvalidInformationException 
	 * @throws ProjectManagerNotLoggedInException 
	 */
	public void setAssignedEmployees(List<Employee> assignedEmployees) throws InvalidInformationException, ProjectManagerNotLoggedInException {
		inProject.hasPermissionToEdit(); //Throws an error if one (the logged in user) does not have permission to edit.
		if (assignedEmployees == null) {
			this.assignedEmployees = new ArrayList<Employee>();
		} else this.assignedEmployees = assignedEmployees;
	}
	
	public String toString()
	{
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("Activity name: ");
		sBuilder.append(name);
		sBuilder.append(System.getProperty("line.separator"));
		sBuilder.append("Bugeted time: ");
		sBuilder.append(budgettedTime);
		sBuilder.append(System.getProperty("line.separator"));
		sBuilder.append("Detailed text: ");
		sBuilder.append(detailText);
		sBuilder.append(System.getProperty("line.separator"));
		sBuilder.append("Employee initials: ");
		final List<String> employeeInitials = assignedEmployees.stream()
														 	    .map(x -> x.getInitials())
														 	    .collect(Collectors.toList());
		sBuilder.append(String.join(", ", employeeInitials));
		
		sBuilder.append(System.getProperty("line.separator"));
		sBuilder.append(System.getProperty("line.separator"));
		
		return sBuilder.toString();
	}
}
