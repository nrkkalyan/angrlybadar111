package suncertify.db;

/**
 * This exception is thrown if the database file contains incorrect magic cookie
 * or if a user is trying to modify(unlock, update or delete) a record in the
 * database without owning the lock for that particular record.
 * 
 * @author nrkkalyan
 * 
 */
public class SecurityException extends Exception {
	
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * Creates an instance of SecurityException
	 */
	public SecurityException() {
	}
	
	/**
	 * Creates an instance of SecurityException with an additional error
	 * description.
	 * 
	 * @param message
	 *            additional information about the exception.
	 */
	public SecurityException(String message) {
		super(message);
	}
}
