/**
 * 
 */
package suncertify.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

import suncertify.client.UBException;
import suncertify.common.CommonConstants;
import suncertify.gui.PropertiesDialog;

/**
 * @author Koosie
 * 
 */
public class UrlyBirdRmiServer {
	
	private static final PropertiesDialog	pd	= new PropertiesDialog(null, true);
	
	/**
	 * @param string
	 */
	public static void start() throws UBException {
		Properties props = null;
		
		props = pd.loadProperties("suncertify.properties");
		if (props == null) {
			throw new UBException("No suncertify.properties file found.");
		}
		try {
			String host = props.getProperty(CommonConstants.SERVER_HOST).trim();
			String port = props.getProperty(CommonConstants.SERVER_PORT).trim();
			LocateRegistry.createRegistry(Integer.parseInt(port));
			String name = "rmi://" + host + ":" + port + CommonConstants.REMOTE_SERVER_NAME;
			UrlyBirdRmiImpl theserver = new UrlyBirdRmiImpl(props.getProperty(CommonConstants.DB_FILE));
			Naming.rebind(name, theserver);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UBException("Could not able to start RMI server. Some error occured. Details :" + e.getMessage());
		}
	}
	
}
