/**
 * 
 */
package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import suncertify.client.ClientModel;
import suncertify.client.UrlyBirdClientController;

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
	private final ControlPanel	mControlPannel;
	private final TablePanel	mTablePannel;
	
	public UrlyBirdClientFrame() {
		super("UrlyBird 1.1.1 Application");
		mControlPannel = new ControlPanel();
		mTablePannel = new TablePanel();
		this.getContentPane().add(BorderLayout.NORTH, mControlPannel);
		this.getContentPane().add(BorderLayout.CENTER, mTablePannel);
	}
	
	public void setCPActionListener(ActionListener al) {
		mControlPannel.setCPActionListener(al);
	}
	
	public void setModel(ClientModel cm) {
		cm.addObserver(mTablePannel);
	}
	
	public TablePanel getTablePanel() {
		return mTablePannel;
	}
	
}
