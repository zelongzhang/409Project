import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
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
		readMockCourses();
		System.out.println("finished updating catalog");
	}
	
	public void updateUsers()
	{
		readMockStudents();
		System.out.println("finished updating users");
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
	public void readMockCourses()
	{
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader("MockCourses"));
			String line = br.readLine();
			while(line!=null)
			{
				String content[] =line.split(",");
				Course newCourse = new Course(content[0], Integer.parseInt(content[1]));
				newCourse.addSection(new CourseSection(Integer.parseInt(content[2]),Integer.parseInt(content[3])));
				newCourse.addSection(new CourseSection(Integer.parseInt(content[4]),Integer.parseInt(content[5])));
				this.courseCatalog.addCourse(newCourse);
				line = br.readLine();
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readMockStudents()
	{
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader("MockStudents"));
			String line = br.readLine();
			while(line!=null)
			{
				String content[] = line.split(",");
				Student newStudent = new Student(content[0],content[1], Integer.parseInt(content[2]));
				this.userList.add(newStudent);
				line = br.readLine();
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
