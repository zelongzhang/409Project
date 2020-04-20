package message;

import java.util.ArrayList;

public class SearchCatalogDataMessage extends Message{
	
	private static final long serialVersionUID = 10L;
	private ArrayList<String> searchResult;
	
	public SearchCatalogDataMessage(ArrayList<String> searchresult)
	{
		super("CourseData");
		this.searchResult = searchresult;
	}
	public ArrayList<String> getSearchResult()
	{
		return this.searchResult;
	}

}
