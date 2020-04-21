import java.util.ArrayList;

/**
 * Implementation of a course catalog, a collection of courses for the purposes of a registration application.
 * @author Maaz Khurram
 * @author Kevin Zhang
 * @author Saksham Nanda
 * @version 1.0
 * @since Apr 20, 2020
 */
public class CourseCatalog 
{
	private ArrayList<Course> courseList;
	
	/**
	 * Default Constructor.
	 */
	public CourseCatalog()
	{
		this.courseList = new ArrayList<Course>();
	}
	
	/**
	 * Searches the catalog for a course by its name and number.
	 * @param courseName the name of the course to be searched for.
	 * @param courseNum the number of the course to be searched for.
	 * @return the course with corresponding courseName and courseNum if it exists in the catalog.
	 */
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
	
	/**
	 * Searches the catalog for a course by its course ID.
	 * @param courseID the ID of the course to be searched.
	 * @return the course with corresponding courseID if it exists in the catalog.
	 */
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
	
	/**
	 * Adds a new course to the catalog.
	 * @param newCourse the course to be added.
	 */
	public void addCourse(Course newCourse) 
	{
		if(!courseList.contains(newCourse))
		{
			this.courseList.add(newCourse);
		}
	}
	
	/**
	 * Removes a course from the catalog if it exists.
	 * @param course the course to be removed.
	 */
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
	
	/**
	 * Converts the catalog into a format suitable for communication with the client.
	 * @return An arrayList of Strings, with each element representing a single course.
	 */
	public ArrayList<String> toSendFormat()
	{
		ArrayList<String> data = new ArrayList<String>();
		for(Course course : this.courseList)
		{
			data.add(course.toSendFormat());
		}
		return data;
	}
	
	@Override
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
			s+=", Prereqs: ";
			for(Course pre : c.getPrereq())
			{
				s+=pre.getCourseName()+pre.getCourseNum()+",";
			}
			s+="\n";
		}
		return s;
	}
	
}
