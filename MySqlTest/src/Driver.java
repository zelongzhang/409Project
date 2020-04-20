
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.Random;

public class Driver {

	public static void main(String[] args)
	{
		try 
		{
			Connection myConn = DriverManager.getConnection("jdbc:mysql://70.65.104.103:3306/coursecatalog", "User","5c5YDO1CvP5Fx2TK");
			
			
//			BufferedReader names = new BufferedReader(new FileReader("names.txt"));
//			int id = 22;
//			String name = names.readLine();
//			while(name != null)
//			{
//				String sql = "INSERT INTO users "
//							+" (username, password, ID)"
//							+" values ('"+name+"', '" +name.split(" ")[0].toLowerCase()+"1234"+"', '" +id+"') ";
//
//				System.out.println(sql);
//				mystm.executeUpdate(sql);
//				name = names.readLine();
//				id++;
//			}
			
			
//			Statement mystm = myConn.createStatement();
//			ResultSet students = mystm.executeQuery("select * from users");
//			Random rand = new Random();
//			String firstyear = "CHEM 209,MATH 211,MATH 275,ENGG 200,ENGG 233,ENGG 201,PHYS 259,MATH 277,ENGG 225,ENGG 202";
//			
//			while(students.next())
//			{
//				String sql = "UPDATE users "
//						+" SET registrations = '0 "+(rand.nextInt(2)+1)+",1 "+(rand.nextInt(2)+1)+",2 "+(rand.nextInt(2)+1)+",3 "+(rand.nextInt(2)+1)+",4 "+(rand.nextInt(2)+1)+"'"
//						+ "WHERE ID = "+students.getInt("ID");
//				System.out.println(sql);
//				Statement update = myConn.createStatement();
//				update.executeUpdate(sql);
//			}
			
//			Statement sec = myConn.createStatement();
//			ResultSet sections = sec.executeQuery("select * from sections");
//			
//			while(sections.next())
//			{
//				String names = "";
//				Statement user = myConn.createStatement();
//				ResultSet students = user.executeQuery("select * from users");
//				while(students.next())
//				{
//					String allCourses[] = students.getString("registrations").split(",");
//					for(String course: allCourses)
//					{
//						int courseID = Integer.parseInt(course.split(" ")[0]);
//						int sectionNum = Integer.parseInt(course.split(" ")[1]);
//						if(courseID == sections.getInt("courseID") && sectionNum == sections.getInt("sectionNum"))
//						{
//							if(names.isBlank())
//							{
//								names += students.getString("username");
//							}
//							else
//							{
//								names += ","+students.getString("username");
//							}
//							
//						}
//					}
//				}
//				String sql = "UPDATE sections "
//						+" SET registrations = '"+names+"'"
//						+ "WHERE sectionID = "+sections.getInt("sectionID");
//				Statement update = myConn.createStatement();
//				update.executeUpdate(sql);
//			}
			
//			Statement sec = myConn.createStatement();
//			ResultSet sections = sec.executeQuery("select * from sections");
//			while(sections.next())
//			{
//				String students[] = sections.getString("registrations").split(",");
//				System.out.println(students.length);
//			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
