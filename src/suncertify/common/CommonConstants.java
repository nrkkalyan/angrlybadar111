/**
 * 
 */
package suncertify.common;

/**
 * @author Koosie
 * 
 */
public final class CommonConstants {
	public static final String	SERVER_MODE_FLAG		= "server";
	public static final String	STANDALONE_MODE_FLAG	= "alone";
	public static final String	NETWORK_MODE_FLAG		= "rmi";
	public static final String	APPLICATION_NAME		= "UrlyBird 1.1.1";
	
	// Keys stored in suncertify.properties file
	public static final String	DB_FILE					= "dbfile";
	public static final String	SERVER_PORT				= "serverport";
	public static final String	SERVER_HOST				= "serverhost";
	public static final String	REMOTE_SERVER_NAME		= "/UBRMIServer";
	
	public enum ActionCommand {
		
		VIEW_ALL, SEARCH_BY_NAME_AND_LOC, BOOK_ROOM, EXIT, CONNECT_LOCAL, CONNECT_REMOTE;
		
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
	
	public enum Query {
		VIEW_BY_HOTEL_NAME_LOC, VIEW_BY_HOTEL_NAME, VIEW_BY_LOC, VIEW_ALL
	}
	
}
