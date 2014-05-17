package Res.Bin;

/**
 * Listener for selection changed event.
 * 
 * @author ZODVAAT.SZE
 */
public interface CalendarSelectionChangedListener{
	/**
	 * @param selected Holds the selected calendar entry. If nothing is selected the value is null.
	 * @author ZODVAAT.SZE
	 */
	public void selectionChanged(CalendarEntry selected);
}
