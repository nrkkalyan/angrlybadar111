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

/**
 * @author Koosie
 * 
 */
public class TablePanel extends JPanel implements Observer {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private static int			TABLE_WIDTH			= 600;
	private static int			TABLE_HIGHT			= 800;
	// GUI
	private final DataModel		mTableDataModel		= new DataModel();
	private final JTable		mRecordTable		= new JTable(mTableDataModel);
	private ClientModel			mClientModel;
	
	public TablePanel() {
		setLayout(new BorderLayout());
		mRecordTable.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH, TABLE_HIGHT));
		mRecordTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		mRecordTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mRecordTable.setToolTipText("Select the room to book");
		JScrollPane jPanel = new JScrollPane(mRecordTable);
		add(BorderLayout.CENTER, jPanel);
		
	}
	
	private class DataModel extends AbstractTableModel {
		
		private final String[]		mColumnNames	= new String[0];
		private final String[][]	mDisplayRows	= new String[0][0];
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}
		
		/**
		 * 
		 */
		public void refresh() {
			// TODO Auto-generated method stub
			
		}
		
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
	
}
