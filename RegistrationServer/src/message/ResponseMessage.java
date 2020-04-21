package message;

/**
 * Implementation of a pass/fail response sent from the server to the client in response to a client request such as add registration.
 * @author Maaz Khurram
 * @author Kevin Zhang
 * @author Saksham Nanda
 * @version 1.0
 * @since Apr 20, 2020
 *
 */
public class ResponseMessage extends Message {

	private static final long serialVersionUID = 2L;
	private String failureMessage;
	
	/**
	 * The response can either be SUCCESS or FAIL, in the case of failure, a failure message is used to convey the reason for the failure.
	 * @param resp SUCCESS if passed, FAIL if failed.
	 * @param failuremessage The reason for failure if FAIL, empty if SUCCESS.
	 */
	public ResponseMessage(String resp, String failuremessage) 
	{
		super(resp);
		this.failureMessage = failuremessage;
	}
	
	public String getFailureMessage()
	{
		return this.failureMessage;
	}
	
}
