package message;

public class SearchCatalogRequestMessage extends Message {


	private static final long serialVersionUID = 9L;
	private String courseName;
	private int courseNum;
	private int sectionNum;
	public SearchCatalogRequestMessage(String coursename, int coursenum, int sectionnum) 
	{
		super("SearchCatalogueRequestMessage");
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
