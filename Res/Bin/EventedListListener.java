package Res.Bin;

/**
 * Listener for EventedList events.
 * 
 * @author ZODVAAT.SZE
 */
public interface EventedListListener<ListItem>{
	/**
	 * Called when event dispatched.
	 * 
	 * @author ZODVAAT.SZE
	 */
	public void listModified(ListItem item);
}
