package Res.GUI.Views;

public abstract class CalendarView extends JPanel{
	public CalendarView(){
		super();
		
		entries = new ArrayList<CalendarEntry>();
	}
	
	private List<CalendarEntry> entries;
	
	public List<CalendarEntry> getCalendarEntries(){
		return entries;
	}
	
	public void setCalendarEntries(List<CalendarEntry> elist){
		if (elist == null) Thrower.Throw(new NullPointerException("Error: Argument can't be null"));
		entries = elist;
	}
}
