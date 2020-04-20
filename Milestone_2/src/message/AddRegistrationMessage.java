package message;

public class AddRegistrationMessage extends Message
{


	private static final long serialVersionUID = 7L;
	private String courseName;
	private int courseNum;
	private int sectionNum;
	
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
