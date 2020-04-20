package message;

import java.util.ArrayList;

public class ViewStudentCoursesDataMessage extends Message
{


	private static final long serialVersionUID = 6L;
	private ArrayList<String> courseList;
	public ViewStudentCoursesDataMessage(String instruction, ArrayList<String> courselist) {
		super("ViewStudentCoursesData");
		this.courseList = courselist;
	}
	
	public ArrayList<String> getCourseList() {
		return courseList;
	}
	
}
