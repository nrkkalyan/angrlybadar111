package suncertify.local;

import java.io.IOException;
import java.rmi.RemoteException;

import suncertify.common.DBConnector;
import suncertify.common.DBServices;

/**
 * Local implementation of DBConnector.
 * */
public class DBConnectorLocal implements DBConnector {
	
	private final String	mDbFile;
	
	/**
	 * Constructs the DBCOnnectorLocal object using the give database File
	 */
	public DBConnectorLocal(String dbFileName) {
		mDbFile = dbFileName;
	}
	
	@Override
	public DBServices getDBClient() throws IOException, RemoteException {
		// TODO Auto-generated method stub
		return new DBFactoryLocalImpl(mDbFile).getClient();
	}
	
}
