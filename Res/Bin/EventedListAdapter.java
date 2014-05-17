package Res.Bin;

import java.util.List;

/**
 * Adapter for EventedList events.
 * 
 * @author ZODVAAT.SZE
 */
public class EventedListAdapter<ListItem> implements EventedListListener<ListItem>{
	/**
	 * Called when single list item modified.
	 * 
	 * @author ZODVAAT.SZE
	 * @param item Modified item.
	 */
	public void itemModified(ListItem item){}
	/**
	 * Called when full list modified.
	 * 
	 * @author ZODVAAT.SZE
	 * @param newVersion New entries of list.
	 */
	public void listModified(EventedList<ListItem> newVersion){}
}
