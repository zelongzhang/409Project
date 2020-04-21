import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.text.BadLocationException;

/**
*	Represents a frame that asks user for Course Section number
*
* @author MaazKhurram
* @version 1.0
* @since 20th April
*
*/

public class CourseSectionFrame extends JFrame {

	
	/**
	 * to enable serialization
	 */
	private static final long serialVersionUID = 99L;
	/**
	 * text area in centre frame
	 */
	private JTextArea sections;
	/**
	 * Scroll Pane for text area
	 */
	private JScrollPane scroll;
	/**
	 * Some buttons for section frame in south panel
	 */
	private JButton OK, Exit;
	/**
	 * Panels to arrange JComponents
	 */
	private JPanel SouthPanel, CenterPanel;
	/**
	 * To store sourse section
	 */
	private String courseSection;

	/**
	 * Counstructs an object of CourseSectionFrame with the given array of Strings
	 * @param StringsOfCourseSections
	 */
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
