import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.text.BadLocationException;


public class CourseSectionFrame extends JFrame {

	
	
	private static final long serialVersionUID = 99L;
	private JTextArea sections;
	private JScrollPane scroll;
	private JButton OK, Exit;
	private JPanel SouthPanel, CenterPanel;
	private String courseSection;

	
	public CourseSectionFrame(String[] StringsOfCourseSections)
	{
		super("Select Course Section");

		
		this.OK = new JButton("OK");
		this.Exit = new JButton("Exit");
		
		SouthPanel = new JPanel();
		SouthPanel.add(OK);
		SouthPanel.add(Exit);
		
		this.add(SouthPanel, "South");
		
		
		
		CenterPanel = new JPanel();
		sections = new JTextArea(10, 25);
		sections.setEditable(false);
		sections.setText(null);
		

		for (int i=1 ; i<StringsOfCourseSections.length ; i++)
		{
			sections.append("["+ StringsOfCourseSections[i] +"\n");
		}

		scroll = new JScrollPane(sections, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		CenterPanel.add(scroll);
		
		

		this.add("South", SouthPanel);
		this.add("Center", CenterPanel);
		pack();
		this.setVisible(true);
		
	}


	public JTextArea getSections() {
		return sections;
	}


	public void setSections(JTextArea sections) {
		this.sections = sections;
	}


	public JButton getOK() {
		return OK;
	}


	public void setOK(JButton oK) {
		OK = oK;
	}


	public JButton getExit() {
		return Exit;
	}


	public void setExit(JButton exit) {
		Exit = exit;
	}


	public String getCourseSection() {
		return courseSection;
	}


	public void setCourseSection(String courseSection) {
		this.courseSection = courseSection;
	}


	public String findCourseSection() {
		
		
		
		
		return null;
	}
	
	
	
	
	
	
}
