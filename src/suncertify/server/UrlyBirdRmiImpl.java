/**
 * 
 */
package suncertify.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import suncertify.db.RecordNotFoundException;
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
	public UrlyBirdRmiImpl(String dbFileName) throws RemoteException, Exception, SecurityException {
		mUrlyBirdImpl = new UrlyBirdImpl(dbFileName);
	}
	
	@Override
	public String[][] searchByHotelNameAndLocation(String hotelName, String location) throws RecordNotFoundException,SecurityException, Exception{
		return mUrlyBirdImpl.searchByHotelNameAndLocation(hotelName, location);
	}
	
	@Override
	public boolean bookRoom(String customerid, String[] data) throws RecordNotFoundException,SecurityException, Exception {
		return mUrlyBirdImpl.bookRoom(customerid, data);
	}

	public void close() {
		mUrlyBirdImpl.close();
	}
	
}
