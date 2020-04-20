import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import message.AddRegistrationMessage;
import message.CatalogDataMessage;
import message.LoginRequestMessage;
import message.Message;
import message.RemoveRegistrationMessage;
import message.ResponseMessage;
import message.SearchCatalogDataMessage;
import message.SearchCatalogRequestMessage;
import message.ViewStudentCoursesDataMessage;


public class RegistrationThread implements Runnable
{
	
	private Socket clientSocket;
	private DBManager dbManager;
	private Student student;
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	private int maxCourseLoad = 6;
	private boolean running = true;
	
	public RegistrationThread(Socket clientsocket, DBManager dbmanager)
	{

		this.clientSocket = clientsocket;
		System.out.println("Accepted new connection..."+clientSocket.getInetAddress()+"on port "+this.clientSocket.getPort());
		this.dbManager = dbmanager;
		try 
		{
			this.toClient = new ObjectOutputStream(this.clientSocket.getOutputStream());
			this.toClient.flush();
			this.fromClient = new ObjectInputStream(this.clientSocket.getInputStream());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() 
	{
		while(running)
		{
			try 
			{
				Message message = (Message) fromClient.readObject();
				switch(message.getInstruction())
				{
					
				case "LoginRequest":
					String username = ((LoginRequestMessage)message).getUsername();
					String password = ((LoginRequestMessage)message).getPassword();
					this.student = this.dbManager.findStudent(username);
					if(this.student == null)
					{
						toClient.writeObject(new ResponseMessage("FAIL", "The user name is not registered."));
					}
					else if(!this.student.getPassword().equals(password))
					{
						toClient.writeObject(new ResponseMessage("FAIL", "Incorrect password."));
					}
					else
					{
						toClient.writeObject(new ResponseMessage("SUCCESS" , ""));
					}
					toClient.flush();
					break;
				case "CatalogRequest":
					toClient.writeObject(new CatalogDataMessage(this.dbManager.getCourseCatalog().toSendFormat()));
					toClient.flush();
					break;
				case "ViewStudentCourseRequest":
					toClient.writeObject(new ViewStudentCoursesDataMessage("ViewStudentData", this.student.coursesToSendFormat()));
					toClient.flush();
					break;
				case "AddRegistrationRequest":
					String courseNameToAdd = ((AddRegistrationMessage)message).getCourseName();
					int courseNumToAdd = ((AddRegistrationMessage)message).getCourseNum();
					int sectionNumToAdd = ((AddRegistrationMessage)message).getSectionNum();
					CourseSection sectionToAdd = this.dbManager.getCourseCatalog().searchForCourse(courseNameToAdd, courseNumToAdd).searchForSection(sectionNumToAdd);
					System.out.println("student has "+this.student.getRegList().size()+" courses");
					for(Registration reg : this.student.getRegList())
					{
						System.out.println(reg);
					}
					System.out.println(this.student.getPastCourses().containsAll(sectionToAdd.getCourse().getPrereq()));
					for(Course c:this.student.getPastCourses())
					{
						System.out.println(c);
					}
					System.out.println();System.out.println();
					for(Course c:sectionToAdd.getCourse().getPrereq())
					{
						System.out.println(c);
					}
					if(this.student.getRegList().size()>=maxCourseLoad)
					{
						toClient.writeObject(new ResponseMessage("FAIL","Cannot register for more than max course load(6 courses)."));
					}
					else if(this.student.isAlreadyRegistered(sectionToAdd))
					{
						toClient.writeObject(new ResponseMessage("FAIL","Cannot register in the same course twice."));
					}
					else if(!this.student.getPastCourses().containsAll(sectionToAdd.getCourse().getPrereq()))
					{
						toClient.writeObject(new ResponseMessage("FAIL","Prereqs are not met."));
					}
					else
					{
						Registration regToAdd = new Registration(this.student,sectionToAdd);
						this.student.addRegistration(regToAdd);
						sectionToAdd.addRegistration(regToAdd);
						this.dbManager.addRegistrationToDB(regToAdd);
						toClient.writeObject(new ResponseMessage("SUCCESS",""));
					}
					toClient.flush();
					break;
				case "RemoveRegistrationRequest":
					System.out.println("remove start");
					String courseNameToRemove = ((RemoveRegistrationMessage)message).getCourseName();
					int courseNumToRemove = ((RemoveRegistrationMessage)message).getCourseNum();
					int sectionNumToRemove = ((RemoveRegistrationMessage)message).getSectionNum();
					System.out.println(courseNameToRemove+courseNumToRemove+" "+sectionNumToRemove);
					CourseSection sectionToRemove = this.dbManager.getCourseCatalog().searchForCourse(courseNameToRemove, courseNumToRemove).searchForSection(sectionNumToRemove);
					Registration regToRemove = this.student.lookForRegistration(sectionToRemove);
					if(regToRemove == null)
					{
						toClient.writeObject(new ResponseMessage("FAIL","Cannot remove a course without registering in it."));
					}
					else
					{
						this.student.removeRegistration(regToRemove);
						sectionToRemove.removeRegistration(regToRemove);
						this.dbManager.removeRegistrationFromDB(regToRemove);
						toClient.writeObject(new ResponseMessage("SUCCESS",""));
					}
					toClient.flush();
					System.out.println("end remove");
					break;
				case "SearchRequest":
					String courseName = ((SearchCatalogRequestMessage)message).getCourseName();
					int courseNum = ((SearchCatalogRequestMessage)message).getCourseNum();
					Course course = this.dbManager.getCourseCatalog().searchForCourse(courseName, courseNum);
					System.out.println(course);
					if(course == null)
					{
						toClient.writeObject(new SearchCatalogDataMessage(""));
					}
					else
					{
						toClient.writeObject(new SearchCatalogDataMessage(course.toSendFormat()));
					}
					toClient.flush();
					break;
				case "Quit":
					this.running = false;
					break;
				}
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
				return;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				return;
			}
		}
	}


}
