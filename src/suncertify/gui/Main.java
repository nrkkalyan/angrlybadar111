package suncertify.gui;

/**
 * This class is the entry point of the UrlyBird application. Use "server" in
 * the VM argument to launch the application as a Server-GUI. Use "alone" in VM
 * argument to launch the application as Local mode. If no argument is specified
 * the application will be launched in Networked Client mode.
 * 
 * @author RadhakrishanKalyan
 * */
public class Main {

	private static final String SERVER_MODE_FLAG = "server";
	private static final String STANDALONE_MODE_FLAG = "alone";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			// Networked Mode
		} else if (SERVER_MODE_FLAG.equalsIgnoreCase(args[0])) {
			// Server
		} else if (STANDALONE_MODE_FLAG.equalsIgnoreCase(args[0])) {
			// Alone
		}else {
			usage();
		}
	}

	private static void usage() {
	    System.err.println(
	    		"Usage: java -jar runme.jar [options]\n" +
	    		"[options]:\n"+
	    		"server\t - Run in server mode.\n" +
	    		"alone\t - Run in standalone mode.\n" +
	    		"* If no mode is specified, the application will run as network client mode.\n");
	    System.exit(1);
	}

}
