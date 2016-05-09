package SoftwareHouse.ExceptionTypes;

/**
 * @author Andreas
 */
public class AlreadyLoggedInException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3212934391539194936L;

	public AlreadyLoggedInException(String message) {
		super(message);
	}
}
