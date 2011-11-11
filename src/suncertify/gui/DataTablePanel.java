/**
 * 
 */
package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import suncertify.client.ClientModel;

/**
 * This class holds the <code>JTable</code> where the list of records been
 * displayed. The inner class named {@link DataModel} is the
 * <code>TableModel</code> for the <code>JTable.</code>
 * 
 * The class also implements <code>Observer</code> so that the JTable could be
 * updated with new records every time when there is a change in the
 * <code>Observable</code> class {@link ClientModel}.
 * 
 * <code>ClientModel</code> holds the information about the rows and column to
 * be displayed in the <code>JTable</code>
 * 
 * @author nrkkalyan
 * 
 */
public class DataTablePanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private static int TABLE_WIDTH = 600;
	private static int TABLE_HIGHT = 800;
	// GUI
	private final DataModel mTableDataModel = new DataModel();
	private final JTable mRecordTable = new JTable(mTableDataModel);
	private ClientModel mClientModel;

	/**
	 * Creates an instance of DataTablePanel.
	 * */
	public DataTablePanel() {
		setLayout(new BorderLayout());
		mRecordTable.setPreferredScrollableViewportSize(new Dimension(
				TABLE_WIDTH, TABLE_HIGHT));
		mRecordTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		mRecordTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mRecordTable.setToolTipText("Select the room to book");
		JScrollPane jPanel = new JScrollPane(mRecordTable);
		add(BorderLayout.CENTER, jPanel);

	}

	/**
	 * This method is defined in from <code>Observer</code> interface. This
	 * method is called when the observable object is changed.
	 * 
	 * @param model
	 *            observable class
	 * @param totalUpdate
	 *            parameter passed to notifyObservers method of <code>Observable</code> class.
	 * */
	@Override
	public void update(Observable model, Object totalUpdate) {
		mClientModel = (ClientModel) model;
		if (totalUpdate instanceof Boolean) {
			updateTableView((Boolean) totalUpdate);
		} else {
			updateTableView(false);
		}

	}

	/**
	 * Calls the <code>JTable.fireTableDataChanged()</code>.
	 */
	private void updateTableView(Boolean totalUpdate) {
		mTableDataModel.refresh();
		if (totalUpdate) {
			mTableDataModel.fireTableStructureChanged();
			revalidate();
			mRecordTable.revalidate();
			mRecordTable.repaint();
		} else {
			mTableDataModel.fireTableDataChanged();
		}
	}

	/**
	 * This method returns the selected row index from the <code>JTable<code>.
	 * 
	 * @return the selected row index
	 * */
	public int getSelectedIndex() {
		return mRecordTable.getSelectedRow();
	}

	/**
	 * Private inner class to hold the data for <code>JTable</code>. This class
	 * extends <code>AbstractTableModel</code>.
	 * */
	private class DataModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private String[] mColumnNames = new String[0];
		private String[][] mDisplayRows;

		/** Creates an instance of DataModel. */
		private DataModel() {
			if (mClientModel != null) {
				mDisplayRows = mClientModel.getDisplayRows();
			} else {
				mDisplayRows = new String[0][0];
			}
		}

		/**
		 * Retrieves columns and rows from {@link ClientModel} to be displayed
		 * in the <code>JTable</code>.
		 */
		public void refresh() {
			if (mClientModel != null) {
				mDisplayRows = mClientModel.getDisplayRows();
				mColumnNames = mClientModel.getColumns();
			}
		}

		/** Returns the number of rows. 
		 * @return the number of rows
		 * */
		@Override
		public int getRowCount() {
			if (mDisplayRows != null) {
				return mDisplayRows.length;
			}
			return 0;
		}

		/**
		 * Returns the number of columns.
		 * 
		 * @return the number of columns.*/
		@Override
		public int getColumnCount() {
			if (mColumnNames != null) {
				return mColumnNames.length;
			}
			return 0;
		}

		/**
		 * Returns the column name at column index.
		 * 
		 * @param col
		 *            column index
		 * @return String value defined at the column index.
		 * */
		@Override
		public String getColumnName(int col) {
			return mColumnNames[col];
		}

		/**
		 * Returns the value stored at the given row, col index.
		 * 
		 *  @param row row index
		 *  @param col column index
		 *  
		 *  @return value stored at the row and column index.
		 * */
		@Override
		public Object getValueAt(int row, int col) {
			if (col == 0) {
				return row + 1;
			}
			if (mDisplayRows != null && mDisplayRows.length >= row
					&& mDisplayRows[row].length >= col) {
				return mDisplayRows[row][col].trim();
			}
			return "";
		}

	}

}
