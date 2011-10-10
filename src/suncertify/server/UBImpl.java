/**
 * 
 */
package suncertify.server;

import java.io.IOException;
import java.rmi.RemoteException;

import suncertify.UB;
import suncertify.client.UBException;
import suncertify.db.DB;
import suncertify.db.Data;
import suncertify.db.RecordNotFoundException;
import suncertify.db.SecurityException;

/**
 * @author Koosie
 * 
 */
public class UBImpl implements UB {
	private DB	db;
	
	/**
	 * @param dbfilename
	 */
	public UBImpl(String dbfilename) throws RemoteException, UBException, SecurityException {
		try {
			db = new Data(dbfilename);
		} catch (IOException e) {
			throw new UBException("Unable to connect to the database. : " + e.getMessage());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * suncertify.UBServer#searchCaterersByHotelNameAndLocation(java.lang.String
	 * , java.lang.String)
	 */
	@Override
	public String[][] searchCaterersByHotelNameAndLocation(String hotelName, String location) throws RemoteException, UBException {
		if (db == null) {
			throw new UBException("Communication error to database. Details : Database connection is not available");
		}
		
		String[] criteria = new String[] { hotelName, location, null, null, null, null, null };
		
		String[][] retval = null;
		Long lockkey = null;
		try {
			lockkey = db.lock(-1);
			int[] ia = db.find(criteria);
			retval = new String[ia.length][];
			for (int i = 0; i < ia.length; i++) {
				String[] data = db.read(ia[i]);
				retval[i] = new String[data.length + 1];
				retval[i][0] = "" + ia[i];
				for (int j = 0; j < data.length; j++) {
					retval[i][j + 1] = data[j].trim();
				}
			}
			db.unlock(-1, lockkey);
		} catch (Exception e) {
			throw new UBException(e.getLocalizedMessage());
			
		} finally {
			try {
				if (lockkey != null)
					db.unlock(-1, lockkey);
			} catch (Exception e) {
				e.printStackTrace();
				// Ignore
			}
		}
		
		return retval;
	}
	
	@Override
	public boolean bookRoom(String customerid, String[] originalData) throws RemoteException, UBException {
		if (db == null) {
			throw new UBException("Communication error to database. Details : Database connection is not available");
		}
		boolean status = false;
		Long lockkey = null;
		Integer recordNo = null;
		try {
			recordNo = Integer.parseInt(originalData[0]);
			lockkey = db.lock(recordNo);
			String[] data = db.read(recordNo);
			if (data[6] == null || data[6].trim().length() == 0) {
				boolean datachanged = false;
				for (int n = 1; n < originalData.length; n++) {
					if (!originalData[n].trim().equals(data[n - 1].trim())) {
						datachanged = true;
						break;
					}
				}
				
				if (datachanged) {
					throw new UBException("The caterer data was updated. Please refresh your view and try again.");
				}
				
				data[6] = customerid;
				db.update(recordNo, data, lockkey);
				status = true;
			} else if (data[6].trim().equals(customerid)) {
				
			} else {
				throw new UBException("This room is already booked.");
			}
			return status;
		} catch (RecordNotFoundException e) {
			throw new UBException("Unable to book the room because it does not exist.");
		} catch (SecurityException e) {
			throw new UBException("Unable to book the room. Details :" + e.getLocalizedMessage());
		} finally {
			try {
				if (lockkey != null && recordNo != null) {
					db.unlock(recordNo, lockkey);
				}
			} catch (Exception e) {
				e.printStackTrace();
				// Ignore
			}
		}
	}
	
	public void close() {
		((Data) db).close();
		db = null;
	}
	
}
