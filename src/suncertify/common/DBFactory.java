package suncertify.common;

public interface DBFactory {
	
	public DBServices getDBClient() throws Exception;
	
}
