import java.awt.event.ActionEvent;
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
	private LoginFrame lf;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	private boolean running = true;
	
	public ClientRegistrationThread(Socket socket, MainFrame mainframe, LoginFrame loginFrame)
	{
		this.socket = socket;
		this.mf = mainframe;
		this.lf = loginFrame;
		try 
		{
			this.toServer = new ObjectOutputStream(this.socket.getOutputStream());
			this.toServer.flush();
			this.fromServer = new ObjectInputStream(this.socket.getInputStream());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() 
	{
	
		lf.getLogin().addActionListener(	(ActionEvent e) ->
		
		{
			System.out.println("Login");
			
			
			mf = new MainFrame("Schedule Builder");
			mf.setSize(1000, 563);
			mf.setVisible(true);
		}
				
				
				
				);
		
		
		
		
		
		
			//if(SHOW ALL COURSES BUTTON PUSHED)
			mf.getViewCC().addActionListener( (ActionEvent e) -> 
			{
				mf.getRecords().setText(null);
				Message catalogRequest = new CatalogRequestMessage();
				try 
				{
					this.toServer.writeObject(catalogRequest);
					this.toServer.flush();
					CatalogDataMessage catalogData = (CatalogDataMessage)this.fromServer.readObject();
					ArrayList<String> data = catalogData.getCatalog();
					//System.out.println(data);
					
					for(String i : data)
					{
						//System.out.println(i);
						String str = new String();
						String[] contents = i.split(",");
						str = contents[0]+ contents[1] + ": ";
						System.out.println(str);
						if (contents.length%2==0)
						{
							for (int k=2; k< contents.length; k++)
							{
								str += "[Section: "+ contents[k]+ " (0/" + contents[++k]+")] ";
							}
						}
						else System.err.println("Wrong size of message****");
						
						str += "\n";
						mf.getRecords().append(str);

					}
					
					
				} 
				catch (IOException f) 
				{
					f.printStackTrace();
				} 
				catch (ClassNotFoundException f) 
				{
					f.printStackTrace();
				}
				
				
				
			}	);
			
		
	}

}
