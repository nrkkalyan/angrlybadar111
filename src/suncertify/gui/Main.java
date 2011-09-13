package suncertify.gui;

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
		if (args.length == 0) {
			UrlyBirdClientMain.start("rmi");
		} else if (GuiConstants.SERVER_MODE_FLAG.equalsIgnoreCase(args[0])) {
			UrlyBirdRMIServer.start(GuiConstants.SERVER_MODE_FLAG);
		} else if (GuiConstants.STANDALONE_MODE_FLAG.equalsIgnoreCase(args[0])) {
			UrlyBirdClientMain.start(GuiConstants.STANDALONE_MODE_FLAG);
		} else {
			usage();
		}
	}
	
	private static void usage() {
		System.err.println("Usage: java -jar runme.jar [options]\n" + "[options]:\n" + "server\t - Run in server mode.\n" + "alone\t - Run in standalone mode.\n"
				+ "* If no mode is specified, the application will run as network client mode.\n");
		System.exit(1);
	}
	
}
