import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.*;


//hello

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
		ViewStudentCourses = new JButton("View Student Courses");
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
		records.setText(null);
		
		
		scroll = new JScrollPane(records, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		CenterPanel.add(scroll);
		
		
		
		//MainLabel = new JLabel ("Schedule Builder");
		
		
		//this.add("North", MainLabel);
		this.add("West", WestPanel);
		this.add("South", SouthPanel);
		this.add("Center", CenterPanel);

		
		pack();
		//System.out.println("Ending of mainframe");
	}


	
	public void showError(String error)
	{
		JOptionPane.showMessageDialog(null, error, "ERROR!", JOptionPane.ERROR_MESSAGE);
	}
	
	
	
	
	
	public JButton getViewCC() {
		return ViewCC;
	}

	public void setViewCC(JButton viewCC) {
		ViewCC = viewCC;
	}


	public JTextArea getRecords() {
		return records;
	}


	public void setRecords(JTextArea records) {
		this.records = records;
	}


	public JButton getSearchCC() {
		return SearchCC;
	}


	public void setSearchCC(JButton searchCC) {
		SearchCC = searchCC;
	}


	public JButton getViewStudentCourses() {
		return ViewStudentCourses;
	}


	public void setViewStudentCourses(JButton viewStudentCourses) {
		ViewStudentCourses = viewStudentCourses;
	}


	public JButton getAddCourse() {
		return AddCourse;
	}


	public void setAddCourse(JButton addCourse) {
		AddCourse = addCourse;
	}


	public JButton getRemoveCourse() {
		return RemoveCourse;
	}


	public void setRemoveCourse(JButton removeCourse) {
		RemoveCourse = removeCourse;
	}


	public JButton getExit() {
		return Exit;
	}


	public void setExit(JButton exit) {
		Exit = exit;
	}
	



}
