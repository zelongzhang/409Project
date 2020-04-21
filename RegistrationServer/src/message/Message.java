package message;
import java.io.Serializable;
import java.util.Map;

/**
 * Implementation of a message passed between the server and client
 * In its simplest form, it is essentially a string
 * However, classes may extend off Message to include different types of data and methods.
 * @author Kevin
 * @version 1.0
 * @since Apr 20, 2020
 */
public abstract class Message implements Serializable
{
	private static final long serialVersionUID = 0L;
	String instruction;
	
	/**
	 * Constructor, needs an instruction String.
	 * @param instruction the instruction or identifier of the message.
	 */
	public Message(String instruction)
	{
		this.instruction = instruction;
	}

	public String getInstruction()
	{
		return this.instruction;
	}
	
	
}

