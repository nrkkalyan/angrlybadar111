package suncertify;

import suncertify.client.UBException;
import suncertify.client.UrlyBirdClientController;
import suncertify.common.CommonConstants;
import suncertify.gui.UrlyBirdClientFrame;
import suncertify.server.UrlyBirdRmiServer;

/**
 * This class is the entry point of the UrlyBird application. Use "server" in
 * the VM argument to launch the application in network mode. Use "alone" in VM
 * argument to launch the application as non-network mode. If no argument is
 * specified the application will be launched in networked client mode.
 * 
 * @author RadhakrishanKalyan
 * */
public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (args.length == 0) {
				startClient(CommonConstants.NETWORK_MODE_FLAG);
			} else if (CommonConstants.SERVER_MODE_FLAG.equals(args[0])) {
				UrlyBirdRmiServer.start();
			} else if (CommonConstants.STANDALONE_MODE_FLAG.equals(args[0])) {
				startClient(CommonConstants.STANDALONE_MODE_FLAG);
			} else {
				usage();
			}
		} catch (UBException e) {
			e.printStackTrace();
			System.out.println("Could not able to start application. Some error occured. Details :" + e.getMessage());
		}
	}
	
	private static void startClient(final String clientType) {
		UrlyBirdClientFrame frame = new UrlyBirdClientFrame();
		UrlyBirdClientController controller = new UrlyBirdClientController(frame, clientType);
		frame.setSize(700, 700);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		controller.showAllRooms();
		
	}
	
	private static void usage() {
		System.err.println("Usage: java -jar runme.jar [options]\n" + //
				"[options]:\n" + "server\t - Run in server mode.\n" + //
				"alone\t - Run in standalone mode.\n" + //
				"* If no mode is specified, the application will run as network client mode.\n");
		System.exit(1);
	}
	
}
