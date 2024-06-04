// Practical 5
// Name and surname: Kieran Woodrow
// Student number: u19304308

import java.util.logging.*;
import java.util.concurrent.locks.*;

public class FIFOReadWriteLock {

    private static final Logger LOGGER = Logger.getLogger("global");

    boolean theWriter;
    int readGet;
    int readReleases;
    Condition condition;
    Lock readLock;
    Lock writeLock;
    Lock theLock;

    public FIFOReadWriteLock() 
    {
        theWriter = false;
        
        readGet = 0; 
        readReleases = 0;

        theLock = new ReentrantLock();
        readLock = new ReadLock();
        writeLock = new WriteLock();
        condition = theLock.newCondition();
	}

	public Lock readLock() {
	    // @todo: return appropriate object
		return readLock;
	}

	public Lock writeLock() {
	    // @todo: return appropriate object
		return writeLock;
	}


    /* -------------------------------------------------- *
     *      Nested ReadLock Class
     * -------------------------------------------------- */
    private class ReadLock extends AbstractLock {

        public void lock() 
        {
            theLock.lock();

            LOGGER.info("Thread " + ThreadID.get() + " wants to acquire the read lock");

            try
            {
                while(theWriter)
                {
                    condition.await();
                }

                readGet = readGet+1;

                LOGGER.info("Thread " + ThreadID.get() + " acquired the read lock");

            } 
            catch(InterruptedException e)
            {
                e.printStackTrace();
            } 

            finally 
            {
                theLock.unlock();
            }
        }

        public void unlock() 
        {
            theLock.lock();

            try
            {
                readReleases= readReleases+1;

                if(readGet == readReleases)
                    condition.signalAll();
            } 
            finally 
            {
                theLock.unlock();

                LOGGER.info("Thread " + ThreadID.get() + " released the read lock");
            }
        }

    }

    /* -------------------------------------------------- *
     *      Nested WriteLock Class
     * -------------------------------------------------- */
    private class WriteLock extends AbstractLock {

        public void lock() 
        {
            theLock.lock();

            LOGGER.info("Thread " + ThreadID.get() + " wants to acquire the write lock");

            try
            {
                while(theWriter)
                {
                    condition.await();
                }
                theWriter = true;

                while(readGet != readReleases)
                    condition.await();
                LOGGER.info("Thread " + ThreadID.get() + " acquired the write lock");
            } catch(InterruptedException e){
                e.printStackTrace();
            } finally {
                theLock.unlock();
            }
        }

        public void unlock() 
        {
            theLock.lock();

            try
            {
                theWriter = false;
                condition.signalAll();
            } 
            finally 
            {
                theLock.unlock();
                LOGGER.info("Thread " + ThreadID.get() + " released the write lock");
            }
        }

    }

}
