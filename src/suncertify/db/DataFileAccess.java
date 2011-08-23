package suncertify.db;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import suncertify.common.ApplicationException;

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
	
	public DataFileAccess(String path) {
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
}
