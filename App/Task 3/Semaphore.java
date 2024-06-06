import java.util.concurrent.locks.*;
import java.util.logging.*;

public class Semaphore {

	private static final Logger LOGGER = Logger.getLogger("global");

	final int maxCapacity;
	int theState;
	Lock theLock;
	Condition condition;

	public Semaphore(int capacity)
	{
		theState = 0;
		this.maxCapacity = capacity;
		theLock = new ReentrantLock();
		condition = theLock.newCondition();
	}

	public void acquire()
	{
		theLock.lock();

		LOGGER.info("Customer " + ThreadID.get() + " is trying to enter the store");

		LOGGER.fine("Available spaces in the store: " + (maxCapacity - theState));

		try
		{
			while(theState == maxCapacity)
			{
				LOGGER.info("Customer " + ThreadID.get() + " tried to enter the store but sees the store is at its capacity");
				condition.await();
			}

			theState = theState + 1;

		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally {
			LOGGER.info("Customer " + ThreadID.get() + " has entered the store");
			theLock.unlock();		
		}
	}

	public void release()
	{
		theLock.lock();

		try{
			theState = theState -1;
			condition.signalAll();
			LOGGER.info("Customer " + ThreadID.get() + " has left the store");
		} 
		finally {
			theLock.unlock();
		}
	}
}