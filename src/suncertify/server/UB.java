/**
 * 
 */
package suncertify.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import suncertify.db.RecordNotFoundException;
import suncertify.db.SecurityException;

/**
 * This interface holds all the business logic that is required by the
 * application i.e is providing the search and book functions.
 * 
 * This interface is implemented by both networked and non-networked application
 * classes.
 * 
 * The interface also extends <code>java.rmi.Remote</code>. The benefit of this
 * approach is we can have a common interface for both network and non-network
 * mode servers. can be used as a
 * 
 * @author nrkkalyan
 * 
 */
public interface UB extends Remote {
	
	/**
	 * Search the available records for the given hotelName and location.
	 * 
	 * @param hotelName
	 *            hotel name to be searched
	 * @param location
	 *            location name to be searched
	 * @return String[][] of available rooms
	 * 
	 * @throws RecordNotFoundException
	 *             if a record is not found for a given record number.
	 * @throws SecurityException
	 *             if could not able to read the database file
	 * @throws RemoteException
	 *             if unable to invoke the method in network mode
	 */
	String[][] searchByHotelNameAndLocation(String hotelName, String location) throws RecordNotFoundException, SecurityException, RemoteException;
	
	/**
	 * Updates the selected room with the given customerId and thus the room is
	 * considered as booked.
	 * 
	 * @param customerid
	 *            8 digit customer id
	 * @param data
	 *            array of the selected record
	 * 
	 * @throws RecordNotFoundException
	 *             if the record is not found for a given record number or the
	 *             room is not available to book.
	 * @throws SecurityException
	 *             if the record is modified or could not able to attain the
	 *             lock on the selected record.
	 * @throws RemoteException
	 *             if unable to invoke the method in network mode
	 */
	void bookRoom(String customerid, String[] data) throws RecordNotFoundException, SecurityException, RemoteException;
	
}
