/**
 * 
 */
package suncertify.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import suncertify.common.CommonConstants;
import suncertify.common.CommonConstants.ApplicationMode;

/**
 * @author Koosie
 * 
 */

public class PropertiesDialog extends JDialog implements ActionListener {
	
	/**
	 * 
	 */
	private static final long		serialVersionUID		= 1L;
	protected int					mStatus					= -1;
	public static final int			OK						= 0;
	public static final int			CANCEL					= -1;
	private Properties				mProperties;
	private final JButton			mOkButton				= new JButton("OK");
	private final JButton			mCancelButton			= new JButton("Cancel");
	private final JButton			mResetButton			= new JButton("Reset");
	private final JButton			mBrowseButton			= new JButton("Browse");
	private final JTextField		mFileNameSelectionText	= new JTextField("db-1x1.db");
	private final JTextField		mServerHostText			= new JTextField("localhost");
	private final JTextField		mServerPortText			= new JTextField("1099");
	
	private final ApplicationMode	mApplicationMode;
	
	public PropertiesDialog(JFrame parentFrame, final ApplicationMode applicationMode) {
		super(parentFrame);
		mApplicationMode = applicationMode;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(370, 240);
		mOkButton.addActionListener(this);
		mCancelButton.addActionListener(this);
		mResetButton.addActionListener(this);
		mBrowseButton.addActionListener(this);
		initGui();
		setTitle(CommonConstants.APPLICATION_NAME + "Application Settings");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				PropertiesDialog.this.mStatus = CANCEL;
				synchronized (PropertiesDialog.this) {
					PropertiesDialog.this.notifyAll();
				}
			}
		});
		
	}
	
	/**
	 * @param mLocalFlag
	 * @param mIsRmiServer
	 * @param mIsNetworkClient
	 */
	private void initGuiComponents() {
		boolean standAloneFlag = false;
		boolean networkClientFlag = false;
		boolean serverFlag = false;
		switch (mApplicationMode) {
			case SERVER: {
				serverFlag = true;
				mFileNameSelectionText.setText(mProperties.getProperty(CommonConstants.DB_FILE, "db-1x1.db"));
				mServerPortText.setText(mProperties.getProperty(CommonConstants.SERVER_PORT, "1099"));
				mServerHostText.setText("");
				break;
			}
			case ALONE: {
				standAloneFlag = true;
				mServerHostText.setText("");
				mServerPortText.setText("");
				mFileNameSelectionText.setText(mProperties.getProperty(CommonConstants.DB_FILE, "db-1x1.db"));
				
				break;
			}
			case NETWORK_CLIENT: {
				networkClientFlag = true;
				mFileNameSelectionText.setText("");
				mServerHostText.setText(mProperties.getProperty(CommonConstants.SERVER_HOST, "localhost"));
				mServerPortText.setText(mProperties.getProperty(CommonConstants.SERVER_PORT, "1099"));
				break;
			}
			
			default: {
				throw new UnsupportedOperationException("Unknow application mode");
			}
			
		}
		
		mFileNameSelectionText.setEnabled(standAloneFlag || serverFlag);
		mBrowseButton.setEnabled(standAloneFlag || serverFlag);
		mServerPortText.setEnabled(networkClientFlag || serverFlag);
		mServerHostText.setEnabled(networkClientFlag);
	}
	
	/**
	 * 
	 */
	private void initGui() {
		setSize(370, 240);
		getContentPane().setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = null;
		
		gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.fill = java.awt.GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.insets = new java.awt.Insets(5, 5, 0, 0);
		
		String text = "<html>Please specify connection properties for " + mApplicationMode.getDescription() + ".<br><br></html>";
		
		this.getContentPane().add(new JLabel(text), gbc);
		
		int pos = 2;
		gbc.gridy = pos++;
		gbc.gridwidth = 1;
		gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
		
		gbc.weightx = 0.0;
		this.getContentPane().add(new JLabel("Database File"), gbc);
		gbc.weightx = 1.0;
		this.getContentPane().add(mFileNameSelectionText, gbc);
		gbc.weightx = 0.0;
		this.getContentPane().add(mBrowseButton, gbc);
		
		// if (!mIsRmiServer) {
		gbc.gridy = pos++;
		this.getContentPane().add(new JLabel("Server Host"), gbc);
		this.getContentPane().add(mServerHostText, gbc);
		// }
		// if (mIsRmiServer || !mLocalFlag) {
		gbc.gridy = pos++;
		this.getContentPane().add(new JLabel("Server port"), gbc);
		this.getContentPane().add(mServerPortText, gbc);
		// }
		JPanel temppanel = new JPanel();
		GridLayout gl = new GridLayout(1, 3);
		gl.setHgap(20);
		temppanel.setLayout(gl);
		temppanel.add(mOkButton);
		temppanel.add(mResetButton);
		temppanel.add(mCancelButton);
		
		gbc.gridy = pos++;
		gbc.gridwidth = 3;
		gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gbc.insets = new java.awt.Insets(5, 25, 15, 25);
		this.getContentPane().add(temppanel, gbc);
		
	}
	
	private int showDialog() {
		initGuiComponents();
		setLocationRelativeTo(null);
		setVisible(true);
		synchronized (this) {
			try {
				this.wait();
			} catch (Exception e) {
				// Ignore
				System.err.println(e.getMessage());
			}
		}
		setVisible(false);
		return mStatus;
	}
	
	/**
	 * Reads a property file and loads the properties object. It then calls
	 * showDialog() to display the GUI. Values in this properties object are
	 * used as default values in the input fields. If the user clicks OK, the
	 * new values are stored back into the file. Instead of using showDialog()
	 * directly, this method can be used to have the additional functionality of
	 * loading the properties from a file and saving them back in the same file.
	 * 
	 * @throws Exception
	 */
	public Properties loadProperties() throws Exception {
		mProperties = new Properties();
		FileInputStream fis = new FileInputStream(CommonConstants.CONFIGURATION_FILE);
		mProperties.load(fis);
		fis.close();
		int status = showDialog();
		if (status == OK) {
			FileOutputStream fos = new FileOutputStream(CommonConstants.CONFIGURATION_FILE);
			mProperties.store(fos, "Urlybird 1.1 Application Settings.");
			fos.close();
		} else {
			System.exit(0);
		}
		return mProperties;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object src = ae.getSource();
		
		if (src == mOkButton) {
			switch (mApplicationMode) {
				case SERVER: {
					mProperties.setProperty(CommonConstants.DB_FILE, mFileNameSelectionText.getText());
					mProperties.setProperty(CommonConstants.SERVER_PORT, mServerPortText.getText());
					break;
				}
				case ALONE: {
					mProperties.setProperty(CommonConstants.DB_FILE, mFileNameSelectionText.getText());
					break;
				}
				case NETWORK_CLIENT: {
					mProperties.setProperty(CommonConstants.SERVER_HOST, mServerHostText.getText());
					mProperties.setProperty(CommonConstants.SERVER_PORT, mServerPortText.getText());
					break;
				}
			}
			synchronized (this) {
				this.mStatus = OK;
				this.notifyAll();
			}
		} else if (src == mCancelButton) {
			synchronized (this) {
				this.mStatus = CANCEL;
				this.notifyAll();
			}
		} else if (src == mResetButton) {
			mFileNameSelectionText.setText("");
			mServerHostText.setText("");
			mServerPortText.setText("");
		} else if (src == mBrowseButton) {
			final JFileChooser fileChooser = new JFileChooser(".");
			fileChooser.setFileFilter(new DBFileFilter());
			fileChooser.setDialogTitle("Database directory locator");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnValue = fileChooser.showOpenDialog(this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				try {
					mFileNameSelectionText.setText(fileChooser.getSelectedFile().getCanonicalPath());
				} catch (IOException e) {
					mFileNameSelectionText.setText(fileChooser.getSelectedFile().getName());
				}
			}
		}
		
	}
	
	private class DBFileFilter extends FileFilter {
		
		@Override
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			}
			if (f.getName().toLowerCase().endsWith(".db")) {
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		public String getDescription() {
			return "UrlyBird DB(*.db)";
		}
		
	}
	
}
