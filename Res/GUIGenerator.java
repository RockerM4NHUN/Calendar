package Res;

import java.awt.Container;

import Res.GUI.Views.CalendarView;

/**
 * Builds and holds the changing contents of window.
 * 
 * @author ZODVAAT.SZE
 */
public interface GUIGenerator {
	/**
	 * Builds the gui in container on parent window.
	 */
	public CalendarView show(Window parent, Container container);

	/**
	 * Called when the gui will be removed from window.
	 */
	public void destroy();
}
