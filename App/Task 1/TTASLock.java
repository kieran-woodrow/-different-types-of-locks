// Practical 5
// Name and surname: Kieran Woodrow
// Student number: u19304308

import java.util.logging.Logger;
import java.util.concurrent.atomic.AtomicBoolean;

public class TTASLock extends AbstractLock 
{
	AtomicBoolean theState = new AtomicBoolean(false);

	private static final Logger LOGGER = Logger.getLogger("global");

	public int lockH = -10;

	public void lock() 
	{
		LOGGER.info("Customer " + ThreadID.get() + " is trying to enter the store");

		while(true)
		{
			while(theState.get())
			{
				//log line
				if(lockH > -10)
				{
					LOGGER.info("Customer " + ThreadID.get() + " tried to enter the store but sees customer " + lockH + " is already in the store");

					try
					{
						Thread.sleep(CustomerOptions.wait);
					}
					catch (InterruptedException e){
						e.printStackTrace();
					}
				}
			}
			if(!theState.getAndSet(true))
			{
				LOGGER.info("Customer " + ThreadID.get() +" has entered the store");
				lockH = ThreadID.get();
				return;
			}
		}
	}
	
	 public void unlock() 
	 {
 		LOGGER.info("Customer " + ThreadID.get() + " has left the store");
 		theState.set(false);
 	}

}
