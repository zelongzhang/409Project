package message;

/**
 * Implementation of a search catalog query sent from client to server.
 * @author Kevin
 * @version 1.0
 * @since Apr 20, 2020
 *
 */
public class SearchCatalogRequestMessage extends Message {


	private static final long serialVersionUID = 9L;
	private String courseName;
	private int courseNum;
	private int sectionNum;
	
	/**
	 * To send a query, the client must provide the course name, course number and section number for the search.
	 * @param coursename the name of the course to be found.
	 * @param coursenum the number of the course to be found.
	 * @param sectionnum the section number of the course section to be found.
	 */
	public SearchCatalogRequestMessage(String coursename, int coursenum, int sectionnum) 
	{
		super("SearchRequest");
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
