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
	public Course searchForCourse(int courseID)
	{
		for(Course course : courseList)
		{
			if(course.getCourseID()==courseID)
			{
				return course;
			}
		}
		System.out.println("Course with courseID"+courseID+" does not exist in the catalog.");
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
	public ArrayList<String> sendingFormat()
	{
		ArrayList<String> data = new ArrayList<String>();
		for(Course course : this.courseList)
		{
			StringBuilder courseString = new StringBuilder();
			courseString.append(course.getCourseName()+","+course.getCourseNum());
			for(CourseSection section : course.getSections())
			{
				courseString.append(","+section.getSectionNum()+","+section.getSectionCap()+","+section.numberOfRegistrations());
			}
			data.add(courseString.toString());
		}
		return data;
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
