package suncertify.gui;

import java.io.File;
import java.net.InetAddress;

/**
 * This class validates the configurations. i.e hostname, port, database file
 * location etc..
 * */

public class ConfigurationValidator {

	private static final int PORT_MIN_VALUE = 0;
	private static final int PORT_MAX_VALUE = 65535;
	private final ExecutionMode executionMode;

	/**
	 * Creates a ConfigurationValidator for the specified execution mode.
	 * 
	 * @param executionMode
	 *            the execution mode type coming from the enum ExecutionMode.
	 */
	public ConfigurationValidator(ExecutionMode executionMode) {
		this.executionMode = executionMode;
	}

	public boolean validate(String hostName, String dBFilePath, int port) {
		if (executionMode == ExecutionMode.CLIENT_LOCAL) {
			return validateDBFilePath(dBFilePath);
		} else if (executionMode == ExecutionMode.CLIENT_REMOTE) {
			return validateHostAndPort(hostName, port);
		} else if (executionMode == ExecutionMode.SERVER) {
			return validateDbFileAndPort(dBFilePath, port);
		}
		return true;
	}

	private boolean validateDbFileAndPort(String dBFilePath, int port) {
		if (validateDBFilePath(dBFilePath) && validatePortNumber(port)) {
			return true;
		}
		return false;
	}

	private boolean validatePortNumber(int port) {
		return port >= PORT_MIN_VALUE && port <= PORT_MAX_VALUE;
	}

	private boolean validateHostAndPort(String hostName, int port) {
		if (!validatePortNumber(port) || hostName == null
				|| hostName.trim().isEmpty()) {
			return false;
		}
		try {
			InetAddress.getAllByName(hostName);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean validateDBFilePath(String dBFilePath) {
		if (dBFilePath != null) {
			File file = new File(dBFilePath);
			if (file.exists() && file.canRead() && file.canWrite()) {
				return true;
			}
		}
		return false;
	}
}
