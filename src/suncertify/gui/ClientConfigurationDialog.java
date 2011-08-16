package suncertify.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import suncertify.gui.ConfigUpdate.ConfigUpdatesEnum;

public class ClientConfigurationDialog extends JDialog implements Observer, ActionListener {
	
	/**
	 * 
	 */
	private static final long				serialVersionUID	= 1L;
	
	private static final int				PORT_DEFAULT_VALUE	= 1234;
	
	private final AbstractApplicationWindow	mApplicationFrame;
	private final ExecutionMode				mExecutionMode;
	private final ConfigurationValidator	mConfigValidator;
	private JOptionPane						mOptionPane;
	private String							mHost;
	private String							mDBFilePath;
	private int								mPort				= PORT_DEFAULT_VALUE;
	private JButton							mConnectButton;
	private JButton							mQuitButton;
	
	public ClientConfigurationDialog(AbstractApplicationWindow abstractApplicationWindow, ExecutionMode executionMode) {
		super(abstractApplicationWindow, true);
		this.mApplicationFrame = abstractApplicationWindow;
		this.mExecutionMode = executionMode;
		this.mConfigValidator = new ConfigurationValidator(executionMode);
		setResizable(false);
		setTitle("Database Configuration:");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		configOptionPane();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				mOptionPane.setValue(JOptionPane.CANCEL_OPTION);
				setVisible(false);
			}
		});
		validateCurrentState();
	}
	
	private void validateCurrentState() {
		boolean isConfigValid = mConfigValidator.validate(this.mHost, this.mDBFilePath, this.mPort);
		if (isConfigValid) {
			mConnectButton.setEnabled(true);
		}
	}
	
	private void configOptionPane() {
		ConfigurationPanel configurationPanel = new ConfigurationPanel(mExecutionMode);
		configurationPanel.setPortText(String.valueOf(PORT_DEFAULT_VALUE));
		// Register as an observer
		configurationPanel.getConfigurationObservable().addObserver(this);
		
		ConfigurationStore store;
		
		try {
			store = ConfigurationStore.getInstance();
			
			// PORT
			String port = store.getParameter(ConfigurationStore.PORT);
			if (port != null) {
				configurationPanel.setPortText(port);
				this.mPort = Integer.valueOf(port);
			}
			
			// HOST
			String host = store.getParameter(ConfigurationStore.HOST);
			if (host != null) {
				configurationPanel.setHostText(host);
				this.mHost = host;
			}
			
			// DB_LOCATION
			String dbFilePath = store.getParameter(ConfigurationStore.DB_FILE_PATH);
			if (!new File(dbFilePath).exists()) {
				throw new IllegalArgumentException("Database file not found.");
			}
			configurationPanel.setDbHostText(dbFilePath);
			this.mDBFilePath = host;
			
		} catch (Exception e) {
			mApplicationFrame.alertErrorMessage("Error while restoring configuration.\n" + e.getMessage());
		}
		mOptionPane = new JOptionPane(configurationPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		mConnectButton = new JButton("Connect");
		mConnectButton.setEnabled(false);
		mConnectButton.addActionListener(this);
		mQuitButton = new JButton("Exit");
		mQuitButton.addActionListener(this);
		Object[] buttonsOptions = { mConnectButton, mQuitButton };
		mOptionPane.setOptions(buttonsOptions);
		setContentPane(mOptionPane);
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof ConfigUpdate) {
			ConfigUpdate configUpdate = (ConfigUpdate) arg;
			ConfigUpdatesEnum updateType = configUpdate.getConfigUpdateType();
			
			switch (updateType) {
				case DB_FILE_LOCATION_UPDATE: {
					String dbFilePath = configUpdate.getUpdatedValue();
					boolean isValidate = mConfigValidator.validate(mHost, dbFilePath, mPort);
					if (isValidate) {
						this.mDBFilePath = dbFilePath;
						this.mConnectButton.setEnabled(true);
					} else {
						this.mConnectButton.setEnabled(false);
					}
					break;
				}
				case HOSTNAME_UPDATE: {
					String host = configUpdate.getUpdatedValue();
					boolean isValidate = mConfigValidator.validate(host, mDBFilePath, mPort);
					if (isValidate) {
						this.mHost = host;
						this.mConnectButton.setEnabled(true);
					} else {
						this.mConnectButton.setEnabled(false);
					}
					break;
				}
				case PORT_UPDATE: {
					Integer port = Integer.parseInt(configUpdate.getUpdatedValue());
					boolean isValidate = mConfigValidator.validate(mHost, mDBFilePath, port);
					if (isValidate) {
						this.mPort = port;
						this.mConnectButton.setEnabled(true);
					} else {
						this.mConnectButton.setEnabled(false);
					}
					break;
				}
			}
		}
	}
	
	public void display() {
		pack();
		setLocationRelativeTo(mApplicationFrame);
		setVisible(true);
	}
	
	/** Method will be invoked when connect button is clicked. */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mConnectButton) {
			mOptionPane.setValue(JOptionPane.OK_OPTION);
			ConfigurationStore store;
			try {
				store = ConfigurationStore.getInstance();
				store.setParameter(ConfigurationStore.DB_FILE_PATH, mDBFilePath);
				store.setParameter(ConfigurationStore.HOST, mHost);
				store.setParameter(ConfigurationStore.PORT, String.valueOf(mPort));
			} catch (Exception ex) {
				mApplicationFrame.alertErrorMessage("Could not save the configuration. Reason: \n" + ex.getMessage());
			}
		} else {
			mOptionPane.setValue(JOptionPane.CANCEL_OPTION);
		}
		setVisible(false);
	}
	
	public String getmHost() {
		return mHost;
	}
	
	public void setmHost(String mHost) {
		this.mHost = mHost;
	}
	
	public String getmDBFilePath() {
		return mDBFilePath;
	}
	
	public void setmDBFilePath(String mDBFilePath) {
		this.mDBFilePath = mDBFilePath;
	}
	
	public int getmPort() {
		return mPort;
	}
	
	public void setmPort(int mPort) {
		this.mPort = mPort;
	}
	
	public boolean connectButtonClicked() {
		return mOptionPane != null && mOptionPane.getValue().equals(JOptionPane.OK_OPTION);
	}
	
}
