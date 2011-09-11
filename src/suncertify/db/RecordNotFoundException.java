package suncertify.db;

/**
 * 
 * @author Koosie
 * 
 */
public class RecordNotFoundException extends Exception {
	
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
