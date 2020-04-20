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
 * @
 */
public class DBManager 
{
	private Connection dbConnection;
	private CourseCatalog courseCatalog;
	private ArrayList<User> userList;
	
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
	public String toString()
	{
		String s = this.courseCatalog.toString()+"\n";
		for(User user: userList)
		{
			s+=user.toString()+"\n";
		}
		return s;
	}
	
	public void updateCatalog()
	{
		readCourses();
		readPrereqs();
		System.out.println("finished updating catalog");
	}
	
	public void updateUsers()
	{
		readStudents();
		System.out.println("finished updating users");
	}
	
	public void updateRegistrations() 
	{
		readRegistrations();
		System.out.println("finished updating registrations");
	}

	
	public void addRegistrationToDB(Registration registration)
	{
		Student student = registration.getStudent();
		CourseSection section = registration.getCourseSection();
		try 
		{
			Statement lookForStudent = this.dbConnection.createStatement();
			ResultSet studentRow = lookForStudent.executeQuery("SELECT * FROM users WHERE ID = "+student.getUserID());
			studentRow.next();
			String newRegistrationString = studentRow.getString("registrations")+","+section.getCourse().getCourseID()+" "+section.getSectionNum();
			String updateStudents = "UPDATE users "
					+" SET registrations = '"+newRegistrationString+"'"
					+ "WHERE ID = "+studentRow.getInt("ID");
			Statement updateRegistrationStudent = this.dbConnection.createStatement();
			updateRegistrationStudent.executeUpdate(updateStudents);
			studentRow.close();
			lookForStudent.close();
			updateRegistrationStudent.close();
			
			Statement lookForSection = this.dbConnection.createStatement();
			ResultSet sectionRow = lookForSection.executeQuery("SELECT * FROM sections WHERE courseID ="+section.getCourse().getCourseID()+" AND sectionNum = "+section.getSectionNum());
			sectionRow.next();
			String newStudentListString = sectionRow.getString("registrations")+","+student.getUsername();
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
	
	public void removeRegistrationFromDB(Registration registration)
	{
		//TODO
	}

	public void readCourses()
	{
		this.courseCatalog = new CourseCatalog();
		try 
		{
			Statement courseStm = this.dbConnection.createStatement();
			ResultSet courses = courseStm.executeQuery("select * from courses");
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
	public void readPrereqs()
	{
		try 
		{
			Statement courseStm = this.dbConnection.createStatement();
			ResultSet courses = courseStm.executeQuery("select * from courses");
			while(courses.next())
			{
				Course course = this.courseCatalog.searchForCourse(courses.getInt("courseID"));
				String prereqsString = courses.getString("prereq");
				if(prereqsString!=null)
				{
					String prereqs[] = prereqsString.split(",");
					for(String pre : prereqs)
					{
						System.out.println(pre);
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
	
	public void readStudents()
	{
		this.userList = new ArrayList<User>();
		try 
		{
			Statement studentStm = this.dbConnection.createStatement();
			ResultSet students = studentStm.executeQuery("select * from users");
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
	public void readRegistrations()
	{
		try 
		{
			Statement studentStm = this.dbConnection.createStatement();
			ResultSet students = studentStm.executeQuery("select * from users");
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
