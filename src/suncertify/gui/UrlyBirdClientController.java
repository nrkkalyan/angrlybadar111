/**
 * 
 */
package suncertify.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.Naming;
import java.rmi.Remote;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * @author Koosie
 * 
 */
public class UrlyBirdClientController implements ActionListener {
	
	private final UrlyBirdClientFrame	mClientFrame;
	private final ClientModel			mClientModel;
	private UBServer					mUBServer;
	private String						mCurrentHotelName;
	private String						mCurrentLocation;
	private String						mCurrentQuery;
	private boolean						mLocalFlag	= false;
	private final PropertiesDialog		mUBClientPropertiesDialog;
	
	/**
	 * @param frame
	 * @param clientType
	 */
	public UrlyBirdClientController(UrlyBirdClientFrame frame, String clientType) {
		mClientFrame = frame;
		mClientModel = new ClientModel();
		mClientFrame.setCPActionListener(this);
		mClientFrame.setModel(mClientModel);
		mClientModel.notifyObservers(true);
		mUBClientPropertiesDialog = new PropertiesDialog(mClientFrame, false);
		mLocalFlag = GuiConstants.STANDALONE_MODE_FLAG.equals(clientType);
		mClientFrame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				if (mUBServer != null && mLocalFlag) {
					((UBServerImpl) mUBServer).close();
				}
				System.exit(0); // Normal Exit
			}
		});
		
		connectToServer(mLocalFlag);
		
	}
	
	/**
	 * @param localFlag
	 */
	private void connectToServer(boolean pLocalFlag) {
		try {
			mUBClientPropertiesDialog.setLocalFlag(pLocalFlag);
			Properties prop = mUBClientPropertiesDialog.loadProperties("suncertify.properties");
			if (prop == null) {
				return; // Should never occur.
			}
			
			if (pLocalFlag) {
				UBServer newServer = new UBServerImpl(prop.getProperty("dbfile"));
				if (mUBServer != null) {
					((UBServerImpl) mUBServer).close();
				}
				mUBServer = newServer;
			} else {
				String host = prop.getProperty("serverhost");
				String port = prop.getProperty("serverport");
				UBServer newServer = null;
				String name = "rmi://" + host + ":" + port + "/RemoteUBServer";
				Remote remoteObj = Naming.lookup(name);
				
				newServer = (UBServer) remoteObj;
				
				if (mUBServer != null && mLocalFlag) {
					((UBServerImpl) mUBServer).close();
				}
				mUBServer = newServer;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Failed to connect to the remote server. Detials" + e.getLocalizedMessage(), "UB Message", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String action = e.getActionCommand();
		
	}
	
	/**
	 * 
	 */
	public void doShowAllRooms() {
		
	}
	
}
