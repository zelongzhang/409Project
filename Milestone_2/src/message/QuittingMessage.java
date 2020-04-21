package message;

/**
 * Implementation of a quit message sent from the client to the server to indicate end of activities.
 * @author Kevin
 * @version 1.0
 * @since Apr 20, 2020
 *
 */
public class QuittingMessage extends Message
{
	private static final long serialVersionUID = 11L;

	/**
	 * No additional data is needed for a quit message.
	 * @param instruction
	 */
	public QuittingMessage() 
	{
		super("Quit");
	}
	
}
