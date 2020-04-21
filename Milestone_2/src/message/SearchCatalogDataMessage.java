package message;

/**
 * Implementation of a search result sent from the server to the client.
 * @author Kevin
 * @version 1.0
 * @since Apr 20, 2020
 *
 */
public class SearchCatalogDataMessage extends Message{
	
	private static final long serialVersionUID = 10L;
	private String searchResult;
	
	/**
	 * The search result is sent as a string representing the course if the query was successful, 
	 * search result will be an empty string if search was unable to find a corresponding course.
	 * @param searchresult the result of the search.
	 */
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
