package message;

public class SearchCatalogDataMessage extends Message{
	
	private static final long serialVersionUID = 10L;
	private String searchResult;
	
	public SearchCatalogDataMessage(String searchresult)
	{
		super("CourseData");
		this.searchResult = searchresult;
	}
	public String getSearchResult()
	{
		return this.searchResult;
	}

}
