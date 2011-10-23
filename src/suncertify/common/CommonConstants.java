/**
 * 
 */
package suncertify.common;

/**
 * @author Koosie
 * 
 */
public final class CommonConstants {
	public static final String	APPLICATION_NAME	= "UrlyBird 1.1 ";
	
	public static final String	DB_FILE				= "dbfile";
	public static final String	SERVER_PORT			= "serverport";
	public static final String	SERVER_HOST			= "serverhost";
	public static final String	REMOTE_SERVER_NAME	= "/UBRMIServer";
	
	public static final String	CONFIGURATION_FILE	= "suncertify.properties";
	
	public enum ApplicationMode {
		SERVER(APPLICATION_NAME + "Server"), ALONE("Non Network Client"), NETWORK_CLIENT("Network Client");
		
		private final String	mValue;
		
		ApplicationMode(String value) {
			mValue = value;
		}
		
		public String getValue() {
			return mValue;
		}
		
	}
	
	public enum ActionCommand {
		
		SEARCH_BY_NAME_AND_LOC, BOOK_ROOM, EXIT;
		
		/**
		 * @param pAction
		 * @return
		 */
		public static ActionCommand getCommandByName(String action) {
			try {
				return ActionCommand.valueOf(action);
			} catch (Exception e) {
				throw new UnsupportedOperationException("Command not supported " + action);
			}
		}
		
	}
	
}
