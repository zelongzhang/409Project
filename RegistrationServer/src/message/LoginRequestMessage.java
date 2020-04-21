package message;
/**
 * Implementation of a log in request from the client to the server.
 * @author Maaz Khurram
 * @author Kevin Zhang
 * @author Saksham Nanda
 * @version 1.0
 * @since Apr 20, 2020
 *
 */
public class LoginRequestMessage extends Message {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
	/**
	 * To request for a log in, the client must provide the user name and password the user has entered.
	 * @param username the user name entered by the user.
	 * @param password the password entered by the user.
	 */
	public LoginRequestMessage(String username, String password) {
		super("LoginRequest");
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	
}
