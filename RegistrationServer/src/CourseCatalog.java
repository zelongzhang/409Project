import java.util.ArrayList;

public class CourseCatalog 
{
	private ArrayList<Course> courseList;
	
	public CourseCatalog()
	{
		this.courseList = new ArrayList<Course>();
	}
	
	public Course searchForCourse(String courseName, int courseNum)
	{
		for(Course course : courseList)
		{
			if(course.getCourseName().equals(courseName) && course.getCourseNum() == courseNum)
			{
				return course;
			}
		}
		System.out.println("Course "+courseName+courseNum+" does not exist in the catalog.");
		return null;
	}

	public void addCourse(Course newCourse) 
	{
		if(!courseList.contains(newCourse))
		{
			this.courseList.add(newCourse);
		}
	}
	public void removeCourse(Course course)
	{
		for(Course c : courseList)
		{
			if(c.equals(course))
			{
				this.courseList.remove(c);
				return;
			}
		}
		System.out.println("Course "+course+" was not found in the catalog.");
	}
	
	public String toString()
	{
		String s = "";
		for(Course c : this.courseList)
		{
			s+=c.toString()+": ";
			for(CourseSection sec: c.getSections())
			{
				s+=sec.toString()+" ";
			}
			s+="\n";
		}
		return s;
	}
	
}