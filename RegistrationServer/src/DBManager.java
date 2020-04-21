import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Implementation of a database manager for the registration application, 
 * it handles all the reads and write to the database and keeps the database in sync with the server objects.
 * @author Kevin
 * @version 1.0
 * @since apr 20, 2020
 */
public class DBManager 
{
	private Connection dbConnection;
	private CourseCatalog courseCatalog;
	private ArrayList<User> userList;
	
	/**
	 * Default constructor, establishes connection to MySQL dabatase.
	 */
	public DBManager()
	{
		this.courseCatalog = new CourseCatalog();
		this.userList = new ArrayList<User>();
		try 
		{
			this.dbConnection = DriverManager.getConnection("jdbc:mysql://70.65.104.103:3306/coursecatalog", "User","5c5YDO1CvP5Fx2TK");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString()
	{
		String s = this.courseCatalog.toString()+"\n";
		for(User user: userList)
		{
			s+=user.toString()+"\n";
		}
		return s;
	}
	
	/**
	 * updates the courses catalog by reading data from the courses table in the database.
	 */
	public void updateCatalog()
	{
		readCourses();
		readPrereqs();
		System.out.println("finished updating catalog");
	}
	
	/**
	 * updates the users list by reading data from the users table in the database.
	 */
	public void updateUsers()
	{
		readStudents();
		System.out.println("finished updating users");
	}
	
	/**
	 * updates the registrations of students and the sections by reading from the users and sections table in the database.
	 */
	public void updateRegistrations() 
	{
		readRegistrations();
		System.out.println("finished updating registrations");
	}

	
	/**
	 * Adds a registration to the database, adds a section to the registration field in the users table, and adds student's name to the registration field in the sections table.
	 * @param registration The registration to be added.
	 */
	public synchronized void addRegistrationToDB(Registration registration)
	{
		
		Student student = registration.getStudent();
		CourseSection section = registration.getCourseSection();
		try 
		{
			//Make a query to find the correct student
			Statement lookForStudent = this.dbConnection.createStatement();
			ResultSet studentRow = lookForStudent.executeQuery("SELECT * FROM users WHERE ID = "+student.getUserID());
			studentRow.next();
			//Update the registration field of the student with the new course section information
			String newRegistrationString = studentRow.getString("registrations");
			if(newRegistrationString==null || newRegistrationString.isEmpty())
			{
				newRegistrationString = section.getCourse().getCourseID()+" "+section.getSectionNum();
			}
			else
			{
				newRegistrationString+=","+section.getCourse().getCourseID()+" "+section.getSectionNum();
			}
			String updateStudents = "UPDATE users "
					+" SET registrations = '"+newRegistrationString+"'"
					+ "WHERE ID = "+studentRow.getInt("ID");
			Statement updateRegistrationStudent = this.dbConnection.createStatement();
			updateRegistrationStudent.executeUpdate(updateStudents);
			studentRow.close();
			lookForStudent.close();
			updateRegistrationStudent.close();
			
			//Make a query to find the correct course section.
			Statement lookForSection = this.dbConnection.createStatement();
			ResultSet sectionRow = lookForSection.executeQuery("SELECT * FROM sections WHERE courseID ="+section.getCourse().getCourseID()+" AND sectionNum = "+section.getSectionNum());
			sectionRow.next();
			//Update the registration field of the section with the new student information
			String newStudentListString = sectionRow.getString("registrations");
			if(newStudentListString==null || newStudentListString.isEmpty())
			{
				newStudentListString = student.getUsername();
			}
			else
			{
				newStudentListString+=","+student.getUsername();
			}
			String updateSection = "UPDATE sections "
					+" SET registrations = '"+newStudentListString+"'"
					+ "WHERE sectionID = "+sectionRow.getInt("sectionID");
			Statement updateRegistrationSection = this.dbConnection.createStatement();
			updateRegistrationSection.executeUpdate(updateSection);
			sectionRow.close();
			lookForSection.close();
			updateRegistrationSection.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Removes a registration from the database
	 * Removes the course section from the registration field in the users table if it exists
	 * Removes the student's name from the registration field in the sections table if it exists.
	 * @param registration the registration to be removed.
	 */
	public synchronized void removeRegistrationFromDB(Registration registration)
	{
		Student student = registration.getStudent();
		CourseSection section = registration.getCourseSection();
		try 
		{
			//Make a query to find the correct student.
			Statement lookForStudent = this.dbConnection.createStatement();
			ResultSet studentRow = lookForStudent.executeQuery("SELECT * FROM users WHERE ID = "+student.getUserID());
			studentRow.next();
			//Update the registration field of the student removing the course section information
			String oldRegistrations = studentRow.getString("registrations");
			if(oldRegistrations !=null)
			{
				String oldRegInfo[] = studentRow.getString("registrations").split(",");
				StringBuilder newRegistrationString = new StringBuilder();
				
				for(String reg : oldRegInfo)
				{
					if(Integer.parseInt(reg.split(" ")[0]) != registration.getCourseSection().getCourse().getCourseID()
							|| Integer.parseInt(reg.split(" ")[1]) != registration.getCourseSection().getSectionNum())
					{
						newRegistrationString.append(reg+",");
					}
				}
				if(newRegistrationString.length()!=0)
				{
					newRegistrationString.deleteCharAt(newRegistrationString.length()-1);
				}
				String updateStudents = "UPDATE users "
						+" SET registrations = '"+newRegistrationString.toString()+"'"
						+ "WHERE ID = "+studentRow.getInt("ID");
				Statement updateRegistrationStudent = this.dbConnection.createStatement();
				updateRegistrationStudent.executeUpdate(updateStudents);
				studentRow.close();
				lookForStudent.close();
				updateRegistrationStudent.close();
				
				
				//Make a query to find the correct section
				Statement lookForSection = this.dbConnection.createStatement();
				ResultSet sectionRow = lookForSection.executeQuery("SELECT * FROM sections WHERE courseID ="+section.getCourse().getCourseID()+" AND sectionNum = "+section.getSectionNum());
				sectionRow.next();
				//Update the registration for the section removing the student information
				String oldStudentListString = sectionRow.getString("registrations");
				StringBuilder newStudentListString = new StringBuilder();
				String oldStudentListInfo[] = oldStudentListString.split(",");
				for(String name : oldStudentListInfo)
				{
					if(!name.equals(registration.getStudent().getUsername()))
					{
						newStudentListString.append(name+",");
					}
				}
				if(newStudentListString.length()!=0)
				{
					newStudentListString.deleteCharAt(newStudentListString.length()-1);
				}
				String updateSection = "UPDATE sections "
						+" SET registrations = '"+newStudentListString.toString()+"'"
						+ "WHERE sectionID = "+sectionRow.getInt("sectionID");
				Statement updateRegistrationSection = this.dbConnection.createStatement();
				updateRegistrationSection.executeUpdate(updateSection);
				sectionRow.close();
				lookForSection.close();
				updateRegistrationSection.close();
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * Reads the database for course information to construct courses for the course catalog
	 * Populates course catalog's course list with courses and course sections.
	 */
	public void readCourses()
	{
		this.courseCatalog = new CourseCatalog();
		try 
		{
			//Query every row in the courses table
			Statement courseStm = this.dbConnection.createStatement();
			ResultSet courses = courseStm.executeQuery("select * from courses");
			//for every row, make a new course and populate it with the correct sections
			while(courses.next())
			{
				Course newCourse = new Course(courses.getString("coursename"),courses.getInt("courseNum"),courses.getInt("courseID"));
				
				Statement sectionStm = this.dbConnection.createStatement();
				ResultSet sections = sectionStm.executeQuery("select * from sections where courseID ="+courses.getInt("courseID"));
				while(sections.next())
				{
					CourseSection newSection = new CourseSection(sections.getInt("sectionNum"),sections.getInt("capacity"),newCourse);
					newCourse.addSection(newSection);
				}
				this.courseCatalog.addCourse(newCourse);
				sectionStm.close();
				sections.close();
			}
			courseStm.close();
			courses.close();
		}
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Reads the database for the prerequisite courses for each of the courses
	 * Populates each course's prerequisite list in the course catalog
	 * This is a separate method from readCourses because the courses needs to be created before they can be used as prerequisites.
	 */
	public void readPrereqs()
	{
		try 
		{
			//Query every row in the course
			Statement courseStm = this.dbConnection.createStatement();
			ResultSet courses = courseStm.executeQuery("select * from courses");
			//Populate each course with its corresponding prerequisites
			while(courses.next())
			{
				Course course = this.courseCatalog.searchForCourse(courses.getInt("courseID"));
				String prereqsString = courses.getString("prereq");
				if(prereqsString!=null)
				{
					String prereqs[] = prereqsString.split(",");
					for(String pre : prereqs)
					{
						Course prereqCourse = this.courseCatalog.searchForCourse(pre.split(" ")[0],Integer.parseInt(pre.split(" ")[1]));
						course.addPrereq(prereqCourse);
					}
				}
			}
			courseStm.close();
			courses.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the database for Student information
	 * Populates the student list.
	 */
	public void readStudents()
	{
		this.userList = new ArrayList<User>();
		try 
		{
			//Query every row in users table
			Statement studentStm = this.dbConnection.createStatement();
			ResultSet students = studentStm.executeQuery("select * from users");
			//Create a new student for each row and add to student list
			while(students.next())
			{
				Student newStudent = new Student(students.getString("username"),students.getString("password"),students.getInt("ID"));
				String prevCourses = students.getString("pastCourses");
				if(prevCourses!=null)
				{
					for(String courseID : prevCourses.split(","))
					{
						newStudent.addPrevCourse(this.courseCatalog.searchForCourse(Integer.parseInt(courseID)));
					}
				}
				this.userList.add(newStudent);
			}
			studentStm.close();
			students.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Searches for a student by their name in the list of students.
	 * @param name the name of the student
	 * @return the student with the corresponding name if they are in the student list.
	 */
	public Student findStudent(String name)
	{
		for(User u :this.userList)
		{
			if(u.getUsername().equals(name))
			{
				
				return (Student)u;
			}
		}
		return null;
	}
	
	/**
	 * Reads the database for all course registration information
	 * Populates each student and each course section's registration lists.
	 */
	public void readRegistrations()
	{
		try 
		{
			//Query every row/student in the users table
			Statement studentStm = this.dbConnection.createStatement();
			ResultSet students = studentStm.executeQuery("select * from users");
			//Using the registration field, make registrations for every student and add them to the students and the sections
			while(students.next())
			{
				Student student = findStudent(students.getString("username"));
				String registeredCourses = students.getString("registrations");
				if(registeredCourses!=null)
				{
					for(String courseInfo : registeredCourses.split(","))
					{
						int courseID = Integer.parseInt(courseInfo.split(" ")[0]);
						int sectionNum = Integer.parseInt(courseInfo.split(" ")[1]);
						CourseSection section = this.courseCatalog.searchForCourse(courseID).searchForSection(sectionNum);
						Registration reg = new Registration(student, section);
						student.addRegistration(reg);
						section.addRegistration(reg);
					}
				}
			}
			studentStm.close();
			students.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}
	public ArrayList<User> getUserList() {
		return userList;
	}
	
}
