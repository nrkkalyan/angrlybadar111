/**
 * 
 */
package suncertify;

import java.rmi.RemoteException;

import suncertify.db.RecordNotFoundException;

/**
 * @author Koosie
 * 
 */
public interface UBServer extends java.rmi.Remote {
	
	String[][] searchCaterersByHotelNameAndLocation(String hotelName, String location) throws RemoteException, RecordNotFoundException;
	
	boolean bookRoom(String customerid, String[] originalData) throws RemoteException, SecurityException;
}
