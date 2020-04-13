
public class Registration 
{
	private CourseSection courseSection;
	private Student student;
	private char grade;
	
	public Registration(Student student, CourseSection coursesection)
	{
		this.student = student;
		this.courseSection = coursesection;
	}
	
	public CourseSection getCourseSection()
	{
		return this.courseSection;
	}
	
	public Student getStudent()
	{
		return this.student;
	}
	
	public String toString()
	{
		return "[Registration: "+this.student+", "+this.courseSection+" ]";
	}
	
	public boolean equals(Object o)
	{
		if(o == this)
		{
			return true;
		}
		if(o instanceof Registration
			&& ((Registration)o).getCourseSection().equals(this.getCourseSection())
			&& ((Registration)o).getStudent().equals(this.getStudent()))
		{
			return true;
		}
		return false;
	}
}
