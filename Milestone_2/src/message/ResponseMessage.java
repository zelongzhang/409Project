package message;

public class ResponseMessage extends Message {

	private static final long serialVersionUID = 2L;
	private String failureMessage;
	public ResponseMessage(String resp, String failuremessage) {
		super(resp);
		this.failureMessage = failuremessage;
	}
	
	public String getFailureMessage()
	{
		return this.failureMessage;
	}
	
}
