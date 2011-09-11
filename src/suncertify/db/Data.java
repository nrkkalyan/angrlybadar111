package suncertify.db;

/***
 * @author Koosie
 * 
 */
public class Data implements DB {
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see suncertify.db.DB#read(int)
	 */
	@Override
	public String[] read(int recNo) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see suncertify.db.DB#update(int, java.lang.String[], long)
	 */
	@Override
	public void update(int recNo, String[] data, long lockCookie) throws RecordNotFoundException, SecurityException {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see suncertify.db.DB#delete(int, long)
	 */
	@Override
	public void delete(int recNo, long lockCookie) throws RecordNotFoundException, SecurityException {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see suncertify.db.DB#find(java.lang.String[])
	 */
	@Override
	public int[] find(String[] criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see suncertify.db.DB#create(java.lang.String[])
	 */
	@Override
	public int create(String[] data) throws DuplicateKeyException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see suncertify.db.DB#lock(int)
	 */
	@Override
	public long lock(int recNo) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see suncertify.db.DB#unlock(int, long)
	 */
	@Override
	public void unlock(int recNo, long cookie) throws RecordNotFoundException, SecurityException {
		// TODO Auto-generated method stub
		
	}
	
}
