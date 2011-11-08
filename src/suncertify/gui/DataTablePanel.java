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
 * This class creates the table view. This class holds the <code>JTable.</code>
 * where the list of records been displayed. The inner class named
 * {@link DataModel} is the <code>TableModel</code> for the <code>JTable.</code>
 * 
 * The class implements <code>Observer</code> so that the JTable could be
 * updated with new records every time when there is a change in the
 * <code>Observable</code> {@link ClientModel}.
 * 
 * <code>ClientModel</code> holds the information about the rows and column to
 * be displayed in the <code>JTable</code>
 * 
 * @author nrkkalyan
 * 
 */
public class DataTablePanel extends JPanel implements Observer {
	
	private static final long	serialVersionUID	= 1L;
	private static int			TABLE_WIDTH			= 600;
	private static int			TABLE_HIGHT			= 800;
	// GUI
	private final DataModel		mTableDataModel		= new DataModel();
	private final JTable		mRecordTable		= new JTable(mTableDataModel);
	private ClientModel			mClientModel;
	
	/**
	 * Creates an instance of DataTablePanel.
	 * */
	public DataTablePanel() {
		setLayout(new BorderLayout());
		mRecordTable.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH, TABLE_HIGHT));
		mRecordTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		mRecordTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mRecordTable.setToolTipText("Select the room to book");
		JScrollPane jPanel = new JScrollPane(mRecordTable);
		add(BorderLayout.CENTER, jPanel);
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
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
	 * @param totalUpdate
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
	
	public int getSelectedIndex() {
		return mRecordTable.getSelectedRow();
	}
	
	private class DataModel extends AbstractTableModel {
		
		private static final long	serialVersionUID	= 1L;
		private String[]			mColumnNames		= new String[0];
		private String[][]			mDisplayRows;
		
		private DataModel() {
			if (mClientModel != null) {
				mDisplayRows = mClientModel.getDisplayRows();
			} else {
				mDisplayRows = new String[0][0];
			}
		}
		
		public void refresh() {
			if (mClientModel != null) {
				mDisplayRows = mClientModel.getDisplayRows();
				mColumnNames = mClientModel.getColumns();
			}
		}
		
		@Override
		public int getRowCount() {
			if (mDisplayRows != null) {
				return mDisplayRows.length;
			}
			return 0;
		}
		
		@Override
		public int getColumnCount() {
			if (mColumnNames != null) {
				return mColumnNames.length;
			}
			return 0;
		}
		
		@Override
		public String getColumnName(int col) {
			return mColumnNames[col];
		}
		
		@Override
		public Object getValueAt(int row, int col) {
			if (col == 0) {
				return row + 1;
			}
			if (mDisplayRows != null && mDisplayRows.length >= row && mDisplayRows[row].length >= col) {
				return mDisplayRows[row][col].trim();
			}
			return "";
		}
		
	}
	
}
