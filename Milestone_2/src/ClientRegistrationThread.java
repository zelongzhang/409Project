import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;

import message.*;
import java.util.*;

public class ClientRegistrationThread implements Runnable {

	private Socket socket;
	private MainFrame mf;
	private LoginFrame lf;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	private boolean running = true;

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
	
	// ************************************* ACTION LISTENERS************************************************

	private void createRemoveCourseListener() {
		mf.getRemoveCourse().addActionListener( (ActionEvent e) -> {
			
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

				Message RemoveCourseRequestMessage = new RemoveCourseRequestMessage();
				Map<String, String> data = new HashMap<String, String>();
				data.put("Course Name :", CourseName.getText());
				data.put("Course Number :", CourseNumber.getText());
				data.put("Course Section :", CourseSection.getText());
				RemoveCourseRequestMessage.setData(data);
				
				
				
//				 try
//				 {
//				 this.toServer.writeObject(RemoveCourseRequestMessage);
//				 this.toServer.flush();
//				 RemoveCourseDataMessage response = (RemoveCourseDataMessage)
//				 this.fromServer.readObject();
//				
//				 if (response.getInstruction().equals("PASS"))
//				 {
//				 JOptionPane.showMessageDialog(null,"Success! Course Removed.");
//				 }
//				 else if (response.getInstruction().equals("FAIL"))
//				 {
//				 mf.showError((String)response.getData().get("Failure Reason"));
//				 }
//				 else System.err.println("Invalid choice by Server side");
//				
//				 }
//				 catch (IOException f) {
//				 f.printStackTrace();
//				 } catch (ClassNotFoundException f) {
//				 f.printStackTrace();
//				 }
			}

			}	);
	
	}



	private void createAddCourseListener() {
		mf.getAddCourse().addActionListener((ActionEvent e) -> {

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

				Message AddCourseRequestMessage = new AddCourseRequestMessage();
				Map<String, String> data = new HashMap<String, String>();
				data.put("Course Name :", CourseName.getText());
				data.put("Course Number :", CourseNumber.getText());
				data.put("Course Section :", CourseSection.getText());
				AddCourseRequestMessage.setData(data);

				// try
				// {
				// this.toServer.writeObject(AddCourseRequestMessage);
				// this.toServer.flush();
				// AddCourseDataMessage response = (AddCourseDataMessage)
				// this.fromServer.readObject();
				//
				// if (response.getInstruction().equals("PASS"))
				// {
				// JOptionPane.showMessageDialog(null,"Success! Course added.");
				// }
				// else if (response.getInstruction().equals("FAIL"))
				// {
				// mf.showError((String)response.getData().get("Failure
				// Reason"));
				// }
				// else System.err.println("Invalid choice by Server side");
				//
				// }
				// catch (IOException f) {
				// f.printStackTrace();
				// } catch (ClassNotFoundException f) {
				// f.printStackTrace();
				// }

			}

		});

	}

	private void createLoginListener() {
		lf.getLogin().addActionListener((ActionEvent e) ->

		{
			System.out.println("Login");
			Message loginRequest = new LoginRequestMessage();
			Map<String, String> data = new HashMap<String, String>();
			data.put("Username", lf.getUsernameField());
			data.put("Password", lf.getPasswordField());
			loginRequest.setData(data);

			// try
			// {
			// this.toServer.writeObject(loginRequest);
			// this.toServer.flush();
			// LoginDataMessage response = (LoginDataMessage)
			// this.fromServer.readObject();
			//
			// if (response.getInstruction().equals("TRUE"))
			// {
			lf.dispose();
			createMainFrameAndListeners();

			// }
			//
			//
			// else if (response.getInstruction().equals("FALSE"))
			// {
			// lf.showError();
			// }
			//
			// }
			// catch (IOException f)
			// {
			// f.printStackTrace();
			// }
			// catch (ClassNotFoundException f)
			// {
			// f.printStackTrace();
			// }

		});

	}

	private void createLoginExitListener() {
		lf.getExit().addActionListener((ActionEvent e) -> {
			lf.dispose();
		});
	}

	private void createViewCourseCatalogueListener() {
		// if(SHOW ALL COURSES BUTTON PUSHED)
		mf.getViewCC().addActionListener((ActionEvent e) -> {
			System.out.println("View Course Catalogue");
			mf.getRecords().setText(null);
			Message catalogRequest = new CatalogRequestMessage();
			try {
				this.toServer.writeObject(catalogRequest);
				this.toServer.flush();
				CatalogDataMessage catalogData = (CatalogDataMessage) this.fromServer.readObject();
				ArrayList<String> data = catalogData.getCatalog();
				// System.out.println(data);

				for (String i : data) {
					// System.out.println(i);
					String str = new String();
					String[] contents = i.split(",");
					str = contents[0] + contents[1] + ": ";
					System.out.println(str);
					if (contents.length % 2 == 0) {
						for (int k = 2; k < contents.length; k++) {
							str += "[Section: " + contents[k] + " (0/" + contents[++k] + ")] ";
						}
					} else
						System.err.println("Wrong size of message****");

					str += "\n";
					mf.getRecords().append(str);

				}

			} catch (IOException f) {
				f.printStackTrace();
			} catch (ClassNotFoundException f) {
				f.printStackTrace();
			}

		});

	}

	private void createMainFrameAndListeners() {
		this.mf = new MainFrame("Schedule Builder");
		mf.setSize(1000, 563);
		mf.setVisible(true);
		createViewCourseCatalogueListener();
		createSearchCatalogueListener();
		createSearchStudentCoursesListener();
		createAddCourseListener();
		createRemoveCourseListener();
	}

	private void createSearchStudentCoursesListener() {
		System.err.println("Unimplemented function");

	}

	private void createSearchCatalogueListener() {
		System.err.println("Unimplemented function");

	}

}
