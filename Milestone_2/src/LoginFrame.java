import java.awt.BorderLayout;
import javax.swing.*;

public class LoginFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	JButton Login , Exit;
	JPanel southPanel, centerPanel, westPanel;
	JTextField usernameField;
	JPasswordField passwordField;
	JLabel username, password;
	
	
	public LoginFrame(String title)
	{
		super (title);
		Login = new JButton("Login");
		Exit = new JButton ("Quit Program");
		
		southPanel = new JPanel();
		southPanel.add(Login);
		southPanel.add(Exit);
		
		
		
		username = new JLabel("Username: ");
		password = new JLabel("Password: ");
		
		usernameField = new JTextField("Maaz Khurram",15 );
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
		centerPanel.setBorder(BorderFactory.createEmptyBorder(60,0,60,0));
		

		
		this.add("South",southPanel);
		this.add("Center",centerPanel );
		
		this.setVisible(true);
		pack();

		
		
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

	public void showError()
	{
		JOptionPane.showMessageDialog(null, "The username or password is invalid. Pleasy try again.", "Invalid Login", JOptionPane.ERROR_MESSAGE);
	}



	
}
