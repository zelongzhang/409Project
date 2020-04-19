
import java.sql.*;

public class Driver {

	public static void main(String[] args)
	{
		try 
		{
			Connection myConn = DriverManager.getConnection("jdbc:mysql://70.65.104.103:3306/coursecatalog", "User","5c5YDO1CvP5Fx2TK");
			Statement mystm = myConn.createStatement();
			ResultSet myrs = mystm.executeQuery("select * from users");
			while(myrs.next())
			{
				System.out.println(myrs.getString("username")+", "+myrs.getString("password")+"," +myrs.getInt("ID"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
