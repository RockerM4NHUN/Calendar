package Res.Data;

import java.util.LinkedList;

import Res.Bin.EventedList;
import Res.Bin.CalendarEntry;

/**
 * Class that hold's application data.
 * 
 * @author ZODVAAT.SZE
 */
public class DataModel{
	private static EventedList<CalendarEntry> entryList = new EventedList<>();
	
	/**
	 * @author ZODVAAT.SZE
	 * @return List of added entries.
	 */
	public static EventedList<CalendarEntry> getEntryList(){
		return entryList;
	}
}
