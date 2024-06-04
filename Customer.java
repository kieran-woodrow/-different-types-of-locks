import java.util.logging.Logger;
import java.util.concurrent.locks.Lock;

public class Customer extends Thread{

	private static final Logger LOGGER = Logger.getLogger("global");

	Boolean theWriter;
	Lock theLock;

	int counter=0;

	public Customer(FIFOReadWriteLock lock, Boolean isWriter)
	{
		if(isWriter == false)
		{
			this.theLock = lock.readLock();
		}
				
		else
		{
			this.theLock = lock.writeLock();
		}
		
		this.theWriter=isWriter;
	}

	public void run()
	{
		try{

			if(theWriter)
			{
				LOGGER.fine("Thread " + ThreadID.get() + " is a writer");
			}
				
			else
			{
				LOGGER.fine("Thread " + ThreadID.get() + " is a reader");
			}
				
			theLock.lock();

			int highest = 50;
			int lowest = 10;
			int range = highest - lowest +1;


			int rand = (int)(Math.random()*range) + lowest;
			sleep(rand);

			theLock.unlock();


		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}