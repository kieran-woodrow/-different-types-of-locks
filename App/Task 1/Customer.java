import java.util.logging.Logger;


public class Customer extends Thread{

	private static final Logger LOGGER = Logger.getLogger("global");
	public static int wait = CustomerOptions.wait;
	public static int customers = CustomerOptions.customers;
	public static int runs = CustomerOptions.runs;

	TTASLock lock;

	public Customer(TTASLock lock)
	{
		this.lock = lock;
	}

	public void run()
	{
		try{
			int max = 49;
			int min = 10;

			int range = max - min +1;

			int rand = (int)(Math.random()*range) + min;
			sleep(rand);

			lock.lock();
			
			int maxAgain = 49;
			int minAgain = 10;
			int rangeAgain = maxAgain - minAgain +1;

			int randAgain = (int)(Math.random()*rangeAgain) + minAgain;
			sleep(randAgain);

			lock.unlock();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}