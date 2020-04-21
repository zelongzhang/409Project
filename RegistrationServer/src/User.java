
/**
 * Implementation of a user of the registration application, can be extended by a student class or an administrator class and so on.
 * @author Maaz Khurram
 * @author Kevin Zhang
 * @author Saksham Nanda
 * @version 1.0
 * @since Apr 20, 2020
 */
public abstract class User 
{
	private String username;
	private String password;
	private int userID;
	
	/**
	 * Every user should have a username, a password and an ID.
	 * @param username the name of the user.
	 * @param password the password of the user.
	 * @param userid the ID of the user.
	 */
	public User(String username, String password, int userid)
	{
		this.username = username;
		this.password = password;
		this.userID = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
}
