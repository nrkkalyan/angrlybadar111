/**
 * 
 */
package suncertify;

import java.rmi.RemoteException;

import suncertify.client.UBException;

/**
 * @author Koosie
 * 
 */
public interface UB extends java.rmi.Remote {
	
	String[][] searchCaterersByHotelNameAndLocation(String hotelName, String location) throws RemoteException, UBException;
	
	boolean bookRoom(String customerid, String[] data) throws RemoteException, UBException;
}
