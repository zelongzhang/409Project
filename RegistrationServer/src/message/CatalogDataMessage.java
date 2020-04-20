package message;

import java.util.ArrayList;

public class CatalogDataMessage extends Message
{

	private static final long serialVersionUID = 4L;
	private ArrayList<String> catalog;
	
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
