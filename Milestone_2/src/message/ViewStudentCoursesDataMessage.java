package message;

import java.util.ArrayList;

public class ViewStudentCoursesDataMessage extends Message {
	
	
	
	private static final long serialVersionUID = 3L;
	private ArrayList<String> catalog;
	
	public ViewStudentCoursesDataMessage(ArrayList<String> cat)
	{
		super("CatalogData");
		this.catalog = cat;
	}
	public ArrayList<String> getCatalog()
	{
		return this.catalog;
	}

}
