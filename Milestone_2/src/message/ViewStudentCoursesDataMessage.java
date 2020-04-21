package message;

import java.util.ArrayList;

/**
 * Implementation of sending a student's course list from the server to the client.
 * @author Kevin
 * @version 1.0
 * @since Apr 20, 2020
 *
 */
public class ViewStudentCoursesDataMessage extends Message
{


	private static final long serialVersionUID = 6L;
	private ArrayList<String> courseList;
	
	/**
	 * To send a student's course list, an ArrayList of Strings will be sent with each element representing a course's information.
	 * @param instruction 
	 * @param courselist
	 */
	public ViewStudentCoursesDataMessage(String instruction, ArrayList<String> courselist) 
	{
		super("ViewStudentCoursesData");
		this.courseList = courselist;
	}
	
	public ArrayList<String> getCourseList() {
		return courseList;
	}
	
}
