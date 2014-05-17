package Res.Bin;

/**
 * Interface for program specific event generators.
 * 
 * @author ZODVAAT.SZE
 */
public interface CalendarEventGenerator{
	public void addSelectionChangedListener(CalendarSelectionChangedListener listener);
	/**
	 * @author ZODVAAT.SZE
	 * @param listener Listener to remove.
	 * @return True if the listener have been succesfully removed.
	 */
	public boolean removeSelectionChangedListener(CalendarSelectionChangedListener listener);
	/**
	 * @param selected Holds the selected calendar entry. If nothing is selected the value is null.
	 * @author ZODVAAT.SZE
	 */
	public void dispatchSelectionChanged(CalendarEntry selected);
}
