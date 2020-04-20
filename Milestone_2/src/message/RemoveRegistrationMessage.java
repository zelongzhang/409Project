package message;

public class RemoveRegistrationMessage extends Message
{

	private static final long serialVersionUID = 8L;
	private String courseName;
	private int courseNum;
	private int sectionNum;
	
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
