
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.text.BadLocationException;

import message.*;
import java.util.*;

public class ClientRegistrationThread implements Runnable {

	private Socket socket;
	private MainFrame mf;
	private LoginFrame lf;
	private CourseSectionFrame csf;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	private boolean running = true;
	
	private String courseName,courseNumber, courseSection;
	private int targetOperation=-1; // -1 initially , 1 for add course, 2 for remove
	private MouseListener ml;

	
	
	public ClientRegistrationThread(Socket socket, MainFrame mainframe, LoginFrame loginFrame) {
		this.socket = socket;
		this.mf = mainframe;
		this.lf = loginFrame;
		try {
			this.toServer = new ObjectOutputStream(this.socket.getOutputStream());
			this.toServer.flush();
			this.fromServer = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		createLoginListener();
		createLoginExitListener();

	}

	// ************************************* ACTION LISTENERS ************************************************

	
	private void createSectionFrameTextAreaListener()
	{
		
		csf.getSections().addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent evt) {
	        	tAreaSectionFrameMousePressed(evt);
	        }
	     });
		
		
	}
	
	

	
	
	   private void tAreaSectionFrameMousePressed(MouseEvent evt) {
		      try {
		         int offset = csf.getSections().viewToModel(evt.getPoint());
		         Rectangle rect = csf.getSections().modelToView(offset);
		         
		 
		         int startRow = csf.getSections().viewToModel(new Point(0, rect.y));
		         int endRow = csf.getSections().viewToModel(new Point(csf.getSections().getWidth(), rect.y));
		         
		         String selectedSectionString = csf.getSections().getText(startRow, endRow-startRow);

		         
		         if (!selectedSectionString.isEmpty()) 
		         {
			         csf.setCourseSection(selectedSectionString.substring(10, 11));
			         System.out.println("Course Section= "+ csf.getCourseSection());
			         csf.getSections().select(startRow, endRow);
		         }

		         
		      } catch (BadLocationException e) {
		         e.printStackTrace();
		      }
		      
		   }
	
	
	
	private void createTextAreaListener()
	{
		ml = new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent evt) {
	           tAreaMousePressed(evt);
	        }
		};
	        
		mf.getRecords().addMouseListener(ml);
		
		
	}
	
	

	
	   private void tAreaMousePressed(MouseEvent evt) {
		      try {
		         int offset = mf.getRecords().viewToModel(evt.getPoint());
		         Rectangle rect = mf.getRecords().modelToView(offset);
		         
		 
		         int startRow = mf.getRecords().viewToModel(new Point(0, rect.y));
		         int endRow = mf.getRecords().viewToModel(new Point(mf.getRecords().getWidth(), rect.y));
		         
		         String selectedCourseString = mf.getRecords().getText(startRow, endRow-startRow);
		          
		         //System.out.printf("Selected Offsets: [%d, %d]%n", startRow, endRow);
		         //System.out.println(	mf.getRecords().getText(startRow, endRow-startRow)	);
		         
		         if (!selectedCourseString.isEmpty()) 
		         {
		        	 mf.getRecords().select(startRow, endRow);
			         courseName = selectedCourseString.substring(0, 4);
			         System.out.println("Course Name= "+ courseName);
			         
			         courseNumber = selectedCourseString.substring(4, 7);
			         System.out.println("Course Number= "+ courseNumber);
			         
			         String[] StringsOfCourseSections = selectedCourseString.split(Pattern.quote("["));  // ignore 1st string and also add "[" character to every other string
				        
			         
			         //for(String i : StringsOfCourseSections) System.out.println(i);
			         
		        	 
				     csf = new CourseSectionFrame(StringsOfCourseSections);
				     createSectionFrameTextAreaListener();
				     createSectionFrameListeners();
				     
				     
				         
				          
				     
				         
			         
		         }
		         

		         

		         
		      } catch (BadLocationException e) {
		         e.printStackTrace();
		      }
		   }
	
	
	
	
	
	
	
	
	private void createAddCourseListener() {
			mf.getAddCourse().addActionListener((ActionEvent e) -> {
				
				if (targetOperation == 2)
				{
					mf.getRecords().removeMouseListener(ml);
				}
				//else if (mf.getRecords().getMouseListeners().length != 0 ){}
				
				
				if (targetOperation == 1){
					ViewAllCatalogueCourses();
					JOptionPane.showMessageDialog(null,"Please choose the course you want to take from the main window.");  
				}
				else{

					targetOperation = 1;
					
//					if (mf.getRecords().getText().trim().length() == 0) 
					ViewAllCatalogueCourses();
					JOptionPane.showMessageDialog(null,"Please choose the course you want to take from the main window.");  
					createTextAreaListener();

				}

			});
		
		}

	private void AddACourse() {
		System.out.println("*******************Final values to ADD************************");
		System.out.println("Course Name :" + courseName);
		System.out.println("Course Number :" + courseNumber);
		System.out.println("Course Section :" + courseSection);
		System.out.println("*****************************************************************");

		Message AddCourseRequestMessage = new AddRegistrationMessage(courseName, Integer.parseInt(courseNumber), Integer.parseInt(courseSection));


		 try
		 {
		 this.toServer.writeObject(AddCourseRequestMessage);
		 this.toServer.flush();
		 ResponseMessage response = (ResponseMessage)this.fromServer.readObject();
		
		 if (response.getInstruction().equals("SUCCESS"))
		 {
		 JOptionPane.showMessageDialog(null,"Success! Course added.");
		 }
		 else if (response.getInstruction().equals("FAIL"))
		 {
		 mf.showError((String)response.getFailureMessage());
		 }
		 else System.err.println("Invalid choice by Server side");
		
		 }
		 catch (IOException f) {
		 f.printStackTrace();
		 } catch (ClassNotFoundException f) {
		 f.printStackTrace();
		 }
		
	}

	private void createViewCourseCatalogueListener() {
		// if(SHOW ALL COURSES BUTTON PUSHED)
		mf.getViewCC().addActionListener((ActionEvent e) -> {
			
			ViewAllCatalogueCourses();
			
		});
	
	}

	private void ViewAllCatalogueCourses() {
		System.out.println("View Course Catalogue");
		mf.getRecords().setText(null);
		Message catalogRequest = new CatalogRequestMessage();
		try {
			this.toServer.writeObject(catalogRequest);
			this.toServer.flush();
			CatalogDataMessage catalogData = (CatalogDataMessage) this.fromServer.readObject();
			ArrayList<String> data = catalogData.getCatalog();
			// System.out.println(data);

			if (data.size() == 0) {
				mf.showError("Course Catalogue is empty!");
			} else {

				for (String i : data) {
					// System.out.println(i);
					String str = new String();
					String[] contents = i.split(",");
					str = contents[0] + contents[1] + ": ";
					System.out.println(str);
					if ((contents.length-2)%3 == 0) {
						for (int k = 2; k < contents.length; k++) {
							str += "[Section: " + contents[k] + " ("+contents[++k] +"/" + contents[++k] + ")] ";
						}
					} else
						System.err.println("Wrong size of message****");

					str += "\n";
					mf.getRecords().append(str);

				}

			}

		} catch (IOException f) {
			f.printStackTrace();
		} catch (ClassNotFoundException f) {
			f.printStackTrace();
		}

		
	}

	private void createRemoveCourseListener() {
			mf.getRemoveCourse().addActionListener((ActionEvent e) -> {
				
				if (targetOperation == 1)
				{
					mf.getRecords().removeMouseListener(ml);
				}
				
				if (targetOperation == 2){
					ViewStudentCourses();
					JOptionPane.showMessageDialog(null,"Please choose the course you want to remove from the main window.");  
				}
				else{
					targetOperation = 2;
					ViewStudentCourses();
					JOptionPane.showMessageDialog(null,"Please choose the course you want to remove from the main window.");  
					createTextAreaListener();
				}
				

	
	
			});
	
		}



	private void RemoveACourse() {
		System.out.println("*******************Final values to remove************************");
		System.out.println("Course Name :" + courseName);
		System.out.println("Course Number :" + courseNumber);
		System.out.println("Course Section :" + courseSection);
		System.out.println("*****************************************************************");
	
		Message RemoveCourseRequest = new RemoveRegistrationMessage(courseName, Integer.parseInt(courseNumber), Integer.parseInt(courseSection));
		
	
		 try
		 {
		 this.toServer.writeObject(RemoveCourseRequest);
		 this.toServer.flush();
		 ResponseMessage response = (ResponseMessage)this.fromServer.readObject();
		
		 if (response.getInstruction().equals("SUCCESS"))
		 {
		 JOptionPane.showMessageDialog(null,"Success! Course Removed.");
		 }
		 else if (response.getInstruction().equals("FAIL"))
		 {
		 mf.showError((String)response.getFailureMessage());
		 }
		 else System.err.println("Invalid choice by Server side");
		
		 }
		 catch (IOException f) {
		 f.printStackTrace();
		 } catch (ClassNotFoundException f) {
		 f.printStackTrace();
		 }
		
		
	}

	private void createViewStudentCoursesListener() {
	
		mf.getViewStudentCourses().addActionListener((ActionEvent e) -> {
	
			ViewStudentCourses();
		});
	
	}



	private void ViewStudentCourses() {
		System.out.println("View Student Course ");
		mf.getRecords().setText(null);
		Message ViewStudentCoursesRequest = new ViewStudentCoursesRequestMessage();
	
		try {
			this.toServer.writeObject(ViewStudentCoursesRequest);
			this.toServer.flush();
			ViewStudentCoursesDataMessage ViewStudentCoursesData = (ViewStudentCoursesDataMessage) this.fromServer.readObject();
			ArrayList<String> data = ViewStudentCoursesData.getCourseList();
			// System.out.println(data);
	
			if (data.size() == 0) {
				mf.showError("Student is not enrolled in any courses!");
			} else {
				for (String i : data) {
					// System.out.println(i);
					String str = new String();
					String[] contents = i.split(",");
					str = contents[0] + contents[1] + ": ";
					System.out.println(str);
					if (contents.length == 5) {
						for (int k = 2; k < contents.length; k++) {
							str += "[Section: " + contents[k] + " ("+contents[++k] +"/" + contents[++k] + ")] ";
						}
					} else
						System.err.println("Wrong size of message****"+contents.length);
	
					str += "\n";
					mf.getRecords().append(str);
	
				}
	
			}
	
		} catch (IOException f) {
			f.printStackTrace();
		} catch (ClassNotFoundException f) {
			f.printStackTrace();
		}
		
	}

	private void createSearchCatalogueListener() {
	
		mf.getSearchCC().addActionListener((ActionEvent e) -> {
	
			JTextField CourseName = new JTextField("ENEL", 5);
			JTextField CourseNumber = new JTextField("343", 5);
			JTextField CourseSection = new JTextField("1", 5);
	
			JPanel CourseInfoPanel = new JPanel();
			CourseInfoPanel.add(new JLabel("Course Name :"));
			CourseInfoPanel.add(CourseName);
			CourseInfoPanel.add(Box.createHorizontalStrut(15)); // a spacer
			CourseInfoPanel.add(new JLabel("Course Number :"));
			CourseInfoPanel.add(CourseNumber);
			CourseInfoPanel.add(Box.createHorizontalStrut(15)); // a spacer
			CourseInfoPanel.add(new JLabel("Course Section :"));
			CourseInfoPanel.add(CourseSection);
	
			int result = JOptionPane.showConfirmDialog(null, CourseInfoPanel, "Please enter course details",
					JOptionPane.OK_CANCEL_OPTION);
	
			if (result == JOptionPane.OK_OPTION) {
				System.out.println("Course Name :" + CourseName.getText());
				System.out.println("Course Number :" + CourseNumber.getText());
				System.out.println("Course Section :" + CourseSection.getText());
	
				Message searchCatalogueRequest = new SearchCatalogRequestMessage(CourseName.getText(), Integer.parseInt(CourseNumber.getText()), Integer.parseInt(CourseSection.getText()));
				
	
				try {
					this.toServer.writeObject(searchCatalogueRequest);
					this.toServer.flush();
					SearchCatalogDataMessage searchCatalogueData = (SearchCatalogDataMessage) this.fromServer.readObject();	
					String courses = searchCatalogueData.getSearchResult();
					 System.out.println(courses);
	
					if (courses.isEmpty()) {
						mf.showError("Course does not exist!");
					} else {
	

						String str = new String();
						String[] contents = courses.split(",");
						str = contents[0] + contents[1] + ": ";
						System.out.println(str);
						if ((contents.length-2) % 3 == 0) {
							for (int k = 2; k < contents.length; k++) {
								str += "[Section: " + contents[k] + " (" + contents[++k] + "/" + contents[++k] + ")] ";
							}
						} else
							System.err.println("Wrong size of message****");

						str += "\n";
						mf.getRecords().setText(null);
						mf.getRecords().append(str);
						
						System.out.println("targetOperation =" + targetOperation);
						System.out.println(mf.getRecords().getMouseListeners().length);
						if ((targetOperation == 2) || (targetOperation == -1))
							{
								System.out.println("Adding a add course listener due to search course functionality");
								
								if (targetOperation == 2)
								{
									mf.getRecords().removeMouseListener(ml);
								}
								//else if (mf.getRecords().getMouseListeners().length != 0 ){}
								
								targetOperation = 1;
								
								
								JOptionPane.showMessageDialog(null,"ADDING THE SEARCHED COURSE");  
								createTextAreaListener();
								
							}
	
						
	
					}
	
				} catch (IOException f) {
					f.printStackTrace();
				} catch (ClassNotFoundException f) {
					f.printStackTrace();
				}
	
			}
	
		});
	
	}

	private void createLoginListener() {
		lf.getLogin().addActionListener((ActionEvent e) ->
	
		{
			System.out.println("Login");
			Message loginRequest = new LoginRequestMessage(lf.getUsernameField(), lf.getPasswordField());
	
	
			 try
			 {
			 this.toServer.writeObject(loginRequest);
			 this.toServer.flush();
			 ResponseMessage response = (ResponseMessage)this.fromServer.readObject();
			
			 if (response.getInstruction().equals("SUCCESS"))
			 {
				 lf.dispose();
				 createMainFrameAndListeners();
			 }
			 else if (response.getInstruction().equals("FAIL"))
			 {
			 lf.showError();
			 }
			
			 }
			 catch (IOException f)
			 {
			 f.printStackTrace();
			 }
			 catch (ClassNotFoundException f)
			 {
			 f.printStackTrace();
			 }
	
		});
	
	}

	private void createMainFrameExitListener()
	{
		mf.getExit().addActionListener( (ActionEvent e) -> {
			
			mf.dispose();
		});
	}

	private void createSectionFrameListeners() {
		csf.getOK().addActionListener((ActionEvent e) ->
		{
			courseSection = csf.getCourseSection();
			csf.dispose();
			
			if(targetOperation==1) AddACourse();
			else if (targetOperation==2) RemoveACourse();
			else if (targetOperation ==-1) System.err.println("Problem is add or remove decision");
			
		});
		
		
		csf.getExit().addActionListener((ActionEvent e) ->
		{
			this.courseName =null;
			this.courseNumber = null;
			this.courseSection = null;
			csf.dispose();
			
		});
	}

	private void createLoginExitListener() {
		lf.getExit().addActionListener((ActionEvent e) -> {
			lf.dispose();
		});
	}

	private void createMainFrameAndListeners() {
		this.mf = new MainFrame("Schedule Builder");
		mf.setSize(1000, 563);
		mf.setVisible(true);
		createViewCourseCatalogueListener();
		createSearchCatalogueListener();
		createViewStudentCoursesListener();
		createAddCourseListener();
		createRemoveCourseListener();
		createMainFrameExitListener();
		
	}

}
