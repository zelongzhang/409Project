import java.util.ArrayList;

public class Course 
{
	private String courseName;
	private int courseNum;
	private int courseID;
	private ArrayList<Course> prereq;
	private ArrayList<CourseSection> sections;
	
	
	public Course(String courseName, int courseNum, int courseID)
	{
		this.courseName = courseName;
		this.courseNum = courseNum;
		this.courseID = courseID;
		this.prereq = new ArrayList<Course>();
		this.sections = new ArrayList<CourseSection>();
	}
	
	public void addSection(CourseSection sec)
	{
		if(!this.sections.contains(sec))
		{
			this.sections.add(sec);
		}
	}
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
	
	public void addPrereq(Course req)
	{
		if(!this.prereq.contains(req))
		{
			this.prereq.add(req);
		}
	}
	
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
	public String toString()
	{
		return this.courseName+this.courseNum;
	}
	
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
