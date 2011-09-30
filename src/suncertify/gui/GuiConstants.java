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
	
	public enum ActionCommand {
		
		VIEW_ALL, SEARCH_BY_NAME_AND_LOC, BOOK_ROOM, EXIT, CONNECT_LOCAL, CONNECT_REMOTE;
		
		/**
		 * @param actionCommand
		 * @return
		 */
		public static ActionCommand parse(String actionCommand) {
			String[] commands = actionCommand.split("=");
			return ActionCommand.valueOf(commands[0]);
		}
		
		/**
		 * @param pAction
		 * @return
		 */
		public static ActionCommand getCommandByName(String action) {
			try {
				ActionCommand.valueOf(action);
			} catch (Exception e) {
				// Ignore
			}
			
			return null;
		}
		
	}
	
}
