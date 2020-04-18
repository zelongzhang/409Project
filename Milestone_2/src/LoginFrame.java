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
		Exit = new JButton ("Exit");
		
		southPanel = new JPanel();
		southPanel.add(Login);
		southPanel.add(Exit);
		
		
		
		username = new JLabel("Username: ");
		password = new JLabel("Password: ");
		
		usernameField = new JTextField("123456789",15 );
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



	
}
