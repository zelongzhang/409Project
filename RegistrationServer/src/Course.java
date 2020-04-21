import java.util.ArrayList;

/**
 * Implementation of a University Course for the purporse of a registration application.
 * @author Maaz Khurram
 * @author Kevin Zhang
 * @author Saksham Nanda
 * @version 1.0
 * @since April 20, 2020
 */
public class Course 
{
	private String courseName;
	private int courseNum;
	private int courseID;
	private ArrayList<Course> prereq;
	private ArrayList<CourseSection> sections;
	
	/**
	 * Constructor to make a course with a course name course number and a course ID such as ENSF, 409, 0.
	 * @param courseName name of the course.
	 * @param courseNum the course number.
	 * @param courseID the course ID in the registration system.
	 */
	public Course(String courseName, int courseNum, int courseID)
	{
		this.courseName = courseName;
		this.courseNum = courseNum;
		this.courseID = courseID;
		this.prereq = new ArrayList<Course>();
		this.sections = new ArrayList<CourseSection>();
	}
	
	/**
	 * Adds a new section to the course
	 * @param sec the section to be added
	 */
	public void addSection(CourseSection sec)
	{
		if(!this.sections.contains(sec))
		{
			this.sections.add(sec);
		}
	}
	
	/**
	 * removes a section if it is in the course.
	 * @param sec the section to be removed.
	 */
	public void removeSec(Course sec)
	{
		for(CourseSection section : this.sections)
		{
			if(section.equals(sec))
			{
				this.sections.remove(section);
				return;
			}
		}
		System.out.println("The section "+sec+" was not found in the course " +this);
	}
	
	/**
	 * Adds a prerequisite course to this course's prerequisite list.
	 * @param req the prerequisite course to be added.
	 */
	public void addPrereq(Course req)
	{
		if(!this.prereq.contains(req))
		{
			this.prereq.add(req);
		}
	}
	
	/**
	 * Removes a prerequisite course from this course's prerequisite list if it is already in it.
	 * @param req the prerequisite course to be removed.
	 */
	public void removePrereq(Course req)
	{
		for(Course course : this.prereq)
		{
			if(course.equals(req))
			{
				this.prereq.remove(course);
				return;
			}
		}
		System.out.println("The course "+req+" was not found in the prereq of "+this);
	}
	
	/**
	 * Converts this course object into a String format suitable for sending to the client.
	 * @return the Sending format as a String for the client.
	 */
	public String toSendFormat()
	{
		StringBuilder courseString = new StringBuilder();
		courseString.append(this.getCourseName()+","+this.getCourseNum());
		for(CourseSection section : this.getSections())
		{
			courseString.append(","+section.getSectionNum()+","+section.numberOfRegistrations()+","+section.getSectionCap());
		}
		return courseString.toString();
	}
	
	@Override
	public String toString()
	{
		return this.courseName+this.courseNum;
	}
	
	@Override 
	public boolean equals(Object o)
	{
		if(o == this)
		{
			return true;
		}
		if(o instanceof Course 
				&& ((Course)o).getCourseName().equals(this.getCourseName())
				&& ((Course)o).getCourseNum() == this.getCourseNum())
		{
			return true;
		}
		return false;
	}
	
	/**
	 * searches this course for a section by section number.
	 * @param sectionNum the section number to search for.
	 * @return the section with sectionNum if it exists in the course.
	 */
	public CourseSection searchForSection(int sectionNum)
	{
		for(CourseSection sec : sections)
		{
			if(sec.getSectionNum() == sectionNum)
			{
				return sec;
			}
		}
		return null;
	}
	
	public String getCourseName() {
		return courseName;
	}

	public int getCourseNum() {
		return courseNum;
	}

	public ArrayList<CourseSection> getSections() {
		return sections;
	}

	public int getCourseID() {
		return courseID;
	}

	public ArrayList<Course> getPrereq() {
		return prereq;
	}
	
	
}
