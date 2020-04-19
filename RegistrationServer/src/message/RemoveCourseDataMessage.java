package message;

public class RemoveCourseDataMessage extends Message {

	public RemoveCourseDataMessage() {
		super(""); //give me "PASS" or "FAIL" in this string
		
		// give me the reason of failure in a hashmap with key "Failure Reason" 
		// map<String, String>
	}

}
