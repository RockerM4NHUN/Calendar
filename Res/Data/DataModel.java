package Res.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Res.Bin.CalendarEntry;
import Res.Bin.EventedList;

/**
 * Class that hold's application data.
 * 
 * @author ZODVAAT.SZE
 */
public class DataModel {
	private static EventedList<CalendarEntry> entryList = new EventedList<CalendarEntry>(new ArrayList<CalendarEntry>());
	private static List<String> typeList = new LinkedList<String>();
	private static String[] defaultTypeArray = new String[]{"Meeting","Birthday","Work","Other..."};
	
	public static final String newType = defaultTypeArray[3]; //type string for new type creation

	/**
	 * @author ZODVAAT.SZE
	 * @return List of added entries.
	 */
	public static EventedList<CalendarEntry> getEntryList() {
		return entryList;
	}
	
	/**
	 * @author ZODVAAT.SZE
	 * @return Type list.
	 */
	public static List<String> getTypeList() {
		return typeList;
	}
	
	/**
	 * @author ZODVAAT.SZE
	 * @return Default type list.
	 */
	public static String[] getDefaultTypeArray() {
		return defaultTypeArray;
	}
}
