package suncertify.gui;

import java.util.Observable;

import javax.swing.JPanel;

/**
 * 
 * This is a customizable JPanel. Depending on the execution mode provided, the
 * components will be shown. In SERVER mode, DB file path and port number will
 * be shown. In CLIENT_REMOTE mode, host name and port number. In CLIENT_LOCAL
 * only DB file path.
 */

public class ConfigurationPanel extends JPanel {

	public ConfigurationPanel(ExecutionMode executionMode) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setPortText(String port) {
		// TODO Auto-generated method stub

	}

	public Observable getConfigurationObservable() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setHostText(String host) {
		// TODO Auto-generated method stub

	}

	public void setDbHostText(String dbFile) {
		// TODO Auto-generated method stub

	}

}
