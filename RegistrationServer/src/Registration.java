
/**
 * Implementation of a registration which represents a student registering in a course section for a registration application.
 * @author Maaz Khurram
 * @author Kevin Zhang
 * @author Saksham Nanda
 * @version 1.0
 * @since Apr 20, 2020
 */
public class Registration 
{
	private CourseSection courseSection;
	private Student student;
	private char grade;
	
	/**
	 * Constructor to tie a student together with a course section.
	 * @param student the student registering.
	 * @param coursesection the course section the student registers for.
	 */
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
	
	@Override
	public String toString()
	{
		return "[Registration: "+this.student+", "+this.courseSection+" ]";
	}
	
	@Override
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
