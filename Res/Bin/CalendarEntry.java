package Res.Bin;

import java.awt.Color;

/**
 * Holds the data of a Calendar event.
 * 
 * @author ZODVAAT.SZE
 */
public class CalendarEntry implements Comparable<CalendarEntry> {
	public CalendarEntry(){
		
		interval = null;
		this.type = null;
		this.title = null;
		foregroundColor = null;
		backgroundColor = null;
		description = null;
	}
	
	/**
	 * Fully parametered constructor.
	 * 
	 * @param ival Time interval of event with timestamp long values.
	 * @param type The type of the event.
	 * @param title The title of the event.
	 * @param foreColor Color of displayed event letters.
	 * @param backColor Color of event background.
	 * @param desc Description of the event.
	 * 
	 * @author ZODVAAT.SZE
	 */
	public CalendarEntry(Interval ival, String type, String title, Color foreColor, Color backColor, String desc) {
		if (ival == null || type == null || foreColor == null || backColor == null || desc == null) {
			Thrower.Throw(new NullPointerException("Null found in constructor"));
		}

		interval = ival;
		this.type = type;
		this.title = title;
		foregroundColor = foreColor;
		backgroundColor = backColor;
		description = desc;
	}
	
	/**
	 * Compares two entries based on their intervals length.
	 * 
	 * @param o CalendarEntry to compare with.
	 * 
	 * @author ZODVAAT.SZE
	 */
	public int compareTo(CalendarEntry o) {
		long l = interval.getLength() - o.interval.getLength();
		if (l == 0) {
			return (getObjectString().compareTo(o.getObjectString()));
		}
		if (l > Integer.MAX_VALUE) {
			return (int) (l / (((long)(Integer.MAX_VALUE) + 1) * 2));
		}
		return (int) l;
	}

	// constants
	public static final Color DEFAULT_FOREGROUND_COLOR = new Color(0, 0, 0);
	public static final Color DEFAULT_BACKGROUND_COLOR = new Color(0, 227, 217);

	// attributes
	private Interval interval;
	private String type;
	private String title;
	private String description;
	private Color foregroundColor;
	private Color backgroundColor;

	// getters
	public Interval getInterval() {
		return interval;
	}

	public String getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public String getObjectString() {
		return super.toString();
	}

	public String getDescription() {
		return description;
	}

	public Color getForegroundColor() {
		return foregroundColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	// setters
	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
