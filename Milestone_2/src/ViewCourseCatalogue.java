import javax.swing.JTextArea;

public class ViewCourseCatalogue {

	public ViewCourseCatalogue(JTextArea records)
	{
		ViewCatalogue(records);
	}
	
	
	private void ViewCatalogue(JTextArea records)
	{
		records.setText(null);
		records.append("Saksham is better====\n");
	}
	
}
