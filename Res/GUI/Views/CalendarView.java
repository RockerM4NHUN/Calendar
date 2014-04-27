package Res.GUI.Views;

import java.sql.Timestamp;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.Dimension;
import java.util.*;
import javax.swing.JPanel;

import Res.Bin.*;

public abstract class CalendarView extends JPanel implements CalendarEventGenerator{
	public CalendarView(){
		super();
		
		toInterval(new Timestamp(System.currentTimeMillis()));
		entries = new ArrayList<CalendarEntry>();
		selectionChangedListeners = new ArrayList<CalendarSelectionChangedListener>();
		
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				clickPerformed(e);
			}
		});
		
		setVisible(true);
	}
	
	protected List<CalendarEntry> entries;
	protected Timestamp startTime;
	
	private List<CalendarSelectionChangedListener> selectionChangedListeners;
	
	public List<CalendarEntry> getCalendarEntries(){
		return entries;
	}
	public void setCalendarEntries(List<CalendarEntry> elist){
		if (elist == null) Thrower.Throw(new NullPointerException("Error: Argument can't be null"));
		entries = elist;
	}
	
	public Timestamp getDisplayedIntervalStart(){
		return startTime;
	}
	
	
	public void addSelectionChangedListener(CalendarSelectionChangedListener listener){
		for (CalendarSelectionChangedListener l : selectionChangedListeners){
			if (l == listener) return;
		}
		selectionChangedListeners.add(listener);
	}
	public void removeSelectionChangedListener(CalendarSelectionChangedListener listener){
		selectionChangedListeners.remove(listener);
	}
	/**
	 * Selected will hold the selected calendar entry.
	 * If nothing is selected null.
	 */
	public void dispatchSelectionChanged(CalendarEntry selected){
		for (CalendarSelectionChangedListener l : selectionChangedListeners){
			l.selectionChanged(selected);
		}
	}
	
	/**
	 * Wrapper method for JPanels paintComponent()
	 */
	protected void abstractPaintComponent(Graphics g){
		super.paintComponent(g);
	}
	protected int dayOfWeek(long timestamp){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(timestamp));
		if (c.get(Calendar.DAY_OF_WEEK) == 1) return 6;
		return c.get(Calendar.DAY_OF_WEEK) - 2;
	}
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
	 */
	public abstract void toInterval(Date t);
	/**
	 * Displays the drawable contents.
	 */
	public abstract void paintComponent(Graphics g);
	
	protected abstract void clickPerformed(MouseEvent e);
}
