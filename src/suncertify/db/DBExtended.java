package suncertify.db;

import java.io.IOException;
import java.util.List;

import suncertify.common.ApplicationException;

/**
 * This interface encourages to use {@link Room} object. This interface extends
 * {@link DB} which was provided by this assignment in order to be compliant
 * with the requirements.
 */
public interface DBExtended extends DB {
	
	/**
	 * Returns a list of records that matches the specified criteria. The field
	 * n in the database file is described to criteria[n]. A null value can
	 * match to any field.
	 * */
	
	public List<Room> search(String[] criteria);
	
	public void bookRoom(Room room, String customerId) throws RecordNotFoundException, ApplicationException;
	
	public void saveRecords() throws IOException;
	
}
