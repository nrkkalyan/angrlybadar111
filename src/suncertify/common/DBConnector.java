package suncertify.common;

import java.io.IOException;
import java.rmi.RemoteException;

public interface DBConnector {
	
	DBServices getDBClient() throws IOException, RemoteException;
	
}
