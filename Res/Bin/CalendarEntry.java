package Res.Bin;

import java.awt.Color;

public class CalendarEntry{
	
	//constructors
	public CalendarEntry(int id, String type){
		if (type == null) Thrower.Throw(new NullPointerException("Null found in constructor"));
		
		this.id = id;
		interval = new Interval();
		this.type = type;
		String description = "";
		foregroundColor = DEFAULT_FOREGROUND_COLOR;
		backgroundColor = DEFAULT_BACKGROUND_COLOR;
	}
	
	public CalendarEntry(int id, Interval ival, String type, Color foreColor, Color backColor, String desc){
		if (ival == null || type == null || foreColor == null || backColor == null || desc == null) Thrower.Throw(new NullPointerException("Null found in constructor"));
		
		this.id = id;
		interval = ival;
		this.type = type;
		foregroundColor = foreColor;
		backgroundColor = backColor;
		String description = desc;
	}
	
	
	//constants
	public static final Color DEFAULT_FOREGROUND_COLOR = new Color(0,0,0);
	public static final Color DEFAULT_BACKGROUND_COLOR = new Color(255,102,0);
	
	
	//attributes
	private int id;
	private Interval interval;
	private String type;
	private String description;
	private Color foregroundColor;
	private Color backgroundColor;
	
	
	
	//getters
	public int getID(){return id;}
	public Interval getInterval(){return interval;}
	public String getType(){return type;}
	public String getDescription(){return description;}
	public Color getForegroundColor(){return foregroundColor;}
	public Color getBackgroundColor(){return backgroundColor;}
}
