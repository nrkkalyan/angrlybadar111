/**
 * 
 */
package suncertify.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

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
	private final TablePannel	mTablePannel;
	
	public UrlyBirdClientFrame() {
		super("UrlyBird 1.1.1 Application");
		mControlPannel = new ControlPanel();
		mTablePannel = new TablePannel();
		this.getContentPane().add(BorderLayout.NORTH, mControlPannel);
		this.getContentPane().add(BorderLayout.CENTER, mTablePannel);
	}
	
}
