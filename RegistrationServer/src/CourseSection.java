import java.util.ArrayList;

public class CourseSection 
{
	private int sectionNum;
	private int sectionCap;
	private Course course;
	private ArrayList<Registration> regList;
	
	public CourseSection(int num, int cap, Course course)
	{
		this.sectionNum = num;
		this.sectionCap = cap;
		this.course = course;
		this.regList = new ArrayList<Registration>();
	}
	
	public void addRegistration(Registration reg)
	{
		if(!this.regList.contains(reg))
		{
			this.regList.add(reg);
		}
	}
	
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
	public String toSendFormat()
	{
		return this.getCourse().getCourseName()+","+this.getCourse().getCourseNum()+","+this.sectionNum+","+this.regList.size()+","+this.sectionCap;
	}
	public String toString()
	{
		return "[Course: "+this.course.getCourseName()+this.course.getCourseNum()+" Section: "+this.sectionNum+" ("+this.regList.size()+"/"+this.sectionCap+")]";
	}
	
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
