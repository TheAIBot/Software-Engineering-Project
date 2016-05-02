package SoftwareHouse.ExceptionTypes;

import java.util.List;

import SoftwareHouse.Employee;

public class EmployeeMaxActivitiesReachedException extends Exception {

	public EmployeeMaxActivitiesReachedException(String message)
	{
		super(message);
	}
	
	public EmployeeMaxActivitiesReachedException(List<Employee> employees, String messagePart1, String messagePart2) {
		super(messagePart1 + " " + employees.toString() + " " + messagePart2);
	}
}
