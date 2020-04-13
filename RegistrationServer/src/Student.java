import java.util.ArrayList;

public class Student extends User
{
	private ArrayList<Course> pastCourses;
	private ArrayList<Registration> regList;
	
	public Student(String studentName, String studentPassword, int studentID)
	{
		super(studentName, studentPassword, studentID);
		this.pastCourses = new ArrayList<Course>();
		this.regList = new ArrayList<Registration>();
	}
	
	public void addPrevCourse(Course course)
	{
		if(!this.pastCourses.contains(course))
		{
			this.pastCourses.add(course);
		}
	}
	
	public void removePrevCourse(Course course)
	{
		for(Course c : this.pastCourses)
		{
			if(c.equals(course))
			{
				this.pastCourses.remove(c);
				return;
			}
		}
		System.out.println("The course "+course+" was not found in student("+this+")'s previous courses.");
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
		System.out.println("The course "+reg.getCourseSection()+" was not found in student("+this+")'s registration list.");
	}
	
	public String toString()
	{
		return this.getUsername()+":"+this.getUserID();
	}
	
	public boolean equals(Object o)
	{
		if(o == this)
		{
			return true;
		}
		if(o instanceof Student 
			&& ((Student) o).getUsername() == this.getUsername() 
			&& ((Student) o).getPassword() == this.getPassword()
			&& ((Student) o).getUserID() == this.getUserID())
		{
			return true;
		}
		return false;
	}
}
