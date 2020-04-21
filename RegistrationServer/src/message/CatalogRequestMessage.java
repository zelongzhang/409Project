package message;
/**
 * Implementation of a full catalog request from the client to the server
 * @author Kevin
 * @version 1.0
 * @since Apr 20, 2020
 *
 */
public class CatalogRequestMessage extends Message
{

	private static final long serialVersionUID = 3L;
	/*
	 * To request a full catalog, no additional data is needed.
	 */
	public CatalogRequestMessage()
	{
		super("CatalogRequest");
	}
}

