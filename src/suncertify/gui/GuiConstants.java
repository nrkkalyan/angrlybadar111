/**
 * 
 */
package suncertify.gui;

/**
 * @author Koosie
 * 
 */
final class GuiConstants {
	
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
