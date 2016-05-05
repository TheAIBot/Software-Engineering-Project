package SoftwareHouse.ExceptionTypes;

public class EmployeeNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8073501746161125877L;

	public EmployeeNotFoundException(String message)
	{
		super(message);
	}
}
