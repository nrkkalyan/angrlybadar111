/**
 * 
 */
package suncertify.server;

import suncertify.db.RecordNotFoundException;
import suncertify.db.SecurityException;

/**
 * @author Koosie
 * 
 */
public interface UB extends java.rmi.Remote {
	
	String[][] searchByHotelNameAndLocation(String hotelName, String location) throws RecordNotFoundException,SecurityException, Exception;
	
	boolean bookRoom(String customerid, String[] data) throws RecordNotFoundException,SecurityException, Exception;
}
