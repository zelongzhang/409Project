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

/**
 * Implementation of a handler for a single client
 * This thread handles all communications and requests from the client.
 * @author Maaz Khurram
 * @author Kevin Zhang
 * @author Saksham Nanda
 * @version 1.0
 * @since Apr 20, 2020
 */
public class RegistrationThread implements Runnable
{
	
	private Socket clientSocket;
	private DBManager dbManager;
	private Student student;
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	private int maxCourseLoad = 6;
	private boolean running = true;
	
	/**
	 * Constructor for the thread, needs the socket connecting client to server as well as access to the database in the form of a DBManager
	 * @param clientsocket The socket connecting this client to the server.
	 * @param dbmanager The database manager responsible for communication with the database.
	 */
	public RegistrationThread(Socket clientsocket, DBManager dbmanager)
	{

		this.clientSocket = clientsocket;
		System.out.println("Accepted new connection..."+clientSocket.getInetAddress()+" on port "+this.clientSocket.getPort());
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
				//Handles log in request from the client
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
						System.out.println(this.student.getUsername()+" has logged in.");
					}
					toClient.flush();
					break;
					
				//Handles full catalog requests from the client
				case "CatalogRequest":
					System.out.println("Sending full catalog to "+this.student.getUsername());
					toClient.writeObject(new CatalogDataMessage(this.dbManager.getCourseCatalog().toSendFormat()));
					toClient.flush();
					break;
					
				//Handles student course list requests from the client
				case "ViewStudentCourseRequest":
					System.out.println("Sending student course list to "+this.student.getUsername());
					toClient.writeObject(new ViewStudentCoursesDataMessage("ViewStudentData", this.student.coursesToSendFormat()));
					toClient.flush();
					break;
					
				//Handles a registration request from the client
				case "AddRegistrationRequest":
					String courseNameToAdd = ((AddRegistrationMessage)message).getCourseName();
					int courseNumToAdd = ((AddRegistrationMessage)message).getCourseNum();
					int sectionNumToAdd = ((AddRegistrationMessage)message).getSectionNum();
					CourseSection sectionToAdd = this.dbManager.getCourseCatalog().searchForCourse(courseNameToAdd, courseNumToAdd).searchForSection(sectionNumToAdd);	
					if(this.student.getRegList().size()>=maxCourseLoad)
					{
						System.out.println("Cannot register "+this.student + " in " + sectionToAdd + " because course load is full.");
						toClient.writeObject(new ResponseMessage("FAIL","Cannot register for more than max course load(6 courses)."));
					}
					else if(this.student.isAlreadyRegistered(sectionToAdd))
					{
						System.out.println("Cannot register "+this.student + " in " + sectionToAdd + " because student is already registered in this course.");
						toClient.writeObject(new ResponseMessage("FAIL","Cannot register in the same course twice."));
					}
					else if(!this.student.getPastCourses().containsAll(sectionToAdd.getCourse().getPrereq()))
					{
						System.out.println("Cannot register "+this.student + " in " + sectionToAdd + " because student fails to meet prerequisites.");
						toClient.writeObject(new ResponseMessage("FAIL","Prereqs are not met."));
					}
					else
					{
						System.out.println("Registering "+this.student + " in " + sectionToAdd);
						Registration regToAdd = new Registration(this.student,sectionToAdd);
						this.student.addRegistration(regToAdd);
						sectionToAdd.addRegistration(regToAdd);
						this.dbManager.addRegistrationToDB(regToAdd);
						toClient.writeObject(new ResponseMessage("SUCCESS",""));
					}
					toClient.flush();
					break;
					
				//Handles a remove registration request from the client
				case "RemoveRegistrationRequest":
					String courseNameToRemove = ((RemoveRegistrationMessage)message).getCourseName();
					int courseNumToRemove = ((RemoveRegistrationMessage)message).getCourseNum();
					int sectionNumToRemove = ((RemoveRegistrationMessage)message).getSectionNum();
					CourseSection sectionToRemove = this.dbManager.getCourseCatalog().searchForCourse(courseNameToRemove, courseNumToRemove).searchForSection(sectionNumToRemove);
					Registration regToRemove = this.student.lookForRegistration(sectionToRemove);
					if(regToRemove == null)
					{
						System.out.println("Cannot remove "+this.student + " from " + sectionToRemove + " because student is not in that section.");
						toClient.writeObject(new ResponseMessage("FAIL","Cannot remove a course without registering in it."));
					}
					else
					{
						System.out.println("Removing "+this.student + " from " + sectionToRemove);
						this.student.removeRegistration(regToRemove);
						sectionToRemove.removeRegistration(regToRemove);
						this.dbManager.removeRegistrationFromDB(regToRemove);
						toClient.writeObject(new ResponseMessage("SUCCESS",""));
					}
					toClient.flush();
					break;
					
				//Handles a search catalog request from the client
				case "SearchRequest":
					String courseName = ((SearchCatalogRequestMessage)message).getCourseName();
					int courseNum = ((SearchCatalogRequestMessage)message).getCourseNum();
					Course course = this.dbManager.getCourseCatalog().searchForCourse(courseName, courseNum);
					System.out.println(course);
					if(course == null)
					{
						System.out.println("Could not find Course queried by "+this.student.getUsername());
						toClient.writeObject(new SearchCatalogDataMessage(""));
					}
					else
					{
						System.out.println("Found Course queried by "+this.student.getUsername()+", sending results...");
						toClient.writeObject(new SearchCatalogDataMessage(course.toSendFormat()));
					}
					toClient.flush();
					break;
					
				//Handles a quit from the client.
				case "Quit":
					System.out.println(this.student.getUsername()+" has left the registration app.");
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
