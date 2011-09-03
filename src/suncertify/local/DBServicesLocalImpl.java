package suncertify.local;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import suncertify.common.ApplicationException;
import suncertify.common.DBServices;
import suncertify.db.DBExtended;
import suncertify.db.DataBaseImpl;
import suncertify.db.RecordNotFoundException;
import suncertify.db.Room;

/** Local implementation of {@link DBServices} interface. */
public class DBServicesLocalImpl implements DBServicesLocal {
	
	private final DBExtended	mDb;
	
	public DBServicesLocalImpl(String dbFilePath) {
		mDb = new DataBaseImpl(dbFilePath);
	}
	
	@Override
	public void bookRoom(Room room, String customerID) throws RecordNotFoundException, ApplicationException,
			RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<Room> findByCriteria(String[] criteria) throws RecordNotFoundException, RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void saveData() throws IOException {
		// TODO Auto-generated method stub
		
	}
	
}
