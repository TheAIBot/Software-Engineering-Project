package SoftwareHouse;

import SoftwareHouse.ExceptionTypes.InvalidInformationException;

/**
 * @author ELL
 * Each instance of the class represents a piece of work or absence period
 */
public class RegisteredTime {
	private Employee employee;
	private String message;
	private int time;

	/**
	 * Emil
	 * @param employee
	 * @param message
	 * @param time
	 * @throws InvalidInformationException
	 */
	public RegisteredTime(Employee employee, String message, int time) throws InvalidInformationException {
		this.employee = employee;
		this.message = message;

		if (time < 0) {
			throw new InvalidInformationException("Used time can't be less than 0");
		}

		this.time = time;
	}

	/**
	 * Andreas
	 * @return
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * Niklas
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Jesper
	 * @return
	 */
	public int getTime() {
		return time;
	}
}
