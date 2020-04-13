import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.CatalogDataMessage;
import message.Message;

public class RegistrationThread implements Runnable
{
	
	private Socket clientSocket;
	private DBManager dbManager;
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	private boolean running = true;
	
	public RegistrationThread(Socket clientsocket, DBManager dbmanager)
	{
		
		this.clientSocket = clientsocket;
		System.out.println("Accepted new connection..."+clientSocket.getInetAddress()+"on port "+this.clientSocket.getPort());
		this.dbManager = dbmanager;
		try 
		{
			this.fromClient = new ObjectInputStream(this.clientSocket.getInputStream());
			this.toClient = new ObjectOutputStream(this.clientSocket.getOutputStream());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() 
	{
		while(running)
		{
			try 
			{
				Message message = (Message) fromClient.readObject();
				switch(message.getInstruction())
				{
				case "CatalogRequest":
					toClient.writeObject(new CatalogDataMessage(this.dbManager.getCourseCatalog().sendingFormat()));
					toClient.flush();
					break;
				}
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}

}
