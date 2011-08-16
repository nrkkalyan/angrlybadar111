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

/**
 * @author eradkal
 * 
 */
public interface ExtendedDB extends DB {

	/**
	 * @throws DatabaseException
	 */
	public void saveData()
			throws DatabaseException;
}
