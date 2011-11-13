/**
 * 
 */
package suncertify.client;

import java.util.Observable;

/**
 * This class holds the static column names and the data to be displayed in the view
 * 
 * @author nrkkalyan
 * 
 */
public class ClientModel extends Observable {
	
	/** Place holder to keep the data to be displayed
	 * */
	private String[][]		mDisplayRows;
	
	/**Array of column names in the view
	 * */
	private final String[]	mColumnNames;
	
	/**
	 * Creates a data holder with static column names. 
	 * */
	public ClientModel() {
		mColumnNames = new String[] { "Sr No", "Hotel Name", "Location", "Size", "Smoking", "Rate", "Date", "Customer Id" };
		// Calling setChanged signifies the data in this object has been changed.
		setChanged();
	}
	
	/**
	 * @return 2 dimensional array containing the data in [row][column] fashion corresponding to the urlybird database. [row] contains the position to insert.
	 * [column] contains the corresponding data to be displayed   
	 */
	public String[][] getDisplayRows() {
		return mDisplayRows;
	}
	
	
	/**
	 * Sets the data to be displayed and call setChanged method thus mark this object having been changed as the data has been modified.
	 * 
	 * @param displayRows 2 dimensional array containing data as [row][column].   
	 * */
	public void setDisplayRows(String[][] displayRows) {
		this.mDisplayRows = displayRows;
		setChanged();
	}
	
	/**
	 * Returns the column names corresponding to the Urlybird 1.1 database structure.
	 * @return the column names as a string array.
	 */
	public String[] getColumns() {
		return mColumnNames;
	}
	
}
