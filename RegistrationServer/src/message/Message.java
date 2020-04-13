package message;
import java.io.Serializable;
import java.util.Map;

public abstract class Message implements Serializable
{
	private static final long serialVersionUID = 1L;
	String instruction;
	Map<Object,Object> data;
	
	public String getInstruction()
	{
		return this.instruction;
	}
	
	public Map<Object,Object> getData()
	{
		return this.data;
	}
	
}

