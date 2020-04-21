package message;

/**
 * Implementation of a add registration request from the client to the server.
 * @author Kevin
 * @version 1.0
 * @since Apr 20, 2020
 */
public class AddRegistrationMessage extends Message
{


	private static final long serialVersionUID = 7L;
	private String courseName;
	private int courseNum;
	private int sectionNum;
	
	/**
	 * To add a course, the server needs to know the course name, course number and the section number.
	 * @param coursename name of the course to be added.
	 * @param coursenum number of the course to be added.
	 * @param sectionnum number of the section to be added.
	 */
	public AddRegistrationMessage(String coursename, int coursenum, int sectionnum) {
		super("AddRegistrationRequest");
		this.courseName = coursename;
		this.courseNum = coursenum;
		this.sectionNum = sectionnum;
	}

	public String getCourseName() {
		return courseName;
	}

	public int getCourseNum() {
		return courseNum;
	}

	public int getSectionNum() {
		return sectionNum;
	}

}
