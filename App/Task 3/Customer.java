import java.util.logging.Logger;

public class Customer extends Thread{

	private static final Logger LOGGER = Logger.getLogger("global");
	public static int customers = CustomerOptions.customers;
	public static int runs = CustomerOptions.runs;
	public static int capacity = CustomerOptions.capacity;

	Semaphore theSemaphore;

	public Customer(Semaphore semaphore)
	{
		this.theSemaphore = semaphore;
	}

	public void run()
	{
		try{
			int max = 50;
			int min = 10;
			int range = max - min +1;
			int rand = (int)(Math.random()*range) + min;
			sleep(rand);

			theSemaphore.acquire();
			
			int maxAgain = 50;
			int minAgain = 10;
			int rangeAgain = maxAgain - minAgain +1;
			int randAgain = (int)(Math.random()*rangeAgain) + minAgain;
			sleep(randAgain);

			theSemaphore.release();

		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}