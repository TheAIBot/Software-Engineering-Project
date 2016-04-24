package SoftwareHouse;

import SoftwareHouse.ExceptionTypes.InvalidInformationException;

public class RegisteredTime {
<<<<<<< HEAD
	private int time;
	private String message;
	private Employee employee;
	
	public RegisteredTime(int time, String message, Employee employee) {
		this.time = time;
		this.message = message;
		this.employee = employee;
	}

	public int getTime() {
		return time;
	}

	public String getMessage() {
		return message;
	}

	public Employee getEmployee() {
		return employee;
	}
	
	
=======
	private Employee employee;
	private String message;
	private int time;
	
	public RegisteredTime(Employee employee, String message, int time) throws InvalidInformationException
	{
		this.employee = employee;
		this.message = message;
		
		if (time < 0) {
			throw new InvalidInformationException("Used time can't be less than 0");
		}
		
		this.time = time;
	}

	
	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	
	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}
>>>>>>> refs/remotes/origin/Dev
}
