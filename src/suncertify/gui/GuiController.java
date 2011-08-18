package suncertify.gui;

/**
 * This class acts as a controller between the GUI and the Business logic.
 * 
 * @author nrkkalyan
 * */
public class GuiController {
	
	private DBServices		mDBServices;
	private final String[]	mCriteria	= new String[] { "true", null, null, null, null, null, null, null };
	
	public GuiController(ExecutionMode executionMode, String dbFileName, String hostName, int port) {
		
		DBConnector connector;
		switch (executionMode) {
			case CLIENT_LOCAL: {
				connector = new DBConnectorLocal(dbFileName);
				break;
			}
			case CLIENT_REMOTE: {
				connector = new DBConnectorRemote();
				break;
				
			}
			case SERVER: {
				connector = new DBConnectorLocal(dbFileName);
				RegistryUtility.register(dbFileName, port);
				break;
			}
				
			default: {
				throw new IllegalArgumentException("Unsupported Execution Mode : " + executionMode);
			}
				mDBServices = connector.getDBClient();
		}
		
	}
	
}
