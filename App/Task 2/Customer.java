import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Customer extends Thread{

	private static final Logger LOGGER = Logger.getLogger("global");
	public static int runs = CustomerOptions.runs;
	public static int customers = CustomerOptions.customers;

	AndersonQueueLock lock;

	public Customer(AndersonQueueLock lock){
		this.lock = lock;
	}

	public void run(){
		try{
			sleep(ThreadID.get()*100%75);

			lock.lock();
			
			int max = 50;
			int min = 10;
			int range = max - min +1;
			int rand = (int)(Math.random()*range) + min;
			sleep(rand);

			lock.unlock();
			
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}