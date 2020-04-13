package message;
import java.io.Serializable;
import java.util.Map;

public abstract class Message implements Serializable
{
	private static final long serialVersionUID = 1L;
	String instruction;
	Map<?,?> data;
	
	public Message(String instruction)
	{
		this.instruction = instruction;
	}
	


	public void setData(Map<?,?> data) {
		this.data = data;
	}

	public String getInstruction()
	{
		return this.instruction;
	}
	
	public Map<?,?> getData()
	{
		return this.data;
	}
	
}

