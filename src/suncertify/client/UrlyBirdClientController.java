/**
 * 
 */
package suncertify.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.Naming;
import java.rmi.Remote;
import java.util.Properties;

import javax.swing.JOptionPane;

import suncertify.UB;
import suncertify.common.CommonConstants;
import suncertify.common.CommonConstants.ActionCommand;
import suncertify.gui.EightDigitsTextField;
import suncertify.gui.PropertiesDialog;
import suncertify.gui.UrlyBirdClientFrame;
import suncertify.server.UrlyBirdImpl;

/**
 * @author Koosie
 * 
 */
public class UrlyBirdClientController implements ActionListener {
	
	private final UrlyBirdClientFrame	mClientFrame;
	private final ClientModel			mClientModel;
	private UB							mUBServer;
	private String						mCurrentHotelName;
	private String						mCurrentLocation;
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
		mLocalFlag = CommonConstants.STANDALONE_MODE_FLAG.equals(clientType);
		mClientFrame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				if (mUBServer != null && mLocalFlag) {
					((UrlyBirdImpl) mUBServer).close();
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
				UB newServer = new UrlyBirdImpl(prop.getProperty(CommonConstants.DB_FILE));
				if (mUBServer != null) {
					((UrlyBirdImpl) mUBServer).close();
				}
				mUBServer = newServer;
			} else {
				String host = prop.getProperty(CommonConstants.SERVER_HOST);
				String port = prop.getProperty(CommonConstants.SERVER_PORT);
				UB newServer = null;
				String name = "rmi://" + host + ":" + port + CommonConstants.REMOTE_SERVER_NAME;
				Remote remoteObj = Naming.lookup(name);
				
				newServer = (UB) remoteObj;
				
				if (mUBServer != null && mLocalFlag) {
					((UrlyBirdImpl) mUBServer).close();
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
		String[] parameters = action.split(":");
		
		ActionCommand command = ActionCommand.getCommandByName(parameters[0]);
		switch (command) {
		
			case CONNECT_LOCAL: {
				new Thread() {
					@Override
					public void run() {
						connectToServer(true);
					}
				}.start();
				break;
			}
			case CONNECT_REMOTE: {
				new Thread() {
					@Override
					public void run() {
						connectToServer(false);
					}
				}.start();
				break;
			}
			case VIEW_ALL: {
				showAllRooms();
				break;
			}
			case SEARCH_BY_NAME_AND_LOC: {
				// Find the parameters
				
				String name = null;
				String loc = null;
				if (parameters.length == 2) {
					name = parameters[1];
				} else if (parameters.length > 2) {
					name = parameters[1];
					loc = parameters[2];
				}
				
				searchByHotelNameAndLocation(name, loc);
				break;
			}
			case BOOK_ROOM: {
				bookRoom();
				break;
			}
			case EXIT: {
				int choice = JOptionPane.showConfirmDialog(mClientFrame, "Do you really want to exit?", CommonConstants.APPLICATION_NAME, JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					if (mUBServer != null && mLocalFlag) {
						((UrlyBirdImpl) mUBServer).close();
					}
					System.exit(0);
				}
				
			}
			
		}
		
	}
	
	private void bookRoom() {
		
		if (mUBServer == null) {
			JOptionPane.showMessageDialog(mClientFrame, "Please connect to the server.", CommonConstants.APPLICATION_NAME, JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		String[] data = null;
		
		int index = mClientFrame.getTablePanel().getSelectedIndex();
		if (index == -1) {
			JOptionPane.showMessageDialog(mClientFrame, "Please select a room first.", CommonConstants.APPLICATION_NAME, JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		data = mClientModel.getDisplayRows()[index];
		
		if (data[7] != null && data[7].trim().length() > 0) {
			JOptionPane.showMessageDialog(mClientFrame, "Room is already booked.", CommonConstants.APPLICATION_NAME, JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		EightDigitsTextField customerIDTextField = new EightDigitsTextField();
		Object[] arrrayMessage = { "Enter customer id(8 Digits)", customerIDTextField };
		
		int value = JOptionPane.showConfirmDialog(mClientFrame, arrrayMessage, CommonConstants.APPLICATION_NAME, JOptionPane.INFORMATION_MESSAGE);
		if (value == 0) {
			if (!customerIDTextField.isEditValid()) {
				JOptionPane.showMessageDialog(mClientFrame, "Invalid Customer id.(8 digits) ", CommonConstants.APPLICATION_NAME, JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				String customerid = customerIDTextField.getText();
				mUBServer.bookRoom(customerid, data);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(mClientFrame, "Unable to book the room." + e.getLocalizedMessage(), CommonConstants.APPLICATION_NAME, JOptionPane.WARNING_MESSAGE);
			}
			searchByHotelNameAndLocation(mCurrentHotelName, mCurrentLocation);
		}
	}
	
	/**
	 * @param pHotelName
	 * @param pLocation
	 */
	private void searchByHotelNameAndLocation(String pHotelName, String pLocation) {
		if (mUBServer == null) {
			JOptionPane.showMessageDialog(mClientFrame, "Please connect to a server first. ", CommonConstants.APPLICATION_NAME, JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		try {
			String[][] data = new String[0][0];
			data = mUBServer.searchByHotelNameAndLocation(pHotelName, pLocation);
			mClientModel.setDisplayRows(data);
			mClientModel.notifyObservers();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mClientFrame, "Exception occured in processing request : " + e.getMessage(), CommonConstants.APPLICATION_NAME, JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	/**
	 * 
	 */
	public void showAllRooms() {
		searchByHotelNameAndLocation(null, null);
	}
	
}
