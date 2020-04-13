import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RegistrationThread implements Runnable
{
	
	private Socket clientSocket;
	private DBManager dbManager;
	private ObjectInputStream toClient;
	private ObjectOutputStream fromClient;
	
	public RegistrationThread(Socket clientsocket, DBManager dbmanager)
	{
		System.out.println("Accepted new connection..."+clientSocket.getInetAddress()+"on port "+this.clientSocket.getPort());
		this.clientSocket = clientsocket;
		this.dbManager = dbmanager;
		try 
		{
			this.toClient = new ObjectInputStream(this.clientSocket.getInputStream());
			this.fromClient = new ObjectOutputStream(this.clientSocket.getOutputStream());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() 
	{
	
		
	}

}
