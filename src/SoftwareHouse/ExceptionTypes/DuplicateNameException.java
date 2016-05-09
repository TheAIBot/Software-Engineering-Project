package SoftwareHouse.ExceptionTypes;

/**
 * @author Niklas
 */
public class DuplicateNameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 426756873897456042L;

	public DuplicateNameException(String message)
	{
		super(message);
	}
}
