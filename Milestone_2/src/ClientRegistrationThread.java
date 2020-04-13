import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import message.CatalogDataMessage;
import message.CatalogRequestMessage;
import message.Message;

public class ClientRegistrationThread implements Runnable{
	
	private Socket socket;
	private MainFrame mf;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	private boolean running = true;
	
	public ClientRegistrationThread(Socket socket, MainFrame mainframe)
	{
		this.socket = socket;
		this.mf = mainframe;
		try 
		{
			this.fromServer = new ObjectInputStream(this.socket.getInputStream());
			this.toServer = new ObjectOutputStream(this.socket.getOutputStream());
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
			//if(SHOW ALL COURSES BUTTON PUSHED)
			
			{
				Message catalogRequest = new CatalogRequestMessage();
				try 
				{
					this.toServer.writeObject(catalogRequest);
					this.toServer.flush();
					CatalogDataMessage catalogData = (CatalogDataMessage)this.fromServer.readObject();
					ArrayList<String> data = catalogData.getCatalog();
					//Do something with data...
					
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				} 
				catch (ClassNotFoundException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
	}

}
