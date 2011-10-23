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
import suncertify.common.CommonConstants.ApplicationMode;
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
	private final PropertiesDialog		mUBPropertiesDialog;
	
	/**
	 * @param frame
	 * @param clientType
	 */
	public UrlyBirdClientController(UrlyBirdClientFrame frame, ApplicationMode clientType) throws Exception {
		mClientFrame = frame;
		mClientModel = new ClientModel();
		mClientFrame.setCPActionListener(this);
		mClientFrame.setModel(mClientModel);
		mClientModel.notifyObservers(true);
		mLocalFlag = ApplicationMode.ALONE == clientType;
		mUBPropertiesDialog = new PropertiesDialog(mClientFrame, clientType);
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
	private void connectToServer(boolean localFlag) throws Exception {
		try {
			// mUBPropertiesDialog.setLocalFlag(localFlag);
			Properties prop = mUBPropertiesDialog.loadProperties();
			
			if (localFlag) {
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
			String message = "Failed to connect. Application will exit. Reason: " + e.getLocalizedMessage();
			JOptionPane.showMessageDialog(null, message, "UB Message", JOptionPane.ERROR_MESSAGE);
			throw new UBException(message);
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
				break;
			}
			default: {
				throw new UnsupportedOperationException("Could not perform the following operation " + command);
			}
			
		}
		
	}
	
	private void bookRoom() {
		
		if (mUBServer == null) {
			JOptionPane.showMessageDialog(mClientFrame, "Please connect to the server.", CommonConstants.APPLICATION_NAME, JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		int index = mClientFrame.getTablePanel().getSelectedIndex();
		if (index == -1) {
			JOptionPane.showMessageDialog(mClientFrame, "Please select a room first.", CommonConstants.APPLICATION_NAME, JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		String[] data = mClientModel.getDisplayRows()[index];
		
		if (data[7] != null && data[7].trim().length() > 0) {
			JOptionPane.showMessageDialog(mClientFrame, "Room is already booked.", CommonConstants.APPLICATION_NAME, JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		String value = null;
		do {
			value = JOptionPane.showInputDialog("Enter customer id(8 Digits only)");
		} while (!isCustomerIdValid(value));
		
		try {
			if (value != null) {
				mUBServer.bookRoom(value, data);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mClientFrame, "Unable to book the room." + e.getLocalizedMessage(), CommonConstants.APPLICATION_NAME, JOptionPane.WARNING_MESSAGE);
		}
		searchByHotelNameAndLocation(mCurrentHotelName, mCurrentLocation);
	}
	
	/**
	 * @param customerId
	 * @return
	 */
	private boolean isCustomerIdValid(String customerId) {
		if (customerId == null) {
			return true;
		}
		boolean result = customerId.matches("[0-9]{8}");
		if (!result) {
			JOptionPane.showMessageDialog(mClientFrame, "Customer id must be 8 digits only.", CommonConstants.APPLICATION_NAME, JOptionPane.ERROR_MESSAGE);
		}
		return result;
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
