package message;

import java.util.ArrayList;
/**
 * Implementation of a full catalog data message from the server to the client.
 * @author Maaz Khurram
 * @author Kevin Zhang
 * @author Saksham Nanda
 * @version 1.0
 * @since Apr 20, 2020
 *
 */
public class CatalogDataMessage extends Message
{

	private static final long serialVersionUID = 4L;
	private ArrayList<String> catalog;
	/**
	 * The catalog is given to the client using an ArrayList of Strings, each element representing a different course.
	 */
	public CatalogDataMessage(ArrayList<String> cat)
	{
		super("CatalogData");
		this.catalog = cat;
	}
	public ArrayList<String> getCatalog()
	{
		return this.catalog;
	}
}
