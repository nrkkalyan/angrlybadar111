package suncertify.db;

/**
 * @author Koosie
 * 
 */
public class SecurityException extends Exception {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * 
	 */
	public SecurityException() {
	}
	
	/**
	 * @param message
	 */
	public SecurityException(String message) {
		super(message);
	}
}
