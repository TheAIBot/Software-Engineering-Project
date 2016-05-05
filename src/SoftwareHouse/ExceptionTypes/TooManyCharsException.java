package SoftwareHouse.ExceptionTypes;

public class TooManyCharsException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7461164055426236902L;

	public TooManyCharsException(String message){
		super(message);
	}
}
