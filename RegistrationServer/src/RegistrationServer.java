import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
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
	protected void start() throws InterruptedException, SocketException
	{
		System.out.println("Registration server starting...");
		this.dbManager.updateCatalog();
		this.dbManager.updateUsers();
		this.dbManager.updateRegistrations();
		System.out.println(this.dbManager);
		for(Course c: ((Student)this.dbManager.getUserList().get(0)).getPastCourses())
		{
			System.out.print(c.getCourseName()+c.getCourseNum()+" ");
		}
		System.out.println();
//		for(Registration reg: ((Student)this.dbManager.getUserList().get(0)).getRegList())
//		{
//			System.out.println(reg);
//		}
//		for(String s: this.dbManager.getCourseCatalog().toSendFormat())
//		{
//			System.out.println(s);
//		}
		
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

		this.clientThreadPool.shutdown();
		this.clientThreadPool.awaitTermination(5, TimeUnit.SECONDS);
	}
	
	
	public static void main(String[] args) throws InterruptedException, SocketException
	{
		RegistrationServer regServer = new RegistrationServer();
		regServer.start();
	}
}
