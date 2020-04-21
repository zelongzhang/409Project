import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of a server for the registration application
 * Handles all client connections and constructs the initial data model.
 * @author Kevin
 * @version 1.0
 * @since Apr 20, 2020
 */
public class RegistrationServer 
{
	private ServerSocket serverSocket;
	private DBManager dbManager;
	ExecutorService clientThreadPool;
	private int maxClients = 100;
	boolean end = false;
	
	/**
	 * Constructor for the server, opens a server socket, establishes a thread pool for future clients and establishes the connection to the database.
	 */
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
	
	/**
	 * Starts the server functionality, builds the initial data model of course catalog and begins to accept client connections.
	 * @throws InterruptedException if thread is interrupted, abandon it without exiting
	 * @throws SocketException if socket disconnects, abandon it without exiting
	 */
	protected void start() throws InterruptedException, SocketException
	{
		System.out.println("Registration server starting...");
		this.dbManager.updateCatalog();
		this.dbManager.updateUsers();
		this.dbManager.updateRegistrations();
		System.out.println(this.dbManager);
		System.out.println("\nServer is ready... waiting for Client Connections...");
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
	
	/**
	 * Main class of the server package
	 * @param args no arguments needed
	 * @throws InterruptedException
	 * @throws SocketException
	 */
	public static void main(String[] args) throws InterruptedException, SocketException
	{
		RegistrationServer regServer = new RegistrationServer();
		regServer.start();
	}
}
