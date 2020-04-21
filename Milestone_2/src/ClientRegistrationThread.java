
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
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

/**
 * Provides all fields and methods to run the client thread and contains most of
 * the logic of the client
 *
 * @author Maaz Khurram
 * @author Kevin Zhang
 * @author Saksham Nanda
 * @version 1.0
 * @since 20th April
 *
 */
public class ClientRegistrationThread implements Runnable {

	/**
	 * to communicate with the server
	 */
	private Socket socket;
	/**
	 * handle for main frame
	 */
	private MainFrame mf;
	/**
	 * handle for login frame
	 */
	private LoginFrame lf;
	/**
	 * handle for course section frame
	 */
	private CourseSectionFrame csf;

	/**
	 * to read output from the server
	 */
	private ObjectInputStream fromServer;
	/**
	 * to read input from the server
	 */
	private ObjectOutputStream toServer;
	/**
	 * state of the application
	 */
	private boolean running = true;

	/**
	 * Some variables to store data
	 */
	private String courseName, courseNumber, courseSection;
	/**
	 * to mention the current operation
	 */
	private int targetOperation = -1; // -1 initially , 1 for add course, 2 for
										// remove
	/**
	 * to add and delete mouse listener
	 */
	private MouseListener ml;

	/**
	 * Constructs an object of ClientRegistrationThread with the given
	 * parameters
	 * 
	 * @param socket
	 * @param mainframe
	 * @param loginFrame
	 */
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

	/**
	 * runs the thread
	 */
	@Override
	public void run() {

		createLoginListener();
		createLoginExitListener();

	}

	/**
	 * Helper function to create mouse listener for section frame on the
	 * JTextArea
	 */
	private void createSectionFrameTextAreaListener() {

		csf.getSections().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				tAreaSectionFrameMousePressed(evt);
			}
		});

	}

	/**
	 * Helper function to create mouse listener for section frame on the
	 * JTextArea. Also look at createSectionFrameTextAreaListener()
	 */
	private void tAreaSectionFrameMousePressed(MouseEvent evt) {
		try {
			int offset = csf.getSections().viewToModel(evt.getPoint());
			Rectangle rect = csf.getSections().modelToView(offset);

			int startRow = csf.getSections().viewToModel(new Point(0, rect.y));
			int endRow = csf.getSections().viewToModel(new Point(csf.getSections().getWidth(), rect.y));

			String selectedSectionString = csf.getSections().getText(startRow, endRow - startRow);

			if (!selectedSectionString.isEmpty()) {
				csf.setCourseSection(selectedSectionString.substring(10, 11));
				//System.out.println("Course Section= " + csf.getCourseSection());
				csf.getSections().select(startRow, endRow);
			}

		} catch (BadLocationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Helper function to create mouse listener for main frame on the JTextArea
	 */
	private void createTextAreaListener() {
		ml = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				tAreaMousePressed(evt);
			}
		};

		mf.getRecords().addMouseListener(ml);

	}

	/**
	 * Helper function to create mouse listener for main frame on the
	 * JTextArea.Also look at createTextAreaListener()
	 */
	private void tAreaMousePressed(MouseEvent evt) {
		try {
			int offset = mf.getRecords().viewToModel(evt.getPoint());
			Rectangle rect = mf.getRecords().modelToView(offset);

			int startRow = mf.getRecords().viewToModel(new Point(0, rect.y));
			int endRow = mf.getRecords().viewToModel(new Point(mf.getRecords().getWidth(), rect.y));

			String selectedCourseString = mf.getRecords().getText(startRow, endRow - startRow);

			if (!selectedCourseString.isEmpty()) {
				mf.getRecords().select(startRow, endRow);
				courseName = selectedCourseString.substring(0, 4);
				//System.out.println("Course Name= " + courseName);

				courseNumber = selectedCourseString.substring(4, 7);
				//System.out.println("Course Number= " + courseNumber);

				String[] StringsOfCourseSections = selectedCourseString.split(Pattern.quote("["));

				csf = new CourseSectionFrame(StringsOfCourseSections);
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				csf.setLocation(dim.width / 2 - csf.getSize().width / 2, dim.height / 2 - csf.getSize().height / 2);
				createSectionFrameTextAreaListener();
				createSectionFrameListeners();

			}

		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a listener for Add Course Button
	 */
	private void createAddCourseListener() {
		mf.getAddCourse().addActionListener((ActionEvent e) -> {

			if (targetOperation == 2) {
				mf.getRecords().removeMouseListener(ml);
			}

			if (targetOperation == 1) {
				ViewAllCatalogueCourses();
				JOptionPane.showMessageDialog(null, "Please choose the course you want to take from the main window.");
			} else {

				targetOperation = 1;

				ViewAllCatalogueCourses();
				JOptionPane.showMessageDialog(null, "Please choose the course you want to take from the main window.");
				createTextAreaListener();

			}

		});

	}

	/**
	 * Helper function for adding a course
	 */
	private void AddACourse() {

		Message AddCourseRequestMessage = new AddRegistrationMessage(courseName, Integer.parseInt(courseNumber),
				Integer.parseInt(courseSection));

		try {
			this.toServer.writeObject(AddCourseRequestMessage);
			this.toServer.flush();
			ResponseMessage response = (ResponseMessage) this.fromServer.readObject();

			if (response.getInstruction().equals("SUCCESS")) {
				JOptionPane.showMessageDialog(null, "Success! Course added.");
			} else if (response.getInstruction().equals("FAIL")) {
				mf.showError((String) response.getFailureMessage());
			} else
				System.err.println("Invalid choice by Server side");

		} catch (IOException f) {
			f.printStackTrace();
		} catch (ClassNotFoundException f) {
			f.printStackTrace();
		}

	}

	/**
	 * Add a listener for View Course catalogue button
	 */
	private void createViewCourseCatalogueListener() {
		mf.getViewCC().addActionListener((ActionEvent e) -> {

			ViewAllCatalogueCourses();

		});

	}

	/**
	 * Helper function for viewing all courses.
	 */
	private void ViewAllCatalogueCourses() {
		//System.out.println("View Course Catalogue");
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
					//System.out.println(str);
					if ((contents.length - 2) % 3 == 0) {
						for (int k = 2; k < contents.length; k++) {
							str += "[Section: " + contents[k] + " (" + contents[++k] + "/" + contents[++k] + ")] ";
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

	/**
	 * Add a listener for Remove Course button
	 */
	private void createRemoveCourseListener() {
		mf.getRemoveCourse().addActionListener((ActionEvent e) -> {

			if (targetOperation == 1) {
				mf.getRecords().removeMouseListener(ml);
			}

			if (targetOperation == 2) {
				ViewStudentCourses();
				JOptionPane.showMessageDialog(null,
						"Please choose the course you want to remove from the main window.");
			} else {
				targetOperation = 2;
				ViewStudentCourses();
				JOptionPane.showMessageDialog(null,
						"Please choose the course you want to remove from the main window.");
				createTextAreaListener();
			}

		});

	}

	/**
	 * Helper function for removing a course
	 */
	private void RemoveACourse() {

		Message RemoveCourseRequest = new RemoveRegistrationMessage(courseName, Integer.parseInt(courseNumber),
				Integer.parseInt(courseSection));

		try {
			this.toServer.writeObject(RemoveCourseRequest);
			this.toServer.flush();
			ResponseMessage response = (ResponseMessage) this.fromServer.readObject();

			if (response.getInstruction().equals("SUCCESS")) {
				JOptionPane.showMessageDialog(null, "Success! Course Removed.");
			} else if (response.getInstruction().equals("FAIL")) {
				mf.showError((String) response.getFailureMessage());
			} else
				System.err.println("Invalid choice by Server side");

		} catch (IOException f) {
			f.printStackTrace();
		} catch (ClassNotFoundException f) {
			f.printStackTrace();
		}

	}

	/**
	 * Create a listener for View Student Courses button
	 */
	private void createViewStudentCoursesListener() {

		mf.getViewStudentCourses().addActionListener((ActionEvent e) -> {

			ViewStudentCourses();
		});

	}

	/**
	 * Helper function for View student courses
	 */
	private void ViewStudentCourses() {
		//System.out.println("View Student Course ");
		mf.getRecords().setText(null);
		Message ViewStudentCoursesRequest = new ViewStudentCoursesRequestMessage();

		try {
			this.toServer.writeObject(ViewStudentCoursesRequest);
			this.toServer.flush();
			ViewStudentCoursesDataMessage ViewStudentCoursesData = (ViewStudentCoursesDataMessage) this.fromServer
					.readObject();
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
					//System.out.println(str);
					if (contents.length == 5) {
						for (int k = 2; k < contents.length; k++) {
							str += "[Section: " + contents[k] + " (" + contents[++k] + "/" + contents[++k] + ")] ";
						}
					} else
						System.err.println("Wrong size of message****" + contents.length);

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

	/**
	 * Creates a listener for Search Catalog button
	 */
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

				Message searchCatalogueRequest = new SearchCatalogRequestMessage(CourseName.getText(),
						Integer.parseInt(CourseNumber.getText()), Integer.parseInt(CourseSection.getText()));

				try {
					this.toServer.writeObject(searchCatalogueRequest);
					this.toServer.flush();
					SearchCatalogDataMessage searchCatalogueData = (SearchCatalogDataMessage) this.fromServer
							.readObject();
					String courses = searchCatalogueData.getSearchResult();
					//System.out.println(courses);

					if (courses.isEmpty()) {
						mf.showError("Course does not exist!");
					} else {

						String str = new String();
						String[] contents = courses.split(",");
						str = contents[0] + contents[1] + ": ";
						//System.out.println(str);
						if ((contents.length - 2) % 3 == 0) {
							for (int k = 2; k < contents.length; k++) {
								str += "[Section: " + contents[k] + " (" + contents[++k] + "/" + contents[++k] + ")] ";
							}
						} else
							System.err.println("Wrong size of message****");

						str += "\n";
						mf.getRecords().setText(null);
						mf.getRecords().append(str);

						//System.out.println("targetOperation =" + targetOperation);
						//System.out.println(mf.getRecords().getMouseListeners().length);
						if ((targetOperation == 2) || (targetOperation == -1)) {
							//System.out.println("Adding a add course listener due to search course functionality");

							if (targetOperation == 2) {
								mf.getRecords().removeMouseListener(ml);
							}

							targetOperation = 1;

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

	/**
	 * Creates a listener for the Login button on Login frame
	 */
	private void createLoginListener() {
		lf.getLogin().addActionListener((ActionEvent e) ->

		{
			//System.out.println("Login");
			Message loginRequest = new LoginRequestMessage(lf.getUsernameField(), lf.getPasswordField());

			try {
				this.toServer.writeObject(loginRequest);
				this.toServer.flush();
				ResponseMessage response = (ResponseMessage) this.fromServer.readObject();

				if (response.getInstruction().equals("SUCCESS")) {
					lf.dispose();
					createMainFrameAndListeners();
				} else if (response.getInstruction().equals("FAIL")) {
					lf.showError();
				}

			} catch (IOException f) {
				f.printStackTrace();
			} catch (ClassNotFoundException f) {
				f.printStackTrace();
			}

		});

	}

	/**
	 * Creates a listener for Exit button on main frame
	 */
	private void createMainFrameExitListener() {
		mf.getExit().addActionListener((ActionEvent e) -> {

			Message QuitRequest = new QuittingMessage();
			try {
				this.toServer.writeObject(QuitRequest);
				this.toServer.flush();
			} catch (IOException f) {
				f.printStackTrace();
			}

			mf.dispose();
		});
	}

	private void createSectionFrameListeners() {
		csf.getOK().addActionListener((ActionEvent e) -> {
			courseSection = csf.getCourseSection();
			csf.dispose();

			if (targetOperation == 1)
				AddACourse();
			else if (targetOperation == 2)
				RemoveACourse();
			else if (targetOperation == -1)
				System.err.println("Problem is add or remove decision");

		});

		csf.getExit().addActionListener((ActionEvent e) -> {
			this.courseName = null;
			this.courseNumber = null;
			this.courseSection = null;
			csf.dispose();

		});
	}

	/**
	 * Creates a listener for exit button on Login frame
	 */
	private void createLoginExitListener() {
		lf.getExit().addActionListener((ActionEvent e) -> {

			Message QuitRequest = new QuittingMessage();
			try {
				this.toServer.writeObject(QuitRequest);
				this.toServer.flush();
			} catch (IOException f) {
				f.printStackTrace();
			}

			lf.dispose();
		});
	}

	/**
	 * Creates all main frame listeners and the main frame itself
	 */
	private void createMainFrameAndListeners() {
		this.mf = new MainFrame("Schedule Builder");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mf.setLocation(dim.width / 2 - mf.getSize().width / 2, dim.height / 2 - mf.getSize().height / 2);
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
