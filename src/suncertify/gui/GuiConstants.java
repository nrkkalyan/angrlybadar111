/**
 * 
 */
package suncertify.gui;

/**
 * @author Koosie
 * 
 */
final class GuiConstants {
	public static final String	SERVER_MODE_FLAG		= "server";
	public static final String	STANDALONE_MODE_FLAG	= "alone";
	public static final String	NETWORK_MODE_FLAG		= "rmi";
	public static final String	APPLICATION_NAME		= "UrlyBird 1.1.1";
	
	public enum Commands {
		
		SEARCH_ALL, SEARCH_BY_NAME_AND_LOC, BOOK_ROOM, EXIT;
		
		/**
		 * @param actionCommand
		 * @return
		 */
		public static Commands parse(String actionCommand) {
			String[] commands = actionCommand.split("=");
			return Commands.valueOf(commands[0]);
		}
		
	}
	
}
