/**
 * 
 */
package suncertify.client;

/**
 * <code>UBException</code> is a generic class used throughout the application.
 * The application throws <code>UBException</code> if any business logic is not satisfied.
 *  
 * @author nrkkalyan
 * 
 */
public class UBException extends Exception {
	
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * Constructs an <code>UBException</code> with no message.
	 * */
	public UBException() {
	}
	
	/**
	 * Constructs an <code>UBException</code> with the specified detail message. 
	 * @param message
	 */
	public UBException(String message) {
		super(message);
	}
	
}
