import java.util.ArrayList;

public class CourseSection 
{
	private int sectionNum;
	private int sectionCap;
	private ArrayList<Registration> regList;
	
	public CourseSection(int num, int cap)
	{
		this.sectionNum = num;
		this.sectionCap = cap;
		this.regList = new ArrayList<Registration>();
	}
	
	public void addRegistration(Registration reg)
	{
		if(!this.regList.contains(reg))
		{
			this.regList.add(reg);
		}
	}
	
	public void removeRegistration(Registration reg)
	{
		for(Registration r : this.regList)
		{
			if(r.equals(reg))
			{
				this.regList.remove(r);
				return;
			}
		}
		System.out.println("The registration "+reg+ " does not exist in the section: "+this);
	}
	
	public String toString()
	{
		return "[Section: "+this.sectionNum+" ("+this.regList.size()+"/"+this.sectionCap+")]";
	}
	
	public boolean equals(Object o)
	{
		if(o == this)
		{
			return true;
		}
		if(o instanceof CourseSection
				&& ((CourseSection)o).getSectionNum() == this.getSectionNum()
				&& ((CourseSection)o).getSectionCap() == this.getSectionCap())
		{
			return true;
		}
		return false;
	}


	public int getSectionNum() {
		return sectionNum;
	}

	public int getSectionCap() {
		return sectionCap;
	}
	
}
