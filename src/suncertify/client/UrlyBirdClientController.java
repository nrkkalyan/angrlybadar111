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

import suncertify.UBServer;
import suncertify.common.GuiConstants;
import suncertify.common.GuiConstants.ActionCommand;
import suncertify.gui.EightDigitsTextField;
import suncertify.gui.PropertiesDialog;
import suncertify.gui.UrlyBirdClientFrame;
import suncertify.server.UBServerImpl;

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
		int i1 = action.indexOf(":");
		String parameterString = action.substring(i1 + 1);
		String[] parameters = parameterString.split(",");
		
		ActionCommand command = ActionCommand.getCommandByName(action);
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
				if (parameters.length == 1) {
					name = parameters[0];
				} else if (parameters.length > 1) {
					name = parameters[0];
					loc = parameters[1];
				}
				
				searchByHotelNameAndLocation(name, loc);
				break;
			}
			case BOOK_ROOM: {
				bookRoom();
				break;
			}
			case EXIT: {
				int choice = JOptionPane.showConfirmDialog(mClientFrame, "Do you really want to exit?", GuiConstants.APPLICATION_NAME, JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					if (mUBServer != null && mLocalFlag) {
						((UBServerImpl) mUBServer).close();
					}
					System.exit(0);
				}
				
			}
			
		}
		
	}
	
	private void bookRoom() {
		
		if (mUBServer == null) {
			JOptionPane.showMessageDialog(mClientFrame, "Please connect to the server.", GuiConstants.APPLICATION_NAME, JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		String[] data = null;
		
		int index = mClientFrame.getTablePanel().getSelectedIndex();
		if (index == -1) {
			JOptionPane.showMessageDialog(mClientFrame, "Please select a room first.", GuiConstants.APPLICATION_NAME, JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		data = mClientModel.getDisplayRows()[index];
		
		if (data[7] != null && data[7].trim().length() > 0) {
			JOptionPane.showMessageDialog(mClientFrame, "Room is already booked.", GuiConstants.APPLICATION_NAME, JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		EightDigitsTextField customerIDTextField = new EightDigitsTextField();
		Object[] arrrayMessage = { "Enter customer id(8 Digits)", customerIDTextField };
		
		int value = JOptionPane.showConfirmDialog(mClientFrame, arrrayMessage, GuiConstants.APPLICATION_NAME, JOptionPane.INFORMATION_MESSAGE);
		if (value == 0) {
			if (!customerIDTextField.isEditValid()) {
				JOptionPane.showMessageDialog(mClientFrame, "Invalid Customer id.(8 digits) ", GuiConstants.APPLICATION_NAME, JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				String customerid = customerIDTextField.getText();
				boolean status = mUBServer.bookRoom(customerid, data);
				if (status) {
					searchByHotelNameAndLocation(mCurrentHotelName, mCurrentLocation);
				} else {
					JOptionPane.showMessageDialog(mClientFrame, "Unable to book the room.", GuiConstants.APPLICATION_NAME, JOptionPane.WARNING_MESSAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(mClientFrame, "Unable to book the room." + e.getLocalizedMessage(), GuiConstants.APPLICATION_NAME, JOptionPane.WARNING_MESSAGE);
				searchByHotelNameAndLocation(mCurrentHotelName, mCurrentLocation);
			}
		}
	}
	
	/**
	 * @param pHotelName
	 * @param pLocation
	 */
	private void searchByHotelNameAndLocation(String pHotelName, String pLocation) {
		if (mUBServer == null) {
			JOptionPane.showMessageDialog(mClientFrame, "Please connect to a server first. ", GuiConstants.APPLICATION_NAME, JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		try {
			String[][] data = new String[0][0];
			data = mUBServer.searchCaterersByHotelNameAndLocation(pHotelName, pLocation);
			mClientModel.setDisplayRows(data);
			mClientModel.notifyObservers();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mClientFrame, "Exception occured in processing request : " + e.getMessage(), GuiConstants.APPLICATION_NAME, JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	/**
	 * 
	 */
	public void showAllRooms() {
		searchByHotelNameAndLocation(null, null);
	}
	
}
