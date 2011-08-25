package suncertify.db;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import suncertify.common.ApplicationException;
import suncertify.common.ErrorCodes;

public final class DataFileAccess implements Serializable {
	
	private static final long			serialVersionUID		= 1L;
	
	// Static instance for singleton design pattern
	private static DataFileAccess		mDataFileAccess;
	
	// Map to store all records in the database file
	private static Map<Integer, Room>	mRecordMap				= new HashMap<Integer, Room>();
	
	// Lock to access the map with all the records
	private static ReadWriteLock		mReadWriteLock			= new ReentrantReadWriteLock();
	
	// Map of all locked records
	private static Map<Integer, Data>	mLockMap				= new HashMap<Integer, Data>();
	
	// Lock to access the map with all the locked records
	private static Lock					mLock					= new ReentrantLock();
	
	// Condition based on the lock to access the map with all the locked records
	private static Condition			mConditionReleased		= mLock.newCondition();
	
	// Db File Path
	private final String				mDBFilePath;
	
	// Position where a new record should be created
	private final int					mNextAvailablePosition	= 0;
	
	private DataFileAccess(String path) throws ApplicationException {
		if (path == null || !(new File(path).exists())) {
			throw new IllegalArgumentException("Invalid database file path.");
		}
		this.mDBFilePath = path;
		try {
			mReadWriteLock.writeLock().lock();
			DBFileParser parser = new DBFileParser(path);
			parser.parse(mRecordMap);
			mNextAvailablePosition = parser.nextAvailablePosition();
			
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			mReadWriteLock.writeLock().unlock();
		}
	}
	
	/**
	 * Returns the {@link DataFileAccess} instance. It is implemented as a
	 * Singleton Pattern
	 * 
	 * @param dbPath
	 * @return {@link DataFileAccess}
	 * @throws ApplicationException
	 * */
	public static synchronized DataFileAccess getInstance(String dbPath) throws ApplicationException {
		if (mDataFileAccess == null) {
			mDataFileAccess = new DataFileAccess(dbPath);
		}
		return mDataFileAccess;
	}
	
	public int create(String[] data) {
		
		try {
			mReadWriteLock.writeLock().lock();
			int positionToInsert = mNextAvailablePosition;
			Room room = RoomConverter.roomFromStringArray(data, positionToInsert);
			
			mNextAvailablePosition += DBConstants.RECORD_LENGTH;
			mRecordMap.put(positionToInsert, room);
			
			return positionToInsert;
		} finally {
			mReadWriteLock.writeLock().unlock();
		}
	}
	
	public void delete(int recordId) throws RecordNotFoundException {
		try {
			mReadWriteLock.writeLock().lock();
			Room room = mRecordMap.get(recordId);
			if (room != null && room.isValid()) {
				room.setIsValid(false);
			} else {
				throw new RecordNotFoundException(ErrorCodes.RECORD_NOT_FOUND.name());
			}
			
		} finally {
			mReadWriteLock.writeLock().unlock();
		}
	}
	
	public String[] read(int recordId) throws RecordNotFoundException {
		try {
			mReadWriteLock.writeLock().lock();
			
			Room room = mRecordMap.get(recordId);
			if (room != null && room.isValid()) {
				return RoomConverter.stringArrayFromRoom(room);
			} else {
				throw new RecordNotFoundException(ErrorCodes.RECORD_NOT_FOUND.name());
			}
		} finally {
			mReadWriteLock.writeLock().unlock();
		}
	}
	
	public void update(int recordId, String[] data) {
		Room room = RoomConverter.roomFromStringArray(data, recordId);
		update(recordId, room);
	}
	
	private void update(int recordId, Room room) {
		try {
			mReadWriteLock.writeLock().lock();
			mRecordMap.put(recordId, room);
		} finally {
			mReadWriteLock.writeLock().unlock();
		}
	}
	
	public Integer[] find(String[] criteria) {
		Room room = RoomConverter.roomFromStringArray(criteria, null);
		List<Integer> recordList = new ArrayList<Integer>();
		try {
			mReadWriteLock.readLock().lock();
			Collection<Room> rooms = mRecordMap.values();
			for (Room r : rooms) {
				if (r.match(room)) {
					recordList.add(r.getRecordId());
				}
			}
		} finally {
			mReadWriteLock.readLock().unlock();
		}
		return (Integer[]) recordList.toArray();
	}
}
