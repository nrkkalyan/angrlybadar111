/**
 * 
 */
package suncertify.server;

import java.rmi.RemoteException;

import suncertify.UBServer;
import suncertify.db.RecordNotFoundException;

/**
 * @author Koosie
 * 
 */
public class UBServerImpl implements UBServer {
	
	/**
	 * @param pProperty
	 */
	public UBServerImpl(String pProperty) {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * suncertify.UBServer#searchCaterersByHotelNameAndLocation(java.lang.String
	 * , java.lang.String)
	 */
	@Override
	public String[][] searchCaterersByHotelNameAndLocation(String pHotelName, String pLocation) throws RemoteException, RecordNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see suncertify.UBServer#bookRoom(java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean bookRoom(String pCustomerid, String[] pOriginalData) throws RemoteException, SecurityException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void close() {
		
	}
	
}
