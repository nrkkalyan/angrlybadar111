package suncertify.common;

public interface DBFactory {
	
	public DBServices getDbClient() throws Exception;
	
}
