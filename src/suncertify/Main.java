package suncertify;

import suncertify.client.UrlyBirdClientController;
import suncertify.common.CommonConstants.ApplicationMode;
import suncertify.gui.UrlyBirdClientFrame;
import suncertify.server.UrlyBirdRmiServer;

/**
 * This class is the entry point class for the UrlyBird 1.1 application. The
 * application accepts either no arguments or one of the following VM arguments:
 * "alone" or "server".
 * 
 * alone : Starts the client application in non network stand alone mode. 
 * server : Starts the application as server.
 * no arguments : Starts the client application in network mode.
 * 
 * Note : Any other arguments will cause the application to exit. However the usage is printed in the console.

 * @author nrkkalyan
 * */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (args.length == 0) {
				startClient(ApplicationMode.NETWORK_CLIENT);
			} else if (ApplicationMode.SERVER.name().toLowerCase()
					.equals(args[0])) {
				startServer();
			} else if (ApplicationMode.ALONE.name().toLowerCase()
					.equals(args[0])) {
				startClient(ApplicationMode.ALONE);
			} else {
				usage();
			}
		} catch (Exception e) {
			System.err
					.println("Could not able to start application. Some error occured. Details :"
							+ e.getMessage());
			System.exit(0);
		}
	}

	private static void startServer() throws Exception {
		UrlyBirdRmiServer.start();
	}

	private static void startClient(final ApplicationMode clientType)
			throws Exception {
		UrlyBirdClientFrame clientFrame = new UrlyBirdClientFrame();
		UrlyBirdClientController controller = new UrlyBirdClientController(clientFrame, clientType);
		clientFrame.setControlPanelActionListener(controller);
		clientFrame.setSize(700, 700);
		clientFrame.setLocationRelativeTo(null);
		clientFrame.setVisible(true);
	}

	private static void usage() {
		System.err
				.println("Usage: java -jar runme.jar [options]\n" + //
						"[options]:\n" + "server\t - Run in server mode.\n" + //
						"alone\t - Run in standalone mode.\n" + //
						"* If no mode is specified, the application will run as network client mode.\n");
		System.exit(0);
	}

}
