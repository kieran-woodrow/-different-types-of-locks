// Practical 5
// Name and surname: Kieran Woodrow
// Student number: u19304308

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.io.FileInputStream;
import java.util.concurrent.atomic.AtomicInteger;

public class AndersonQueueLock extends AbstractLock {

	private static final Logger LOGGER = Logger.getLogger("global");


    ThreadLocal<Integer> mySlotIndex = new ThreadLocal<Integer> ()
    {
		protected Integer initialValue(){
			return 0;
		}
	};

    AtomicInteger theTail;
    
    volatile boolean[] flag;
    
	int theSize;

    public AndersonQueueLock(int numThreads) 
    {
        theSize = numThreads;

        theTail = new AtomicInteger(0);
        
        flag = new boolean[numThreads];

        for(int i=0; i<numThreads; i++)
        {
            if(i==0)
            {
                flag[i]=true;
            }
        		
            else
            {
                flag[i]=false;
            }
        		
        }
    }

    public void lock() 
    {
        int gap = (theTail.getAndIncrement() % theSize);
        LOGGER.fine("Thread " + ThreadID.get() + " has slot " + gap);
        mySlotIndex.set(gap);
        while(!flag[gap]){};
        LOGGER.info("Customer " + ThreadID.get() + " enters the store");
    }


    public void unlock() 
    {
    	LOGGER.info("Customer " + ThreadID.get() + " leaves the store");
        int gap = mySlotIndex.get();

        flag[gap] = false;

        flag[(gap + 1) % theSize] = true;
    }

}
