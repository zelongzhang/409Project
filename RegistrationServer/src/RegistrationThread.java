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
	private User Student;
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
			this.toClient = new ObjectOutputStream(this.clientSocket.getOutputStream());
			this.toClient.flush();
			this.fromClient = new ObjectInputStream(this.clientSocket.getInputStream());
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
				case "LoginRequest":
					
					
					break;
				case "CatalogRequest":
					toClient.writeObject(new CatalogDataMessage(this.dbManager.getCourseCatalog().sendingFormat()));
					toClient.flush();
					break;
				case "ViewStudentCourseRequest":
					
					
					break;
				case "AddRegistrationRequest":
					
					
					break;
				case "RemoveRegistrationRequest":
					
					
					break;
				case "SearchReqeuest":
					
					
					break;
				}
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
				return;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				return;
			}
		}
	}

}
