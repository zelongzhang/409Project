import java.util.ArrayList;
/**
 * Implementation of a Student using the registration application.
 * @author Maaz Khurram
 * @author Kevin Zhang
 * @author Saksham Nanda
 * @version 1.0
 * @since Apr 20, 2020
 */
public class Student extends User
{
	private ArrayList<Course> pastCourses;
	private ArrayList<Registration> regList;
	
	/**
	 * Constructor for Student with a name, a password and a student ID.
	 * @param studentName the name of the student.
	 * @param studentPassword the password the student uses to log in to the application.
	 * @param studentID The ID of the student.
	 */
	public Student(String studentName, String studentPassword, int studentID)
	{
		super(studentName, studentPassword, studentID);
		this.pastCourses = new ArrayList<Course>();
		this.regList = new ArrayList<Registration>();
	}
	
	/**
	 * Adds a course to this student's previously taken courses list.
	 * @param course the course to be added.
	 */
	public void addPrevCourse(Course course)
	{
		if(!this.pastCourses.contains(course))
		{
			this.pastCourses.add(course);
		}
	}
	
	/**
	 * Removes a course from this student's previously taken courses list.
	 * @param course the course to be removed.
	 */
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
	
	/**
	 * Adds a registration to this student's registration list.
	 * @param reg the registration to be added.
	 */
	public void addRegistration(Registration reg)
	{
		if(!this.regList.contains(reg))
		{
			this.regList.add(reg);
		}
	}
	
	/**
	 * Removes a registration from this student's registration list if it exists.
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
		System.out.println("The course "+reg.getCourseSection()+" was not found in student("+this+")'s registration list.");
	}
	
	/**
	 * Converts this student's registration list to a format suitable for sending to the client.
	 * @return An arrayList of type String with every element representing a course the student is registered for.
	 */
	public ArrayList<String> coursesToSendFormat() 
	{
		ArrayList<String> send = new ArrayList<String>();
		for(Registration reg : this.regList)
		{
			CourseSection section = reg.getCourseSection();
			send.add(section.toSendFormat());
		}
		return send;
	}	
	
	/**
	 * Searches this student's registration list for a specific registration.
	 * @param section the section of the registration to be searched.
	 * @return the registration with the corresponsind section if it exists.
	 */
	public Registration lookForRegistration(CourseSection section)
	{
		for(Registration reg: this.regList)
		{
			if(reg.getCourseSection().equals(section))
			{
				return reg;
			}
		}
		return null;
	}
	
	/**
	 * Checks if a student is already registered in a course section.
	 * @param sectionToAdd the section to check.
	 * @return true if student is already registered in the section, false otherwise.
	 */
	public boolean isAlreadyRegistered(CourseSection sectionToAdd) 
	{
		for(Registration reg : this.regList)
		{
			if(reg.getCourseSection().getCourse().equals(sectionToAdd.getCourse()))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return this.getUsername()+":"+this.getUserID();
	}
	
	@Override
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

	public ArrayList<Course> getPastCourses() {
		return pastCourses;
	}

	public ArrayList<Registration> getRegList() {
		return regList;
	}


	

	
}
