/**
 * 
 */
package suncertify.common;

/**
 * Class holding constant strings used across the application.
 * 
 * @author nrkkalyan
 * 
 */
public final class CommonConstants {
	
	/** Application name */
	public static final String	APPLICATION_NAME	= "UrlyBird 1.1 ";
	
	/**
	 * This is stored as key for the value pair in the suncertify.properties
	 * file for database file location.
	 */
	public static final String	DB_FILE				= "dbfile";
	
	/**
	 * This is stored as the value pair in the suncertify.properties file for
	 * port number property where the server application must run.
	 */
	public static final String	SERVER_PORT			= "serverport";
	
	/**
	 * This is stored as the value pair in the suncertify.properties file for
	 * host property where the server application must run.
	 */
	public static final String	SERVER_HOST			= "serverhost";
	
	/** The string used for binding the rmi server. */
	public static final String	REMOTE_SERVER_NAME	= "/UBRMIServer";
	
	/** Configuration file name where application settings are saved. */
	public static final String	CONFIGURATION_FILE	= "suncertify.properties";
	
	/** Enumeration defines which mode the application is running. */
	public enum ApplicationMode {
		
		/** Server mode. */
		SERVER(APPLICATION_NAME + "Server"),
		
		/** Non network mode. */
		ALONE("Non Network Client"),
		
		/** Network mode. */
		NETWORK_CLIENT("Network Client");
		
		private final String	mDescription;
		
		/**
		 * Constructor to add description to the enumeration.
		 * 
		 * @param desc
		 *            description for application mode.
		 * */
		ApplicationMode(String desc) {
			mDescription = desc;
		}
		
		/**
		 * Returns the description associated with the enumeration.
		 * 
		 * @return description
		 * */
		public String getDescription() {
			return mDescription;
		}
		
	}
	
	/**
	 * Enumeration defines the command to be executed when events are triggered
	 * from the view.
	 */
	public enum ActionCommand {
		
		/** Search by name and location command. */
		SEARCH_BY_NAME_AND_LOC,
		/** Book room command. */
		BOOK_ROOM,
		/** Exit command. */
		EXIT;
		
		/**
		 * Returns the ActionCommand with the specified name.
		 * 
		 * @param action
		 *            String representation of {@link ActionCommand}
		 *            enumeration.
		 * @return the {@link ActionCommand}
		 * 
		 * @exception IllegalArgumentException
		 *                if the specified enumeration type has no constant with
		 *                the specified name.
		 */
		public static ActionCommand getCommandByName(String action) {
			try {
				return ActionCommand.valueOf(action);
			} catch (Exception e) {
				throw new IllegalArgumentException("Command not supported " + action);
			}
		}
		
	}
	
}
