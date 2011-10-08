import suncertify.db.Data;

/**
 * 
 */

/**
 * @author Koosie
 * 
 */
public class LockThreadTest {
	
	public static void main(String[] args) throws Exception {
		Data db = new Data("db-1x1.db");
		LockTestThread lt1 = new LockTestThread("DB-1", db);
		lt1.start();
		
		LockTestThread lt2 = new LockTestThread("DB-2", db);
		lt2.start();
		
		LockTestThread lt3 = new LockTestThread("DB-3", db);
		lt3.start();
		
		LockTestThread lt4 = new LockTestThread("DB-4", db);
		lt4.start();
	}
	
	/**
	 * This class is just to test the locking functionality. It implements a
	 * thread that continuously acquires and releases locks on random record
	 * numbers including -1 (i.e. db lock) Create multiple instances of this
	 * class and start them to simulate multiple clients trying to acquire
	 * locks.
	 */
	public static class LockTestThread extends Thread {
		
		private String	name	= "";
		private Data	dbi		= null;
		
		public LockTestThread(String name, Data db) {
			LockTestThread.this.dbi = db;
			LockTestThread.this.name = name;
			
		}
		
		@Override
		public void run() {
			try {
				while (true) {
					int recno = (int) (Math.random() * 10) - 1; // substract 1
																// so that recno
																// can also be
																// -1 for
																// database
																// lock.
					int sleeptime1 = (int) (Math.random() * 10);
					int sleeptime2 = (int) (Math.random() * 10);
					if (recno == -1) {
						sleeptime2 = 5;
					}
					// System.out.println(name + " Sleeping : " +
					// sleeptime1+"   "+sleeptime2);
					Thread.sleep(sleeptime1 * 1000);
					long tm1 = System.currentTimeMillis();
					Long key = dbi.lock(recno);
					long tm2 = System.currentTimeMillis();
					System.out.println(name + " got lock for : " + recno + " in " + (tm2 - tm1) + " millis.");
					Thread.sleep(sleeptime2 * 1000);
					// System.out.println(name + " unlocking DB after " +
					// sleeptime2 + " secs.");
					dbi.unlock(recno, key);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
