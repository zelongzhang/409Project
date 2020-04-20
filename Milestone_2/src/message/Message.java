package message;
import java.io.Serializable;
import java.util.Map;

public abstract class Message implements Serializable
{
	private static final long serialVersionUID = 0L;
	String instruction;
	
	public Message(String instruction)
	{
		this.instruction = instruction;
	}

	public String getInstruction()
	{
		return this.instruction;
	}
	
	
}

