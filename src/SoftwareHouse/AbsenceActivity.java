package SoftwareHouse;

import java.util.Calendar;
import java.util.List;

import SoftwareHouse.ExceptionTypes.EmployeeAlreadyAssignedException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;

/**
 * Activity subclass used for recording absence like holiday or absence because of sickness
 */
public class AbsenceActivity extends Activity {

<<<<<<< HEAD:src/SoftwareHouse/AbsenceActivity.java
	public AbsenceActivity(String name, String detailText, List<Employee> employees, Calendar startDate,
			Calendar endDate, int budgettedTime, Project inProject) throws EmployeeMaxActivitiesReachedException {
=======
	public AbsenseActivity(String name, String detailText, List<Employee> employees, Calendar startDate,
			Calendar endDate, int budgettedTime, Project inProject) throws EmployeeMaxActivitiesReachedException, InvalidInformationException {
>>>>>>> refs/remotes/origin/Andreas:src/SoftwareHouse/AbsenseActivity.java
		super(name, detailText, employees, startDate, endDate, budgettedTime, inProject);
	}
	
	public void addEmployee(String initials) throws EmployeeMaxActivitiesReachedException, EmployeeNotFoundException, EmployeeAlreadyAssignedException {
		if (Tools.containsEmployee(inProject.getEmployees(), initials)) {
			if (!Tools.containsEmployee(assignedEmployees, initials)) {
				Employee employee = Tools.getEmployeeFromInitials(inProject.getEmployees(), initials);
				employee.addAbsenceActivity(this);
				assignedEmployees.add(employee);
			} else {
				throw new EmployeeAlreadyAssignedException(initials + " is already assigned to this activity");
			}
		} else {
			throw new EmployeeNotFoundException("Employee does not exists or is not part of this project");
		}
	}

}
