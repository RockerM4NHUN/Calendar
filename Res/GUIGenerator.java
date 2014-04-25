package Res;

import javax.swing.JPanel;
import javax.swing.JFrame;

/**
 * Builds and holds the changing contents of window.
 */
public interface GUIGenerator{
	/**
	 * Builds the gui in container on parent window.
	 */
	public void show(Window parent, JPanel container);
	/**
	 * Called when the gui will be removed from window.
	 */
	public void destroy();
}
