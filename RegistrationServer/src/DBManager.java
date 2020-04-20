import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
		//readMockCourses();
		readCourses();
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
	public void addCourseToDB(Course course)
	{
		//TODO
	}
	public void removeCourseFromDB(Course course)
	{
		//TODO
	}
	public void addSectionToDB(Course course, CourseSection section)
	{
		//TODO
	}
	public void removeSectionFromDB(Course course, CourseSection section)
	{
		//TODO
	}
	public void addUserToDB(User user)
	{
		//TODO
	}
	public void removeUserFromDB(User user)
	{
		//TODO
	}
	
	public void addRegistrationToDB(Registration registration)
	{
		//TODO
	}
	
	public void removeRegistrationFromDB(Registration registration)
	{
		//TODO
	}
//	public void readMockCourses()
//	{
//		try 
//		{
//			BufferedReader br = new BufferedReader(new FileReader("MockCourses"));
//			String line = br.readLine();
//			while(line!=null)
//			{
//				String content[] =line.split(",");
//				Course newCourse = new Course(content[0], Integer.parseInt(content[1]));
//				newCourse.addSection(new CourseSection(Integer.parseInt(content[2]),Integer.parseInt(content[3])));
//				newCourse.addSection(new CourseSection(Integer.parseInt(content[4]),Integer.parseInt(content[5])));
//				this.courseCatalog.addCourse(newCourse);
//				line = br.readLine();
//			}
//		} 
//		catch (FileNotFoundException e) 
//		{
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
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
					CourseSection newSection = new CourseSection(sections.getInt("sectionNum"),sections.getInt("capacity"));
					newCourse.addSection(newSection);
				}
				this.courseCatalog.addCourse(newCourse);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
//	public void readMockStudents()
//	{
//		try 
//		{
//			BufferedReader br = new BufferedReader(new FileReader("MockStudents"));
//			String line = br.readLine();
//			while(line!=null)
//			{
//				String content[] = line.split(",");
//				Student newStudent = new Student(content[0],content[1], Integer.parseInt(content[2]));
//				this.userList.add(newStudent);
//				line = br.readLine();
//			}
//		} 
//		catch (FileNotFoundException e) 
//		{
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
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
