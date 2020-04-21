import java.awt.BorderLayout;
import javax.swing.*;

/**
 *
 * This class represents and helps create a login frame
 * 
 * @author Maaz Khurram
 * @author Kevin Zhang
 * @author Saksham Nanda
 * @since 20th April
 *
 */

public class LoginFrame extends JFrame {

	/**
	 * Enables serialization
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Buttons for login frame
	 */
	JButton Login, Exit;
	/**
	 * Panels for login frame
	 */
	JPanel southPanel, centerPanel, westPanel;
	/**
	 * username field for login frame
	 */
	JTextField usernameField;
	/**
	 * password field for login frame
	 */
	JPasswordField passwordField;
	/**
	 * Labels for fields in Login frame
	 */
	JLabel username, password;

	/**
	 * Constructs an object of type LoginFrame with the given title
	 * 
	 * @param title
	 */
	public LoginFrame(String title) {
		super(title);
		Login = new JButton("Login");
		Exit = new JButton("Quit Program");

		southPanel = new JPanel();
		southPanel.add(Login);
		southPanel.add(Exit);

		username = new JLabel("Username: ");
		password = new JLabel("Password: ");

		usernameField = new JTextField("Maaz Khurram", 15);
		passwordField = new JPasswordField(15);

		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));

		JPanel tempUsernamePanel = new JPanel();
		tempUsernamePanel.add(username);
		tempUsernamePanel.add(usernameField);

		JPanel tempPasswordPanel = new JPanel();
		tempPasswordPanel.add(password);
		tempPasswordPanel.add(passwordField);

		centerPanel.add(tempUsernamePanel);
		centerPanel.add(tempPasswordPanel);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 60, 0));

		this.add("South", southPanel);
		this.add("Center", centerPanel);

		this.setVisible(true);
		pack();

	}

	/**
	 * Displays a pop-up window with specified error message
	 */
	public void showError() {
		JOptionPane.showMessageDialog(null, "The username or password is invalid. Pleasy try again.", "Invalid Login",
				JOptionPane.ERROR_MESSAGE);
	}

	public JButton getLogin() {
		return Login;
	}

	public JButton getExit() {
		return Exit;
	}

	public String getUsernameField() {
		return usernameField.getText();
	}

	public String getPasswordField() {

		return String.valueOf(passwordField.getPassword());
	}

}
