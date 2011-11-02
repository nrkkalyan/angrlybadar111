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
	
	/** Application name*/
	public static final String	APPLICATION_NAME	= "UrlyBird 1.1 ";
	
	/** This is stored as key for the value pair in the suncertify.properties file for database file location.*/
	public static final String	DB_FILE				= "dbfile";
	
	/** This is stored as the value pair in the suncertify.properties file for port number property where the server application must run. */
	public static final String	SERVER_PORT			= "serverport";
	
	/** This is stored as the value pair in the suncertify.properties file for host property where the server application must run. */
	public static final String	SERVER_HOST			= "serverhost";
	
	/** The string used for binding the rmi server. */
	public static final String	REMOTE_SERVER_NAME	= "/UBRMIServer";
	
	/** Configuration file name where application settings are saved.*/
	public static final String	CONFIGURATION_FILE	= "suncertify.properties";
	
	/** Enum defines which mode the application is running.*/
	public enum ApplicationMode {
		SERVER(APPLICATION_NAME + "Server"), ALONE("Non Network Client"), NETWORK_CLIENT("Network Client");
		
		private final String	mDescription;
		
		/** Constructor to add description to the enum.*/
		ApplicationMode(String desc) {
			mDescription = desc;
		}
		
		/**Returns the description associated with the enum.
		 * 
		 * @return description
		 * */
		public String getDescription() {
			return mDescription;
		}
		
	}
	
	/** Enum defines the command to be executed when events are triggered from the view.*/
	public enum ActionCommand {
		
		SEARCH_BY_NAME_AND_LOC, BOOK_ROOM, EXIT;
		
		/**
		 * Returns the ActionCommand with the specified name.
		 * 
		 * @param action 
		 * 			String representation of {@link ActionCommand} enum.
		 * @return the {@link ActionCommand}
		 * 
		 * @exception IllegalArgumentException if the specified enum type has
		 *         	  no constant with the specified name. 
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
