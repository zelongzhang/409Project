import java.util.ArrayList;

/**
 * Implementation of a course section which belongs to a course for the implementation of a registration system.
 * @author Maaz Khurram
 * @author Kevin Zhang
 * @author Saksham Nanda
 * @version 1.0
 * @since Apr 20, 2020
 */
public class CourseSection 
{
	private int sectionNum;
	private int sectionCap;
	private Course course;
	private ArrayList<Registration> regList;
	
	/**
	 * Constructor for CourseSection, requires the section number, section capacity and the course it belongs to.
	 * @param num the course number.
	 * @param cap the capacity of the course.
	 * @param course The course this section belongs to.
	 */
	public CourseSection(int num, int cap, Course course)
	{
		this.sectionNum = num;
		this.sectionCap = cap;
		this.course = course;
		this.regList = new ArrayList<Registration>();
	}
	
	/**
	 * Adds a registration to the list of registrations for this section.
	 * @param reg the section to be added.
	 */
	public void addRegistration(Registration reg)
	{
		if(!this.regList.contains(reg))
		{
			this.regList.add(reg);
		}
	}
	
	/**
	 * Removes a registration from the list of registrations if it exists.
	 * @param reg the registration to be removed.
	 */
	public void removeRegistration(Registration reg)
	{
		for(Registration r : this.regList)
		{
			if(r.equals(reg))
			{
				this.regList.remove(r);
				return;
			}
		}
		System.out.println("The registration "+reg+ " does not exist in the section: "+this);
	}
	
	public int numberOfRegistrations()
	{
		return this.regList.size();
	}
	
	/**
	 * Converts this section to a format suitable for communication with the client.
	 * @return
	 */
	public String toSendFormat()
	{
		return this.getCourse().getCourseName()+","+this.getCourse().getCourseNum()+","+this.sectionNum+","+this.regList.size()+","+this.sectionCap;
	}
	
	@Override
	public String toString()
	{
		return "[Course: "+this.course.getCourseName()+this.course.getCourseNum()+" Section: "+this.sectionNum+" ("+this.regList.size()+"/"+this.sectionCap+")]";
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == this)
		{
			return true;
		}
		if(o instanceof CourseSection
				&& ((CourseSection)o).getSectionNum() == this.getSectionNum()
				&& ((CourseSection)o).getSectionCap() == this.getSectionCap()
				&& ((CourseSection)o).getCourse().equals(this.getCourse())	)
		{
			return true;
		}
		return false;
	}
	public int getSectionNum() {
		return sectionNum;
	}
	public int getSectionCap() {
		return sectionCap;
	}
	public Course getCourse()
	{
		return this.course;
	}
}
