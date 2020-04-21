import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class RegistrationClient 
{
	private Socket clientSocket;
	private MainFrame mf;
	private LoginFrame lf;
	
	public RegistrationClient()
	{
		try 
		{
			clientSocket = new Socket(InetAddress.getByName("localhost"), 8008); //70.65.104.103 
			
			lf = new LoginFrame("Login");
			lf.setSize(500, 250);
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void start()
	{
		System.out.println("starting client...");
		Thread clientThread = new Thread(new ClientRegistrationThread(this.clientSocket,this.mf, this.lf));
		clientThread.start();
		try 
		{
			clientThread.join();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		RegistrationClient regClient = new RegistrationClient();
		System.out.println("Client started...");
		
		regClient.start();
	}
}
