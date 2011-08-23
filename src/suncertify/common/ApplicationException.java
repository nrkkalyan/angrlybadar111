package suncertify.common;

/**
 * Runtime Exceptions thrown by the application.
 */
public class ApplicationException extends Exception {
	
	private static final long	serialVersionUID	= 1L;
	
	public ApplicationException(Throwable e) {
		super(e);
	}
	
	public ApplicationException(ErrorCodes errorCode) {
		super(errorCode.name());
	}
	
}
