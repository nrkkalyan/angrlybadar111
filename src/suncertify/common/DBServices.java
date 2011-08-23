package suncertify.common;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import suncertify.db.RecordNotFoundException;
import suncertify.db.Room;

public interface DBServices {
	
	void bookRoom(Room room, String customerID) throws RecordNotFoundException, ApplicationException, RemoteException;
	
	List<Room> findByCriteria(String[] criteria) throws RecordNotFoundException, RemoteException;
	
	void saveData() throws IOException;
}
