/**
 * 
 */
package suncertify.db;

/**
 * @author eradkal
 * 
 */
public class RecordNotFoundException extends DatabaseException {
	
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
