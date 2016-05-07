package SoftwareHouse.ExceptionTypes;

public class ProjectAlreadyClosedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3820462918237174778L;

	public ProjectAlreadyClosedException(String message)
	{
		super(message);
	}
}
