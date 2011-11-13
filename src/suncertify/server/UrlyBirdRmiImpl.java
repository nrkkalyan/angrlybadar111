/**
 * 
 */
package suncertify.server;

import java.io.IOException;
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
	 * @throws IOException 
	 */
	public UrlyBirdRmiImpl(String dbFileName) throws SecurityException, IOException {
		mUrlyBirdImpl = new UrlyBirdImpl(dbFileName);
	}
	
	@Override
	public String[][] searchByHotelNameAndLocation(String hotelName, String location) throws RecordNotFoundException,SecurityException{
		return mUrlyBirdImpl.searchByHotelNameAndLocation(hotelName, location);
	}
	
	@Override
	public void bookRoom(String customerid, String[] data) throws RecordNotFoundException,SecurityException{
		mUrlyBirdImpl.bookRoom(customerid, data);
	}

	public void close() {
		mUrlyBirdImpl.close();
	}
	
}
