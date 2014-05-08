package Res.Bin;

public interface CalendarEventGenerator{
	public void addSelectionChangedListener(CalendarSelectionChangedListener listener);
	public void removeSelectionChangedListener(CalendarSelectionChangedListener listener);
	/**
	 * Selected will hold the selected calendar entry.
	 * If nothing is selected null.
	 */
	public void dispatchSelectionChanged(CalendarEntry selected);
}
