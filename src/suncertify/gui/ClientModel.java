/**
 * 
 */
package suncertify.gui;

import java.util.Observable;

/**
 * @author Koosie
 * 
 */
public class ClientModel extends Observable {
	
	private String[][]		mDisplayRows;
	private final String[]	mColumnNames;
	
	public ClientModel() {
		mColumnNames = new String[] { "Sr No", "Hotel Name", "Location", "Size", "Smoking", "Rate", "Date", "Customer Id" };
		setChanged();
	}
	
	/**
	 * @return
	 */
	public String[][] getDisplayRows() {
		return mDisplayRows;
	}
	
	public void setDisplayRows(String[][] displayRows) {
		this.mDisplayRows = displayRows;
		setChanged();
	}
	
	/**
	 * @return
	 */
	public String[] getColumns() {
		return mColumnNames;
	}
	
}
