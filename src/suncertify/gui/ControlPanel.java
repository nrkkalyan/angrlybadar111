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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import suncertify.common.CommonConstants;
import suncertify.common.CommonConstants.ActionCommand;

/**
 * @author Koosie
 * 
 */
public class ControlPanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private final JMenuBar		mMenuBar			= new JMenuBar();
	private final JTextField	mHotelName			= new JTextField(10);
	private final JTextField	mLocation			= new JTextField(10);
	private final JButton		mSearchBtn			= new JButton("Search / Show All");
	private final JButton		mBookButton			= new JButton("Book");
	
	private ActionListener		mActionListner;
	
	public ControlPanel() {
		super();
		this.setLayout(new BorderLayout());
		
		mSearchBtn.setToolTipText("Search rooms by hotel name and/or location");
		mSearchBtn.setActionCommand(createCommand(ActionCommand.SEARCH_BY_NAME_AND_LOC));
		mSearchBtn.addActionListener(this);
		
		mBookButton.setToolTipText("Book the selected room");
		mBookButton.setActionCommand(createCommand(ActionCommand.BOOK_ROOM));
		mBookButton.addActionListener(this);
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		exit.setActionCommand(createCommand(ActionCommand.EXIT));
		JMenu file = new JMenu("File");
		file.add(exit);
		mMenuBar.add(file);
		this.add(BorderLayout.NORTH, mMenuBar);
		this.add(BorderLayout.SOUTH, addOtherUIComponents());
		
	}
	
	/**
	 * @return
	 */
	private Component addOtherUIComponents() {
		
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == mSearchBtn) {
			String hotelName = mHotelName.getText();
			String location = mLocation.getText();
			ae = new ActionEvent(mSearchBtn, ae.getID(), createCommand(CommonConstants.ActionCommand.SEARCH_BY_NAME_AND_LOC, hotelName, location));
		}
		mActionListner.actionPerformed(ae);
	}
	
	private String createCommand(ActionCommand command) {
		return createCommand(command, null, null);
	}
	
	private String createCommand(ActionCommand command, String hotelName, String location) {
		if (hotelName != null || location != null) {
			return command.name() + ":" + hotelName + ":" + location;
		}
		return command.name();
	}
	
	/**
	 * @param actionListner
	 *            the actionListner to set
	 */
	public void setCPActionListener(ActionListener actionListner) {
		this.mActionListner = actionListner;
	}
	
	public static void main(String[] args) {
		ControlPanel ccp = new ControlPanel();
		JFrame fr = new JFrame();
		fr.setSize(500, 300);
		fr.getContentPane().add(BorderLayout.NORTH, ccp);
		fr.setVisible(true);
	}
	
}
