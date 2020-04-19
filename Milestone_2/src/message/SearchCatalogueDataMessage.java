package message;

import java.util.ArrayList;

public class SearchCatalogueDataMessage extends Message{
	
	private static final long serialVersionUID = 3L;
	private ArrayList<String> catalog;
	
	public SearchCatalogueDataMessage(ArrayList<String> cat)
	{
		super("CourseData");
		this.catalog = cat;
	}
	public ArrayList<String> getCatalog()
	{
		return this.catalog;
	}

}
