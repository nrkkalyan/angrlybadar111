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
 * <code>UrlyBirdImpl</code> class implements all the business methods required
 * by the application client. The networked server also calls the methods of
 * this class to perform the desired operations.
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
	 *            Database file path
	 * 
	 * @throws SecurityException
	 *             if the database file is invalid
	 * @throws IOException
	 *             if unable to create an instance of {@link Data}
	 * */
	public UrlyBirdImpl(String dbFileName) throws SecurityException, IOException {
		db = new Data(dbFileName);
	}
	
	/**
	 * Search the database and return all the records that exactly match the
	 * given hotelName and location. If hotelName and location both are null or
	 * empty then all records will be returned.
	 * 
	 * The lock is acquired over the database then using DB.find(String[]) which
	 * returns the record numbers matching the criteria and using DB.read(int)
	 * String[][] is constructed and returned finally the database is unlocked.
	 * 
	 * @param hotelName
	 *            Hotel name to be searched
	 * @param location
	 *            Hotel location to be searched
	 * @return string[][] matching the given criteria
	 * 
	 * @throws RecordNotFoundException
	 *             if any record is not found for a record number
	 * @throws SecurityException
	 *             if unable to unlock the record
	 * 
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
	
	/**
	 * Attach the customerId to the selected record and thus mark the record as
	 * booked with the specified customerId. This method will throw
	 * <code>SecurityException</code> in case the data of the selected record is
	 * modified by any other user during the booking process.
	 * 
	 * The booking process involves few steps,
	 * <p>
	 * Step 1: Using DB.lock(int) try to acquire the lock on the selected record
	 * till the record is available for locking.
	 * <p>
	 * Step 2: Once the lock is acquired read the data[] using DB.read(int)
	 * <p>
	 * Step 3: Verify if the 7th field in the data[] is null or empty if not
	 * throw <code>SecurityException</code>, otherwise proceed step 4
	 * <p>
	 * Step 4: Verify if the data is modified by any other user during the above
	 * steps, this can be done by comparing the data in the originalData and the
	 * data[], if yes then <code>SecurityException</code> is thrown, otherwise
	 * proceed step 5.
	 * <p>
	 * Step 5: After that 6th index of data[] is set to customerId and database
	 * file is updated.
	 * <p>
	 * Step 6: Finally unlock the record.
	 * 
	 * @param customerId
	 *            customer id to be booked for
	 * @param originalData
	 *            String[] representing the original data as shown on the client
	 * 
	 * @throws RecordNotFoundException
	 *             if the selected record is not found or deleted during the
	 *             booking process
	 * @throws SecurityException
	 *             if unable to book the room
	 *             <p>
	 *             if the data is modified during the booking process
	 *             <p>
	 *             if unable to unlock the record
	 * */
	@Override
	public void bookRoom(String customerId, String[] originalData) throws RecordNotFoundException, SecurityException {
		
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
				
				data[6] = customerId;
				db.update(recordNo, data, lockkey);
			} else {
				throw new SecurityException("This room is already booked.");
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
	
	/**
	 * Closes the database and waits till all the clients are done, and clears
	 * all the locks acquired.
	 */
	public void close() {
		if (db != null) {
			((Data) db).close();
			db = null;
		}
	}
	
}
