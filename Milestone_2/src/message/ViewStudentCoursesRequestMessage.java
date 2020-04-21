package message;

/**
 * Implementation of a view student course list request sent from the client to the server.
 * @author Maaz Khurram
 * @author Kevin Zhang
 * @author Saksham Nanda
 * @version 1.0
 * @since Apr 20, 2020
 *
 */
public class ViewStudentCoursesRequestMessage extends Message {

	private static final long serialVersionUID = 5L;
	/**
	 * No additional data is needed to request the student's course list.
	 */
	public ViewStudentCoursesRequestMessage() {
		super("ViewStudentCourseRequest");
	}

}
