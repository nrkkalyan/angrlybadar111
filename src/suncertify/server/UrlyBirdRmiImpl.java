/**
 * 
 */
package suncertify.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import suncertify.UB;
import suncertify.client.UBException;
import suncertify.db.SecurityException;

/**
 * @author Koosie
 * 
 */
public class UrlyBirdRmiImpl extends UnicastRemoteObject implements UB {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private final UrlyBirdImpl	mUrlyBirdImpl;
	
	/**
	 * @param dbFileName
	 */
	public UrlyBirdRmiImpl(String dbFileName) throws RemoteException, UBException, SecurityException {
		mUrlyBirdImpl = new UrlyBirdImpl(dbFileName);
	}
	
	@Override
	public String[][] searchCaterersByHotelNameAndLocation(String hotelName, String location) throws RemoteException, UBException {
		return mUrlyBirdImpl.searchCaterersByHotelNameAndLocation(hotelName, location);
	}
	
	@Override
	public boolean bookRoom(String customerid, String[] data) throws RemoteException, UBException {
		return mUrlyBirdImpl.bookRoom(customerid, data);
	}
	
}
