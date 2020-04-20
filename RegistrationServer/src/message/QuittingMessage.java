package message;

public class QuittingMessage extends Message
{


	private static final long serialVersionUID = 11L;

	public QuittingMessage(String instruction) 
	{
		super("Quit");
	}
	
}
