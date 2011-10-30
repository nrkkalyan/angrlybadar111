/**
 * 
 */
package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import suncertify.client.UrlyBirdClientController;
import suncertify.common.CommonConstants;

/**
 * This class is the main view of the application, mainly the view part of MVC
 * pattern. It displays the menubar of the application and the table with the
 * list of available rooms. This class sends all the user interactions to the
 * {@link UrlyBirdClientController} which is just an ActionListner. Depending on
 * the event triggered the controller takes an appropriate action.
 * 
 * @author Koosie
 * 
 */
public class UrlyBirdClientFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * Panel that contains the menubar.
	 * */
	private final SearchAndBookPanel	mControlPannel;
	private final DataTablePanel	mTablePannel;
	
	public UrlyBirdClientFrame() {
		super(CommonConstants.APPLICATION_NAME);
		mControlPannel = new SearchAndBookPanel();
		mTablePannel = new DataTablePanel();
		this.getContentPane().add(BorderLayout.NORTH, mControlPannel);
		this.getContentPane().add(BorderLayout.CENTER, mTablePannel);
	}
	
	public void setControlPanelActionListener(ActionListener al) {
		mControlPannel.setActionListener(al);
	}
	
	
	public DataTablePanel getTablePanel() {
		return mTablePannel;
	}
	
}
