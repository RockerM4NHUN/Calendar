package Res.Bin;

import java.awt.Color;

public class CalendarEntry implements Comparable<CalendarEntry>{
	
	//constructor
	public CalendarEntry(int id, Interval ival, String type, String title, Color foreColor, Color backColor, String desc){
		if (ival == null || type == null || foreColor == null || backColor == null || desc == null) Thrower.Throw(new NullPointerException("Null found in constructor"));
		
		this.id = id;
		interval = ival;
		this.type = type;
		this.title = title;
		foregroundColor = foreColor;
		backgroundColor = backColor;
		description = desc;
	}
	
	public int compareTo(CalendarEntry o){
		long l = interval.getLength() - o.interval.getLength();
		if (l == 0) return (getObjectString().compareTo(o.getObjectString()));
		if (l > Integer.MAX_VALUE) return (int)(l / ((Integer.MAX_VALUE + 1) * 2));
		return (int)l;
	}
	
	//constants
	public static final Color DEFAULT_FOREGROUND_COLOR = new Color(0,0,0);
	public static final Color DEFAULT_BACKGROUND_COLOR = new Color(255,102,0);
	
	
	//attributes
	private int id;//entry id
	private Interval interval;//time interval of entry
	private String type;//entry type
	private String title;//entry title
	private String description;//entry description
	private Color foregroundColor;//letters color
	private Color backgroundColor;//rectangles color
	
	
	
	//getters
	public int getID(){return id;}
	public Interval getInterval(){return interval;}
	public String getType(){return type;}
	public String getTitle(){return title;}
	public String getObjectString(){return super.toString();}
	public String getDescription(){return description;}
	public Color getForegroundColor(){return foregroundColor;}
	public Color getBackgroundColor(){return backgroundColor;}
}
