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
public class UBRmiImpl extends UnicastRemoteObject implements UB {
	
	/**
	 * @param property
	 */
	public UBRmiImpl(String property) throws RemoteException, UBException, SecurityException {
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
	public String[][] searchCaterersByHotelNameAndLocation(String hotelName, String location) throws RemoteException, UBException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see suncertify.UBServer#bookRoom(java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean bookRoom(String customerid, String[] originalData) throws RemoteException, UBException {
		// TODO Auto-generated method stub
		return false;
	}
	
}
