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

import suncertify.common.CommonConstants;
import suncertify.common.CommonConstants.ActionCommand;
import suncertify.common.CommonConstants.ApplicationMode;
import suncertify.gui.PropertiesDialog;
import suncertify.gui.SearchAndBookPanel;
import suncertify.gui.UrlyBirdClientFrame;
import suncertify.server.UB;
import suncertify.server.UrlyBirdImpl;

/**
 * <code>UrlyBirdClientController</code> is the controller of the application. All user actions are handled in this class.
 * Depending on the {@link ApplicationMode} the appropriate implementation of {@link UB} is chosen.
 * 
 * @author nrkkalyan
 * 
 */
public class UrlyBirdClientController implements ActionListener {
	
	private String						mCurrentHotelName;
	private String						mCurrentLocation;
	
	private final UrlyBirdClientFrame	mClientFrame;
	private final ClientModel			mClientModel;
	private final UB					mUBImpl;
	private final ApplicationMode 		mApplicationMode;
	private final PropertiesDialog		mUBPropertiesDialog;
	
	/**
	 * Constructs <code>UrlyBirdClientController</code> with the {@link UrlyBirdClientFrame} and {@link ApplicationMode}.
	 *  
	 * @param ubClientFrame
	 * @param applicationMode
	 */
	public UrlyBirdClientController(UrlyBirdClientFrame ubClientFrame, ApplicationMode applicationMode) throws Exception {
		mClientFrame = ubClientFrame;
		mClientModel = new ClientModel();
		mClientModel.addObserver(mClientFrame.getTablePanel());
		mClientModel.notifyObservers(true);
		mApplicationMode = applicationMode;
		mUBPropertiesDialog = new PropertiesDialog(mClientFrame, applicationMode);
		mClientFrame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				closeUBImpl();
			}
		});
		
		mUBImpl = connectToServer();
		showAllRooms();
		
	}
	
	
	
	/* Display the startup screen to set the database properties.
	 * Depending on the applicationMode the implementation of UB interface is chosen.
	 */
	private UB connectToServer() throws Exception {
		UB ubImpl = null;
		try {
			Properties prop = mUBPropertiesDialog.loadProperties();
			
			if (mApplicationMode ==  ApplicationMode.ALONE) {
				ubImpl = new UrlyBirdImpl(prop.getProperty(CommonConstants.DB_FILE));
			} else if(mApplicationMode ==  ApplicationMode.NETWORK_CLIENT) {
				String host = prop.getProperty(CommonConstants.SERVER_HOST);
				String port = prop.getProperty(CommonConstants.SERVER_PORT);
				
				String name = "rmi://" + host + ":" + port + CommonConstants.REMOTE_SERVER_NAME;
				Remote remoteObj = Naming.lookup(name);
				ubImpl = (UB) remoteObj;
			} else{
				throw new UnsupportedOperationException("Application mode not supported. Mode: " +mApplicationMode);
			}
			return ubImpl;
		} catch (Exception e) {
			String message = "Failed to connect. Application will exit. Reason: " + e.getLocalizedMessage();
			JOptionPane.showMessageDialog(null, message, "UB Message", JOptionPane.ERROR_MESSAGE);
			throw new UBException(message);
		}
	}
	
	/**
	 * Overrides java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent) to handle events generated in {@link SearchAndBookPanel} 
	 * The command string is the string value of one of the following commands described in {@link ActionCommand}
	 * @param  actionEvent
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String action = actionEvent.getActionCommand();
		String[] parameters = action.split(":");
		
		ActionCommand command = ActionCommand.getCommandByName(parameters[0]);
		switch (command) {
		
			case SEARCH_BY_NAME_AND_LOC: {
				
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
					closeUBImpl();
					System.exit(0);
				}
				break;
			}
			default: {
				throw new UnsupportedOperationException("Could not perform the following operation " + command);
			}
			
		}
		
	}

	/* Close the database when the application exit.
	 * */
	private void closeUBImpl() {
		if (mUBImpl != null && mApplicationMode ==  ApplicationMode.ALONE) {
			((UrlyBirdImpl) mUBImpl).close();
		}
	}
	
	/* Book the selected room if available.
	 */
	private void bookRoom() {
		
		if (mUBImpl == null) {
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
				mUBImpl.bookRoom(value, data);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mClientFrame, "Unable to book the room." + e.getLocalizedMessage(), CommonConstants.APPLICATION_NAME, JOptionPane.WARNING_MESSAGE);
		}
		searchByHotelNameAndLocation(mCurrentHotelName, mCurrentLocation);
	}
	
	/*
	 * Check if the entered customerId is valid
	 * 
	 * @param customerId
	 * @return true if the customerId is 8 digits else return false.
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
	
	/*
	 * Search the database for specific hotelName and location.
	 * 
	 * @param hotelName
	 * @param location
	 */
	private void searchByHotelNameAndLocation(String hotelName, String location) {
		if (mUBImpl == null) {
			JOptionPane.showMessageDialog(mClientFrame, "Please connect to a server first. ", CommonConstants.APPLICATION_NAME, JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		try {
			String[][] data = new String[0][0];
			data = mUBImpl.searchByHotelNameAndLocation(hotelName, location);
			mClientModel.setDisplayRows(data);
			mClientModel.notifyObservers();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mClientFrame, "Exception occured in processing request : " + e.getMessage(), CommonConstants.APPLICATION_NAME, JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	/*
	 * Show all rooms in the database.
	 */
	private void showAllRooms() {
		searchByHotelNameAndLocation(null, null);
	}
}
