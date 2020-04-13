import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.JFrame;




public class MainFrame extends JFrame{


	private static final long serialVersionUID = 1L;
	private JButton ViewCC, SearchCC , ViewStudentCourses, AddCourse, RemoveCourse , Exit;
	private JPanel SouthPanel, WestPanel, CenterPanel;
	private JTextArea records;
	private JScrollPane scroll;
	private JLabel MainLabel;
	
	public MainFrame(String title)
	{
		super(title);
		ViewCC = new JButton("View Catalogue");
		SearchCC = new JButton("Search Catalogue");
		ViewStudentCourses = new JButton("Search Student Courses");
		AddCourse = new JButton("Add Course");
		RemoveCourse = new JButton("Remove Course");
		Exit = new JButton("Exit");
		
		SouthPanel = new JPanel();
		SouthPanel.add(ViewCC);
		SouthPanel.add(SearchCC);
		SouthPanel.add(ViewStudentCourses);
		SouthPanel.add(Exit);

		
		
		WestPanel = new JPanel(new BorderLayout());
		WestPanel.add("North",AddCourse);
		WestPanel.add("South", RemoveCourse);
		
		
		CenterPanel = new JPanel();
		records = new JTextArea(100,57);
		records.setEditable(false);
		records.append("initial text\n");

		
		
		scroll = new JScrollPane(records, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		CenterPanel.add(scroll);
		
		
	
		this.add("West", WestPanel);
		this.add("South", SouthPanel);
		this.add("Center", CenterPanel);
		
		
		
		ViewCC.addActionListener( (ActionEvent e) -> {	
			
			ViewCourseCatalogue VCC = new ViewCourseCatalogue(records);
			
		}	);	
		
		
		
		
		
		
		
		SearchCC.addActionListener( (ActionEvent e) -> {	System.out.println("SearchCC");	}	);
		
		ViewStudentCourses.addActionListener( (ActionEvent e) -> {	
			String id = JOptionPane.showInputDialog("Please enter the student's ID");
			String courseName = JOptionPane.showInputDialog("Please enter the course name");
			}	);
		
		AddCourse.addActionListener( (ActionEvent e) -> {	
			String id = JOptionPane.showInputDialog("Please enter the student's ID");
			String courseName = JOptionPane.showInputDialog("Please enter the course name");
			}	);
		
		RemoveCourse.addActionListener( (ActionEvent e) -> {	
			String id = JOptionPane.showInputDialog("Please enter the student's ID");
			String courseName = JOptionPane.showInputDialog("Please enter the course name");
			}	);
		
		Exit.addActionListener( (ActionEvent e) -> {	this.dispose();	}	);

		this.setVisible(true);
		pack();
		
		
		
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
		MainFrame mf = new MainFrame("Schedule Builder");
		mf.setSize(1000, 563);

	}

}
