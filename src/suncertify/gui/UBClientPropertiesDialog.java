/**
 * 
 */
package suncertify.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

/**
 * @author Koosie
 * 
 */
public class UBClientPropertiesDialog extends PropertiesDialog implements ActionListener {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID		= 1L;
	
	private final JButton		mOkButton				= new JButton("OK");
	private final JButton		mCancelButton			= new JButton("Cancel");
	private final JButton		mResetButton			= new JButton("Reset");
	private final JButton		mBrowseButton			= new JButton("Browse");
	private final JTextField	mFileNameSelectionText	= new JTextField("db-1x1.db");
	private final JTextField	mServerHostText			= new JTextField("localhost");
	private final JTextField	mServerPortText			= new JTextField("1099");
	private boolean				mLocalFlag;
	
	/**
	 * @param pParentFrame
	 */
	public UBClientPropertiesDialog(JFrame pParentFrame) {
		super(pParentFrame);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(370, 240);
		mOkButton.addActionListener(this);
		mCancelButton.addActionListener(this);
		mResetButton.addActionListener(this);
		mBrowseButton.addActionListener(this);
		initGui();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see suncertify.gui.PropertiesDialog#initGuiComponents()
	 */
	@Override
	public void initGuiComponents() {
		
		mServerHostText.setEnabled(!mLocalFlag);
		mServerPortText.setEnabled(!mLocalFlag);
		mFileNameSelectionText.setEnabled(mLocalFlag);
		mBrowseButton.setEnabled(mLocalFlag);
		
		if (!mLocalFlag) {
			mServerHostText.setText(getProperties().getProperty("urlybird.servername", "localhost"));
			mServerPortText.setText(getProperties().getProperty("urlybird.serverport", "1099"));
			mFileNameSelectionText.setText("");
		} else {
			mServerHostText.setText("");
			mServerPortText.setText("");
			mFileNameSelectionText.setText(getProperties().getProperty("urlybird.dbfile", "db-1x1.db"));
		}
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
		
		String text = "Please specify or confirm the following server properties.";
		
		this.getContentPane().add(new JLabel(text), gbc);
		
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
		
		gbc.weightx = 0.0;
		this.getContentPane().add(new JLabel("Database File"), gbc);
		gbc.weightx = 1.0;
		this.getContentPane().add(mFileNameSelectionText, gbc);
		gbc.weightx = 0.0;
		this.getContentPane().add(mBrowseButton, gbc);
		
		gbc.gridy = 3;
		this.getContentPane().add(new JLabel("Server Host"), gbc);
		this.getContentPane().add(mServerHostText, gbc);
		
		gbc.gridy = 4;
		this.getContentPane().add(new JLabel("Server port"), gbc);
		this.getContentPane().add(mServerPortText, gbc);
		
		JPanel temppanel = new JPanel();
		GridLayout gl = new GridLayout(1, 3);
		gl.setHgap(20);
		temppanel.setLayout(gl);
		temppanel.add(mOkButton);
		temppanel.add(mResetButton);
		temppanel.add(mCancelButton);
		
		gbc.gridy = 5;
		gbc.gridwidth = 3;
		gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gbc.insets = new java.awt.Insets(5, 25, 15, 25);
		this.getContentPane().add(temppanel, gbc);
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object src = ae.getSource();
		if (src == mOkButton) {
			if (mLocalFlag) {
				getProperties().setProperty("client.localdbfile", mFileNameSelectionText.getText());
			} else {
				getProperties().setProperty("client.serverhost", mServerHostText.getText());
				getProperties().setProperty("client.serverport", mServerPortText.getText());
			}
			synchronized (this) {
				this.mStatus = OK;
				this.notifyAll();
			}
		} else if (src == mCancelButton) {
			System.exit(0);
		} else if (src == mResetButton) {
			mFileNameSelectionText.setText("");
			mServerHostText.setText("");
			mServerPortText.setText("");
		} else if (src == mBrowseButton) {
			final JFileChooser fileChooser = new JFileChooser(".");
			fileChooser.setFileFilter(new DBFileFilter());
			fileChooser.setDialogTitle("Video directory locator");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
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
	
	/**
	 * @param localFlag
	 *            the localFlag to set
	 */
	public void setLocalFlag(boolean localFlag) {
		this.mLocalFlag = localFlag;
	}
	
	/**
	 * @return the localFlag
	 */
	public boolean isLocalFlag() {
		return mLocalFlag;
	}
	
	@Deprecated
	public static void main(String[] args) {
		Properties props = new Properties();
		UBClientPropertiesDialog pd = new UBClientPropertiesDialog(null);
		pd.setLocalFlag(true);
		pd.setProperties(props);
		pd.showDialog();
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
