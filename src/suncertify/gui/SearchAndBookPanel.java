/**
 * 
 */
package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import suncertify.client.UrlyBirdClientController;
import suncertify.common.CommonConstants;
import suncertify.common.CommonConstants.ActionCommand;

/**
 * This class extends JPanel to create a top panel in the application. The panel
 * includes 2 input fields thus one can search the rooms by hotel name or
 * location or both. There is also a book button available which can be used to
 * book the selected room.
 * <p>
 * If nothing is provided in the input fields and the search button is pressed
 * then the search will return all available records from the database file.
 * <p>
 * The file menu contains only one option 'Exit' which can be used to exit the
 * application.
 * <p>
 * This class implements <code>ActionListener</code> thus used as a listener for
 * the events triggered in this panel.
 * 
 * @author nrkkalyan
 * 
 */
public class SearchAndBookPanel extends JPanel implements ActionListener {
	
	private static final long			serialVersionUID	= 1L;
	
	private final JMenuBar				mMenuBar			= new JMenuBar();
	private final JTextField			mHotelName			= new JTextField(10);
	private final JTextField			mLocation			= new JTextField(10);
	private final JButton				mSearchBtn			= new JButton("Search / Show All");
	private final JButton				mBookButton			= new JButton("Book");
	
	private UrlyBirdClientController	mUrlyBirdClientController;
	
	/**
	 * Creates a new instance of {@link SearchAndBookPanel}.
	 * */
	public SearchAndBookPanel() {
		this.setLayout(new BorderLayout());
		
		mSearchBtn.setToolTipText("Search rooms by hotel name and/or location");
		mSearchBtn.setActionCommand(ActionCommand.SEARCH_BY_NAME_AND_LOC.name());
		mSearchBtn.addActionListener(this);
		
		mBookButton.setToolTipText("Book the selected room");
		mBookButton.setActionCommand(ActionCommand.BOOK_ROOM.name());
		mBookButton.addActionListener(this);
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		exit.setActionCommand(ActionCommand.EXIT.name());
		JMenu file = new JMenu("File");
		file.add(exit);
		mMenuBar.add(file);
		add(BorderLayout.NORTH, mMenuBar);
		add(BorderLayout.SOUTH, addUIComponents());
		
	}
	
	private Component addUIComponents() {
		
		// HotelName
		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		namePanel.add(new JLabel("Hotel Name"));
		mHotelName.setToolTipText("Enter hotel name");
		namePanel.add(mHotelName);
		
		// Location
		JPanel locationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		locationPanel.add(new JLabel("Location"));
		mLocation.setToolTipText("Enter the location name");
		locationPanel.add(mLocation);
		
		// name and location panel
		JPanel textBoxPanel = new JPanel(new BorderLayout());
		textBoxPanel.add(namePanel, BorderLayout.NORTH);
		textBoxPanel.add(locationPanel, BorderLayout.SOUTH);
		
		// Left panel, Search and Book
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		searchPanel.add(textBoxPanel);
		searchPanel.add(mSearchBtn);
		searchPanel.add(mBookButton);
		
		// Create border
		Border blackBorder = BorderFactory.createLineBorder(Color.black);
		TitledBorder searchBorder = BorderFactory.createTitledBorder(blackBorder, "Search & Book");
		searchBorder.setTitleJustification(TitledBorder.CENTER);
		searchPanel.setBorder(searchBorder);
		
		return searchPanel;
	}
	
	/**
	 * 
	 * If the search button is clicked a new <code>ActionEvent</code> is created
	 * with a custom command
	 * {@link CommonConstants.ActionCommand.SEARCH_BY_NAME_AND_LOC}, hotelName
	 * and location from the respective input fields. The command pattern looks
	 * like
	 * 
	 * <pre>
	 * SEARCH_BY_NAME_AND_LOC:<hotelName>:<location>
	 * </pre>
	 * 
	 * <p>
	 * And finally delegates the call to the {@link UrlyBirdClientController}
	 * .actionPerformed().
	 * 
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == mSearchBtn) {
			String hotelName = mHotelName.getText();
			String location = mLocation.getText();
			String command = CommonConstants.ActionCommand.SEARCH_BY_NAME_AND_LOC.name() + ":" + hotelName + ":" + location;
			ae = new ActionEvent(mSearchBtn, ae.getID(), command);
		}
		mUrlyBirdClientController.actionPerformed(ae);
	}
	
	/**
	 * Sets the private UrlyBirdClientController field thus
	 * {@link UrlyBirdClientController} class will act as the actionListner for this class and
	 * the call can be delegated to
	 * <code>UrlyBirdClientController.actionPerformed()</code> when the events
	 * triggered by search and book buttons of this panel.
	 * 
	 * @param urlyBirdClientController
	 *            the {@link UrlyBirdClientController} instance
	 */
	public void setUrlyBirdClientController(UrlyBirdClientController urlyBirdClientController) {
		this.mUrlyBirdClientController = urlyBirdClientController;
	}
	
}
