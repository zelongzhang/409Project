import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import message.*;
import java.util.*;

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
		
		createLoginListener();

		
		
		
		
		
	
	}

	
	
	
	
	private void createLoginListener() {
			lf.getLogin().addActionListener((ActionEvent e) ->
			
			{
				System.out.println("Login");
				Message loginRequest = new LoginRequestMessage();
				Map<String, String> data = new HashMap<String, String>();
				data.put("Username", lf.getUsernameField());
				data.put("Password", lf.getPasswordField());
				loginRequest.setData(data);
				
				try 
				{
					this.toServer.writeObject(loginRequest);
					this.toServer.flush();
					LoginDataMessage response = (LoginDataMessage) this.fromServer.readObject();
					
					if (response.getInstruction().equals("TRUE"))
					{
						lf.dispose();
						this.mf = new MainFrame("Schedule Builder");
						mf.setSize(1000, 563);
						mf.setVisible(true);
						createViewCourseCatalogueListener();
						
						
					}
					
					
					else if (response.getInstruction().equals("FALSE"))
					{
						lf.showError();
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
		
			});
			
		}

	private void createViewCourseCatalogueListener() {
		//if(SHOW ALL COURSES BUTTON PUSHED)
		mf.getViewCC().addActionListener( (ActionEvent e) -> 
		{
			System.out.println("View Course Catalogue");
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
