package suncertify.db;

/**
 * This exception is thrown if no record is found in the database for a given
 * record number.
 * 
 * @author nrkkalyan
 * 
 */
public class RecordNotFoundException extends Exception {
	
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * Creates a new instance of RecordNotFoundException.
	 * 
	 */
	public RecordNotFoundException() {
		super();
	}
	
	/**
	 * Creates a new instance of RecordNotFoundException with an additional
	 * explanation.
	 * 
	 * @param message
	 *            additional information about the exception.
	 */
	public RecordNotFoundException(String message) {
		super(message);
	}
}
