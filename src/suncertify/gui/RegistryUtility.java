package suncertify.gui;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import suncertify.network.DBFactoryRemote;
import suncertify.network.DBFactoryRemoteImpl;

/**
 * Utility class for declaring a remote object to the RMI registry. This is
 * declared as a final class in order to avoid unnecessary inheritance.
 * */
public final class RegistryUtility {
	
	public static final String	RMI_LOOKUP_NAME	= "URLY_BIRD_111_ACCESS_OBJECT";
	
	/**
	 * Private constructor prevents from creating any object of the utility
	 * class.
	 * */
	private RegistryUtility() {
	}
	
	public static void register(String dbPath, int port) throws RemoteException {
		DBFactoryRemote dbRemoteFactory = new DBFactoryRemoteImpl(dbPath);
		DBFactoryRemote server = (DBFactoryRemote) UnicastRemoteObject.exportObject(dbRemoteFactory, 0);
		Registry registry = LocateRegistry.createRegistry(port);
		registry.rebind(RMI_LOOKUP_NAME, server);
		
	}
}
