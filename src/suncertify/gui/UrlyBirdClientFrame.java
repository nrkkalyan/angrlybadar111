/**
 * 
 */
package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import suncertify.common.CommonConstants;

/**
 
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
