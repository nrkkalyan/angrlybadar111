package suncertify.db;

/**
 * 
 * This exception is thrown if a duplicate record is created in the database.
 * The exception is thrown from the <code>DB.create(String[] data)</code> method
 * 
 * @author nrkkalyan
 * 
 */
public class DuplicateKeyException extends Exception {
	
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * Constructs a new instance of DuplicateKeyException
	 */
	public DuplicateKeyException() {
		super();
	}
	
	/**
	 * Constructs a new instance of DuplicateKeyException with an explanation.
	 * 
	 * @param message
	 *            additional information about the exception.
	 */
	public DuplicateKeyException(String message) {
		super(message);
	}
}
