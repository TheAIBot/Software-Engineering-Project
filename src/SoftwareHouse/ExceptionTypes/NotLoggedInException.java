package SoftwareHouse.ExceptionTypes;

/**
 * @author Andreas
 */
public class NotLoggedInException extends Exception {

	public NotLoggedInException() {
		super();
	}
	
	public NotLoggedInException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 817970403923899897L;
}
