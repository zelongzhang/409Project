import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class RegistrationClient 
{
	private Socket clientSocket;
	private MainFrame mf;
	
	public RegistrationClient()
	{
		try 
		{
			clientSocket = new Socket("localhost", 8008);
			mf = new MainFrame("Schedule Builder");
			mf.setSize(1000, 563);
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
		Thread clientThread = new Thread(new ClientRegistrationThread(this.clientSocket,this.mf));
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
		regClient.start();
	}
}
