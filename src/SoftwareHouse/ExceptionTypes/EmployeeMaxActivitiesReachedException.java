package SoftwareHouse.ExceptionTypes;

import java.util.List;
import java.util.stream.Collectors;

import SoftwareHouse.Employee;

/**
 * @author Jesper
 */
public class EmployeeMaxActivitiesReachedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -551911119515728293L;

	public EmployeeMaxActivitiesReachedException(String message)
	{
		super(message);
	}
	
	public EmployeeMaxActivitiesReachedException(List<Employee> employees, String messagePart1, String messagePart2) {
		super(messagePart1 + " " + String.join(", ", employees.stream().map(x -> x.getInitials()).collect(Collectors.toList())) + " " + messagePart2);
	}
}
