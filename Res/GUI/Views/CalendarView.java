package Res.GUI.Views;

import java.sql.Timestamp;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.Dimension;
import java.util.*;
import javax.swing.JPanel;

import Res.Bin.*;

/**
 * Abstract class for displaying calendar entries.
 * @author ZODVAAT.SZE
 */
public abstract class CalendarView extends JPanel implements CalendarEventGenerator{
	public CalendarView(){
		super();
		
		toInterval(new Timestamp(System.currentTimeMillis()));
		selectionChangedListeners = new ArrayList<CalendarSelectionChangedListener>();
		
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				clickPerformed(e);
			}
		});
		
		setVisible(true);
	}
	
	protected Interval viewInterval;
	private List<CalendarSelectionChangedListener> selectionChangedListeners;
	
	/**
	 * @author ZODVAAT.SZE
	 * @return The displayed interval.
	 */
	public Interval getDisplayedInterval(){
		return viewInterval.clone();
	}
	
	/**
	 * Adds SelectionChangedListener to view.
	 * @author ZODVAAT.SZE
	 * @param listener Value to add.
	 */
	public void addSelectionChangedListener(CalendarSelectionChangedListener listener){
		for (CalendarSelectionChangedListener l : selectionChangedListeners){
			if (l == listener) return;
		}
		selectionChangedListeners.add(listener);
	}
	
	/**
	 * Removes the listener.
	 * 
	 * @author ZODVAAT.SZE
	 * @param listener Value to remove.
	 * @return False if listener not found.
	 */
	public boolean removeSelectionChangedListener(CalendarSelectionChangedListener listener){
		return selectionChangedListeners.remove(listener);
	}
	
	/**
	 * Fires all SelectionChangedListeners
	 * 
	 * @author ZODVAAT.SZE
	 * @param selected Selected value to dispatch.
	 */
	public void dispatchSelectionChanged(CalendarEntry selected){
		for (CalendarSelectionChangedListener l : selectionChangedListeners){
			l.selectionChanged(selected);
		}
	}
	
	/**
	 * Wrapper method for JPanels paintComponent()
	 * 
	 * @author ZODVAAT.SZE
	 * @param g Graphics painting component.
	 */
	protected void abstractPaintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	/**
	 * Returns the 0 and Monday started day of week.
	 * 
	 * @param timestamp Timestamp of time.
	 * @author ZODVAAT.SZE
	 * @return Day of week.
	 */
	protected int dayOfWeek(long timestamp){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(timestamp));
		if (c.get(Calendar.DAY_OF_WEEK) == 1) return 6;
		return c.get(Calendar.DAY_OF_WEEK) - 2;
	}
	
	/**
	 * @param timestamp Timestamp of time.
	 * @author ZODVAAT.SZE
	 * @return The hour of time.
	 */
	protected int hourOfDay(long timestamp){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(timestamp));
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	//abstract methods
	/**
	 * Displays the next time interval.
	 */
	public abstract void nextInterval();
	/**
	 * Displays the previous time interval.
	 */
	public abstract void prevInterval();
	/**
	 * Displays the specified time interval.
	 * @param t Specified time interval.
	 */
	public abstract void toInterval(Date t);
	/**
	 * Sets the selected entry.
	 * @param e Selected entry.
	 * @return Default value.
	 */
	public abstract boolean setSelected(CalendarEntry e);
	/**
	 * Displays the drawable contents.
	 * @param g Drawable content.
	 */
	public abstract void paintComponent(Graphics g);
	
	/**
	 * Handles the mouse clicks.
	 * @author ZODVAAT.SZE
	 * @param e Performed click.
	 */
	protected abstract void clickPerformed(MouseEvent e);
}
