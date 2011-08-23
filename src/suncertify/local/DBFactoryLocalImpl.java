package suncertify.local;

import suncertify.common.DBServices;

/**
 * Local implementation of {@link DBFactoryLocal}
 * */

public class DBFactoryLocalImpl implements DBFactoryLocal {
	
	private final String	mDbFilePath;
	
	public DBFactoryLocalImpl(String dbFilePath) {
		mDbFilePath = dbFilePath;
	}
	
	@Override
	public DBServices getDBClient() throws Exception {
		return new DBServicesLocalImpl(mDbFilePath);
	}
	
}
