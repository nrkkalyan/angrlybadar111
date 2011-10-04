/**
 * 
 */
package suncertify.common;

/**
 * @author Koosie
 * 
 */
public final class GuiConstants {
	public static final String	SERVER_MODE_FLAG		= "server";
	public static final String	STANDALONE_MODE_FLAG	= "alone";
	public static final String	NETWORK_MODE_FLAG		= "rmi";
	public static final String	APPLICATION_NAME		= "UrlyBird 1.1.1";
	
	public enum ActionCommand {
		
		VIEW_ALL, SEARCH_BY_NAME_AND_LOC, BOOK_ROOM, EXIT, CONNECT_LOCAL, CONNECT_REMOTE;
		
		/**
		 * @param pAction
		 * @return
		 */
		public static ActionCommand getCommandByName(String action) {
			try {
				int x = action.indexOf(":");
				ActionCommand.valueOf(action.substring(0, x - 1));
			} catch (Exception e) {
				// Ignore
			}
			throw new UnsupportedOperationException("Command not supported " + action);
		}
		
	}
	
	public enum Query {
		VIEW_BY_HOTEL_NAME_LOC, VIEW_BY_HOTEL_NAME, VIEW_BY_LOC, VIEW_ALL
	}
	
}
