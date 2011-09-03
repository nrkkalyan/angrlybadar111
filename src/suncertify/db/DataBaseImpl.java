/**
 * <b>Copyright (c) 2010 Ericsson AB, Sweden. All rights reserved.</b>
 * <p>
 * The Copyright to the computer program(s) herein is the property of Ericsson AB, Sweden. The program(s) may be used
 * and/or copied with the written permission from Ericsson AB or in accordance with the terms and conditions stipulated
 * in the agreement/contract under which the program(s) have been supplied.
 * </p>
 * 
 */
package suncertify.db;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import suncertify.common.ApplicationException;

/**
 * @author Kalyan
 * 
 */
public class DataBaseImpl implements DBExtended, Serializable {
	
	private final int						offset;
	private final int						nooffields;
	private final String[]					fieldnames;
	private final HashMap<String, Short>	fieldmap;
	private int								recordlength;
	private final RandomAccessFile			ras;
	
	/**
	 * 
	 */
	private static final long				serialVersionUID	= 1L;
	
	public DataBaseImpl(String dbFileName, String magicCokie) throws IOException {
		
	}
	
	@Override
	public String[] read(int recNo) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void update(int recNo, String[] data, long lockCookie) throws RecordNotFoundException, SecurityException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void delete(int recNo, long lockCookie) throws RecordNotFoundException, SecurityException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int[] find(String[] criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int create(String[] data) throws DuplicateKeyException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public long lock(int recNo) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void unlock(int recNo, long cookie) throws RecordNotFoundException, SecurityException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<Room> search(String[] criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void bookRoom(Room room, String customerId) throws RecordNotFoundException, ApplicationException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void saveRecords() throws IOException {
		// TODO Auto-generated method stub
		
	}
	
}
