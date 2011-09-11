package suncertify.db;

/**
 * @author Koosie
 * 
 */
public class DuplicateKeyException extends Exception {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * 
	 */
	public DuplicateKeyException() {
		super();
	}
	
	/**
	 * @param message
	 */
	public DuplicateKeyException(String message) {
		super(message);
	}
}
