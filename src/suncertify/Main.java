package suncertify;

import suncertify.client.UrlyBirdClientController;
import suncertify.common.CommonConstants.ApplicationMode;
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
				startClient(ApplicationMode.NETWORK_CLIENT);
			} else if (ApplicationMode.SERVER.name().toLowerCase().equals(args[0])) {
				startServer();
			} else if (ApplicationMode.ALONE.name().toLowerCase().equals(args[0])) {
				startClient(ApplicationMode.ALONE);
			} else {
				usage();
			}
		} catch (Exception e) {
			System.err.println("Could not able to start application. Some error occured. Details :" + e.getMessage());
			System.exit(0);
		}
	}
	
	private static void startServer() throws Exception {
		UrlyBirdRmiServer ubRmiServer = new UrlyBirdRmiServer();
		ubRmiServer.start();
	}
	
	private static void startClient(final ApplicationMode clientType) throws Exception {
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
		System.exit(0);
	}
	
}
