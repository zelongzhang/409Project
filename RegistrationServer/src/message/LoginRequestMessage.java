package message;

public class LoginRequestMessage extends Message {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
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
