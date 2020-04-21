package message;

/**
 * Implementation of a remove registrtion request from the client to the server.
 * @author Maaz Khurram
 * @author Kevin Zhang
 * @author Saksham Nanda
 * @version 1.0
 * @since Apr 20, 2020
 *
 */
public class RemoveRegistrationMessage extends Message
{

	private static final long serialVersionUID = 8L;
	private String courseName;
	private int courseNum;
	private int sectionNum;
	
	/**
	 * To request removal of a course, the client must provide the course name, course number and section number of the course section to be removed.
	 * @param coursename the name of the course to be removed.
	 * @param coursenum the number of the course to be removed.
 	 * @param sectionnum the number of the section to be removed.
	 */
	public RemoveRegistrationMessage(String coursename, int coursenum, int sectionnum) 
	{
		super("RemoveRegistrationRequest");
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
