/*package Res.GUI.Views;

public class MonthView extends CalendarView{
	public MonthView(EventedList<CalendarEntry> elist){
		
	}
	
	public static final String[] days = new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
	public static final String[] months = new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};
	public static final long HOUR_MILLIS = (long)3600000;
	public static final long WEEK_MILLIS = 7*24*HOUR_MILLIS;
	
	private static int firstVerticalLine = 56;
	private static int firstHorizontalLine = 48;
	private static int finalVerticalGap = 0;//gap between final vertical line and width
	private static int finalHorizontalGap = 0;//gap between final horizontal line and height
	
	private int xstep;
	private int ystep;
	
	/**
	 * Displays the next time interval.
	 *//*
	public abstract void nextInterval(){
	}
	/**
	 * Displays the previous time interval.
	 *//*
	public abstract void prevInterval(){
		Calendar c = Calendar.getInstance();
		
	}
	/**
	 * Displays the specified time interval.
	 *//*
	public abstract void toInterval(Date month){
		if (month == null) Thrower.Throw(new NullPointerException("Error: Argument can't be null"));
		Calendar c = Calendar.getInstance();
		c.setTime(month);
		c.set(Calendar.DAY_OF_MONTH,0);
		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND,0);
		c.set(Calendar.AM_PM, Calendar.AM);
		* 
		long start = c.getTimeInMillis();
		
		c.add(Calendar.DAY_OF_YEAR,6 * 7);
		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND,0);
		c.set(Calendar.AM_PM, Calendar.AM);
		
		viewInterval = new Interval(start,c.getTimeInMillis());
		repaint();
	}
	/**
	 * Displays the drawable contents.
	 *//*
	public abstract void paintComponent(Graphics g);
	
	protected abstract void clickPerformed(MouseEvent e);
}*/
