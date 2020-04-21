import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
*
*Represents the client and is responsible for handling the connection with the server
* @author MaazKhurram
* @version 1.0
* @since 20th April
*
*/


public class RegistrationClient 
{
	/**
	 * Handle to communicate with the socket
	 */
	private Socket clientSocket;
	/**
	 * Handle for main frame
	 */
	private MainFrame mf;
	/**
	 * handle for login frame
	 */
	private LoginFrame lf;
	
	/**
	 * Constructs an object of type RegistrationClient
	 */
	public RegistrationClient()
	{
		try 
		{
			clientSocket = new Socket(InetAddress.getByName("localhost"), 8008); //70.65.104.103 
			
			lf = new LoginFrame("Login");
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			lf.setLocation(dim.width/2-lf.getSize().width/2, dim.height/2-lf.getSize().height/2);
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
	
	/**
	 * Starts the client
	 */
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
