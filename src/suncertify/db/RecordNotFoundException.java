/**
 * 
 */
package suncertify.db;

/**
 * 
 * @author kalyan
 * 
 */
public class RecordNotFoundException extends DatabaseException {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	/** 
	 * 
	 */
	public RecordNotFoundException() {
		super();
	}
	
	/**
	 * @param message
	 */
	public RecordNotFoundException(String message) {
		super(message);
	}
}
