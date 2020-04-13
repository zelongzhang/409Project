import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RegistrationServer 
{
	private ServerSocket serverSocket;
	private DBManager dbManager;
	ExecutorService clientThreadPool;
	private int maxClients = 100;
	boolean end = false;
	
	
	public RegistrationServer()
	{
		try 
		{
			this.serverSocket = new ServerSocket(8008);
			this.clientThreadPool = Executors.newFixedThreadPool(maxClients);
			this.dbManager = new DBManager();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			return;
		}
	}
	protected void start()
	{
		System.out.println("Registration server starting...");
		this.dbManager.updateCatalog();
		this.dbManager.updateUsers();
		System.out.println(this.dbManager);
		for(String s: this.dbManager.getCourseCatalog().sendingFormat())
		{
			System.out.println(s);
		}
		
		while(!end)
		{
			try 
			{
				this.clientThreadPool.submit(new RegistrationThread(this.serverSocket.accept(),this.dbManager));
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		try 
		{
			this.clientThreadPool.shutdown();
			this.clientThreadPool.awaitTermination(5, TimeUnit.SECONDS);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args)
	{
		RegistrationServer regServer = new RegistrationServer();
		regServer.start();
	}
}
