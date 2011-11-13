/**
 * 
 */
package suncertify.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import suncertify.client.UrlyBirdClientController;
import suncertify.common.CommonConstants;

/**
 * This class makes the main user interface of the client application. The class
 * has the {@link SearchAndBookPanel} and {@link DataTablePanel}.
 * 
 * {@link SearchAndBookPanel} is used to search and book the rooms. And
 * {@link DataTablePanel} displays the total available records as a result of
 * search.
 * 
 * @author nrkkalyan
 */
public class UrlyBirdClientFrame extends JFrame {
	
	private static final long			serialVersionUID	= 1L;
	private final SearchAndBookPanel	mSearchAndBookPanel;
	private final DataTablePanel		mDataTablePanel;
	
	/** Creates an instance of {@link UrlyBirdClientFrame}. */
	public UrlyBirdClientFrame() {
		super(CommonConstants.APPLICATION_NAME);
		mSearchAndBookPanel = new SearchAndBookPanel();
		mDataTablePanel = new DataTablePanel();
		this.getContentPane().add(BorderLayout.NORTH, mSearchAndBookPanel);
		this.getContentPane().add(BorderLayout.CENTER, mDataTablePanel);
	}
	
	/**
	 * Set the SearchAndBookPanel's <code>UrlyBirdClientController</code>
	 * 
	 * @param urlyBirdClientController
	 *            UrlyBirdClientController instance
	 * */
	public void setControlPanelActionListener(UrlyBirdClientController urlyBirdClientController) {
		mSearchAndBookPanel.setUrlyBirdClientController(urlyBirdClientController);
	}
	
	/**
	 * Returns the DataTablePanel
	 * */
	public DataTablePanel getDataTablePanel() {
		return mDataTablePanel;
	}
	
}
