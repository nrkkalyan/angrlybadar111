package suncertify.db;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;

/***
 * @author Koosie
 * 
 */
public class Data implements DB {
	
	private int								offset;
	private final String[]					fieldnames;
	private final HashMap<String, Short>	fieldmap;
	private int								recordlength;
	private final RandomAccessFile			ras;
	private static final String				CHARSET					= "US-ASCII";
	/** The bytes that store the "magic cookie" value */
	private static final int				MAGIC_COOKIE_BYTES		= 4;
	/** The bytes that store the total overall length of each record */
	private static final int				RECORD_LENGTH_BYTES		= 4;
	/** The bytes that store the number of fields in each record */
	private static final int				NUMBER_OF_FIELDS_BYTES	= 2;
	/** The bytes that store the length of each field name */
	private static final int				FIELD_NAME_BYTES		= 2;
	/** The bytes that store the fields length */
	private static final int				FIELD_LENGTH_BYTES		= 2;
	private final static byte				DELETEDROW_BYTE1		= 0X1;
	private final static byte				VALIDROW_BYTE1			= 0X0;
	
	/**
	 * This hashmap holds the lock information for records.
	 */
	private final LockManager				locker					= new LockManager();
	
	public Data(String dbfilename) throws IOException, SecurityException {
		FileInputStream fis = new FileInputStream(dbfilename);
		DataInputStream dis = new DataInputStream(fis);
		int magicCookie = dis.readInt();
		if (magicCookie != DBConstants.MAGIC_COOKIE_REFERENCE) {
			throw new SecurityException("Mismatch magic cookie in specified database file");
		}
		offset += MAGIC_COOKIE_BYTES + RECORD_LENGTH_BYTES + NUMBER_OF_FIELDS_BYTES;
		recordlength = dis.readInt();
		int nooffields = dis.readShort();
		fieldnames = new String[nooffields];
		fieldmap = new HashMap<String, Short>();
		
		for (int i = 0; i < nooffields; i++) {
			final int fieldsLength = dis.readShort();
			offset += FIELD_NAME_BYTES + FIELD_LENGTH_BYTES + fieldsLength;
			final byte[] fieldNameByteArray = new byte[fieldsLength];
			dis.read(fieldNameByteArray);
			fieldnames[i] = new String(fieldNameByteArray, CHARSET);
			short fl = dis.readShort();
			fieldmap.put(fieldnames[i], new Short(fl));
		}
		recordlength = recordlength + 1;// 1 byte for deleted flag.
		dis.close();
		fis.close();
		
		ras = new RandomAccessFile(dbfilename, "rw");
		ras.seek(offset);
		
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see suncertify.db.DB#read(int)
	 */
	@Override
	public synchronized String[] read(int recNo) throws RecordNotFoundException {
		if (recNo < 0) {
			throw new RecordNotFoundException("No record found for : " + recNo);
		}
		
		try {
			ras.seek(offset + recNo * recordlength);
			byte[] ba = new byte[recordlength];
			int noofbytesread = ras.read(ba);
			if (noofbytesread < recordlength) {
				throw new RecordNotFoundException("No such record found or insufficient data : " + recNo);
			}
			if (ba[0] == DELETEDROW_BYTE1) {
				throw new RecordNotFoundException("Record has been deleted : " + recNo);
			}
			return parseRecord(new String(ba, CHARSET));
		} catch (IOException e) {
			throw new RecordNotFoundException("Unable to retrieve the record : " + recNo + " : " + e.getMessage());
		}
		
	}
	
	/**
	 * @param recorddata
	 * @return
	 */
	private String[] parseRecord(String recorddata) {
		String[] returnValue = new String[fieldnames.length];
		int startind = 1;// first 1 bytes are for status flag so ignore them.
		
		for (int i = 0; i < fieldnames.length; i++) {
			int fieldlength = (fieldmap.get(fieldnames[i])).intValue();
			returnValue[i] = recorddata.substring(startind, startind + fieldlength);
			startind = startind + fieldlength;
		}
		return returnValue;
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see suncertify.db.DB#update(int, java.lang.String[], long)
	 */
	@Override
	public synchronized void update(int recNo, String[] data, long lockCookie) throws RecordNotFoundException, SecurityException {
		
		if (recNo < 0) {
			throw new RecordNotFoundException("No such record : " + recNo);
		}
		
		if (data == null || data.length != fieldnames.length) {
			throw new SecurityException("Invalid Data");
		}
		
		if (lockCookie == -1) {
			throw new SecurityException("Invalid lock key");
		}
		
		Long realkey = locker.getOwner(recNo);
		if (realkey == null) {
			throw new SecurityException("You have to lock the record first before updating it.");
		}
		
		if (realkey.equals(lockCookie)) {
			try {
				ras.seek(offset + recNo * recordlength);
				byte[] ba = new byte[recordlength];
				int noofbytesread = ras.read(ba);
				if (noofbytesread < recordlength) {
					throw new RecordNotFoundException("No such record : " + recNo);
				}
				if (ba[0] == DELETEDROW_BYTE1) {
					throw new RecordNotFoundException("This record has been deleted : " + recNo);
				}
				ras.seek(offset + recNo * recordlength);
				ras.writeByte(VALIDROW_BYTE1);
				ras.write(getByteArray(data));
			} catch (Exception e) {
				throw new SecurityException("Unable to update the record : " + recNo + " : " + e.getMessage());
			}
		} else {
			throw new SecurityException("You do not own the lock for this record.");
		}
		
	}
	
	/**
	 * @param pData
	 * @return
	 * @throws IOException
	 */
	private byte[] getByteArray(String[] data) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		for (int i = 0; i < fieldnames.length; i++) {
			
			String field = data[i];
			short flength = (fieldmap.get(fieldnames[i])).shortValue();
			byte[] ca = new byte[flength];
			int j = 0;
			for (byte b : field.getBytes()) {
				ca[j++] = b;
			}
			for (int x = 0; x < flength; x++) {
				dos.write(ca[x]);
			}
		}
		dos.flush();
		dos.close();
		byte[] ba = baos.toByteArray();
		return ba;
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see suncertify.db.DB#delete(int, long)
	 */
	@Override
	public synchronized void delete(int recNo, long lockCookie) throws RecordNotFoundException, SecurityException {
		
		if (recNo < 0) {
			throw new RecordNotFoundException("No such record : " + recNo);
		}
		
		if (lockCookie == -1) {
			throw new SecurityException("Invalid lock key");
		}
		
		Long realkey = locker.getOwner(recNo);
		if (realkey == null) {
			throw new SecurityException("You have to lock the record first before updating it.");
		}
		
		if (realkey.equals(lockCookie)) {
			try {
				ras.seek(offset + recNo * recordlength);
				byte[] ba = new byte[recordlength];
				int noofbytesread = ras.read(ba);
				if (noofbytesread < recordlength) {
					throw new RecordNotFoundException("No such record : " + recNo);
				}
				if (ba[0] == DELETEDROW_BYTE1) {
					throw new RecordNotFoundException("This record has already been deleted : " + recNo);
				}
				ras.seek(offset + recNo * recordlength);
				ras.writeByte(DELETEDROW_BYTE1);
				
			} catch (IOException e) {
				throw new SecurityException("Unable to delete the record : " + recNo + " : " + e.getMessage());
			}
		}
		
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see suncertify.db.DB#find(java.lang.String[])
	 */
	@Override
	public synchronized int[] find(String[] criteria) {
		ArrayList<Integer> matchingIndices = new ArrayList<Integer>();
		if (criteria == null || criteria.length != fieldnames.length) {
			return new int[0];
		}
		try {
			ras.seek(offset);
			byte[] ba = new byte[recordlength];
			int recno = 0;
			while (ras.read(ba) == recordlength) {
				String rec = new String(ba, CHARSET);
				if (ba[0] == DELETEDROW_BYTE1) {
					recno++;
					continue;
				}
				final String[] fielddata = parseRecord(rec);
				boolean match = true;
				for (int i = 0; i < fieldnames.length; i++) {
					if (criteria[i] == null) {
						continue;
					}
					if (!fielddata[i].trim().matches(criteria[i])) {
						match = false;
						break;
					}
					
				}
				if (match) {
					matchingIndices.add(new Integer(recno));
				}
				recno++;
			}
			
			int noofmatches = matchingIndices.size();
			int[] retvalue = new int[noofmatches];
			for (int i = 0; i < noofmatches; i++) {
				retvalue[i] = (matchingIndices.get(i)).intValue();
			}
			return retvalue;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
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
	
	private class LockManager {
		
		/**
		 * @param pRecNo
		 * @return
		 */
		public Long getOwner(int pRecNo) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
