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
			this.setTimePeriod(new TimePeriod(startDate, endDate));
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
	 */
	public void setName(String name) throws MissingInformationException, DuplicateNameException {
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
	 */
	public void setDetailText(String detailText) throws MissingInformationException {
		if (Tools.isNullOrEmpty(detailText)) {
			throw new MissingInformationException("Missing detailed text");
		}
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
	 */
	public void setBudgettedTime(int budgettedTime) throws InvalidInformationException {
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
	 */
	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}

	public void addEmployee(String initials) throws EmployeeMaxActivitiesReachedException, EmployeeNotFoundException, EmployeeAlreadyAssignedException {
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
	 */
	public void setAssignedEmployees(List<Employee> assignedEmployees) throws InvalidInformationException {
		if (assignedEmployees == null) {
			throw new InvalidInformationException("Assigned employees can't be null");
		}
		this.assignedEmployees = assignedEmployees;
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
