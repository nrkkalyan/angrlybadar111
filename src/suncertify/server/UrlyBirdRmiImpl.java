/**
 * 
 */
package suncertify.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import suncertify.db.RecordNotFoundException;
import suncertify.db.SecurityException;

/**
 * The <code>UrlyBirdRmiImpl</code> class is the RMI over JRMP implementation of
 * {@link UB} interface. This class extends <code>UnicastRemoteObject</code> in
 * order to export this class as remote object over JRMP and thus the network
 * clients can obtain a stub to communicate to this remote object.
 * 
 * This class does implement all the business methods exposed in <code>UB</code>
 * interface, but does not implement any logic, instead uses
 * {@link UrlyBirdImpl} implementation.
 * 
 * Since both <code>UrlyBirdRmiImpl</code> and <code>UrlyBirdImpl</code>
 * implements {@link UB} the actual code can be shared between the two
 * implementing classes.
 * 
 * @author nrkkalyan
 * 
 */
public class UrlyBirdRmiImpl extends UnicastRemoteObject implements UB {
	
	private static final long	serialVersionUID	= 1L;
	
	private final UrlyBirdImpl	mUrlyBirdImpl;
	
	/**
	 * Creates an instance of <code>UrlyBirdRmiImpl</code>.
	 * 
	 * @param dbFileName
	 *            database file path.
	 * @throws IOException
	 *             if UrlyBirdImpl(String) could not able to read the database
	 *             file.
	 * @throws SecurityException
	 *             if unable to create <code>UrlyBirdImpl</code> instance.
	 * 
	 */
	public UrlyBirdRmiImpl(String dbFileName) throws SecurityException, IOException {
		mUrlyBirdImpl = new UrlyBirdImpl(dbFileName);
	}
	
	/**
	 * Search the database to exactly match the records for the given hotelName
	 * or location or both.
	 * <p>
	 * If both the hotelName and location is empty then this method will return
	 * all available records from the database.
	 * 
	 * @param hotelName
	 *            hotel name to be searched
	 * @param location
	 *            location name to be searched
	 * 
	 * @return String[][] of record matching for the hotelName and location
	 * 
	 * @throws RecordNotFoundException
	 *             if a record is not found or deleted for a record number.
	 * @throws SecurityException
	 *             if the unable to unlock the record.
	 * @throws RemoteException
	 *             if unable to invoke the method
	 * 
	 * @see suncertify.server.UrlyBirdImpl#searchByHotelNameAndLocation(java.lang.String,
	 *      java.lang.String)
	 * 
	 * */
	@Override
	public String[][] searchByHotelNameAndLocation(String hotelName, String location) throws RecordNotFoundException, SecurityException, RemoteException {
		return mUrlyBirdImpl.searchByHotelNameAndLocation(hotelName, location);
	}
	
	/**
	 * Updates the database by setting the 6th index of the selected record with
	 * the given customerId, and thus marking the record as booked and making
	 * the record not available for booking.
	 * 
	 * @param customerid
	 *            8 digits customer id
	 * @param data
	 *            selected record array
	 * 
	 * @throws RecordNotFoundException
	 *             if the record is not available or deleted for a given record
	 *             number
	 * @throws SecurityException
	 *             if unable to book the room
	 *             <p>
	 *             if unable to unlock the record.
	 *             <p>
	 *             if the data is modified during the booking process
	 * @throws RemoteException
	 *             if unable to invoke the method
	 * 
	 * @see suncertify.server.UrlyBirdImpl#bookRoom(java.lang.String,
	 *      java.lang.String[])
	 */
	@Override
	public void bookRoom(String customerid, String[] data) throws RecordNotFoundException, SecurityException, RemoteException {
		mUrlyBirdImpl.bookRoom(customerid, data);
	}
	
	/**
	 * Closes the database
	 * */
	public void close() {
		mUrlyBirdImpl.close();
	}
	
}
