package SoftwareHouse.ExceptionTypes;

/**
 * @author ELL
 */
public class EmployeeAlreadyAssignedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2733395848908930987L;

	public EmployeeAlreadyAssignedException(String message)
	{
		super(message);
	}
}
