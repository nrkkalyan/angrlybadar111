/**
 * 
 */
package suncertify.server;

import java.io.IOException;

import suncertify.db.DB;
import suncertify.db.Data;
import suncertify.db.RecordNotFoundException;
import suncertify.db.SecurityException;

/**
 * 
 * This class implements all the business methods required by the application
 * client. The networked server also calls the methods of this class to perform
 * the desired operations.
 * 
 * @author nrkkalyan
 * 
 */
public class UrlyBirdImpl implements UB {
	private DB	db;
	
	/**
	 * Constructs an instance of {@link UrlyBirdImpl}.
	 * 
	 * @param dbFileName
	 *            database file path
	 * 
	 * @throws SecurityException
	 *             if the database file is invalid
	 * @throws IOException
	 *             if unable to read the database file
	 * */
	public UrlyBirdImpl(String dbFileName) throws SecurityException, IOException {
		db = new Data(dbFileName);
	}
	
	/**
	 * Search 
	 * */
 	@Override
	public String[][] searchByHotelNameAndLocation(String hotelName, String location) throws RecordNotFoundException, SecurityException {
		
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
		} finally {
			try {
				if (lockkey != null)
					db.unlock(-1, lockkey);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				// Ignore
			}
		}
		
		return retval;
	}
	
	@Override
	public void bookRoom(String customerid, String[] originalData) throws RecordNotFoundException, SecurityException {

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
					throw new SecurityException("The record is modified, please try again.");
				}
				
				data[6] = customerid;
				db.update(recordNo, data, lockkey);
			} else {
				throw new RecordNotFoundException("This room is already booked.");
			}
		} finally {
			try {
				if (lockkey != null && recordNo != null) {
					db.unlock(recordNo, lockkey);
				}
			} catch (Exception e) {
				// Ignore
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void close() {
		((Data) db).close();
		db = null;
	}
	
}
