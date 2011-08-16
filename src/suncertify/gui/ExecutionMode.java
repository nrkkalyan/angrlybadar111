package suncertify.gui;

/**
 * Enum defines the various modes the application can be executed.
 * 
 * @author Koosie
 * */
public enum ExecutionMode {

	/**
	 * To run the application as a server.
	 * */
	SERVER,

	/**
	 * To run the client on a remote system and the client connects to the
	 * server on a network.
	 * */
	CLIENT_REMOTE,

	/**
	 * To run the application on local. No network is been used in this mode.
	 * */
	CLIENT_LOCAL

}
