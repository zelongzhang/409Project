import javax.swing.*;


public class CourseSectionFrame extends JFrame {

	
	
	private static final long serialVersionUID = 1L;
	private JTextArea sections;
	private JScrollPane scroll;
	private JButton OK, Exit;
	private JPanel SouthPanel, CenterPanel;
	private int numberOfSections;
	
	public CourseSectionFrame(int numberOfSections)
	{
		super("Select Course Section");
		this.numberOfSections=numberOfSections;
		
		this.OK = new JButton("OK");
		this.Exit = new JButton("Exit");
		
		SouthPanel = new JPanel();
		SouthPanel.add(OK);
		SouthPanel.add(Exit);
		
		this.add(SouthPanel, "South");
		
		
		
		CenterPanel = new JPanel();
		sections = new JTextArea(100,57);
		sections.setEditable(false);
		sections.setText(null);
		
		for (int i=1 ; i<=numberOfSections ; i++)
		{
			sections.append(i+"\n");
		}
		
		
		
		
		
		scroll = new JScrollPane(sections, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		CenterPanel.add(scroll);

		
	}
	
}
