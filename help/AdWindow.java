package AdSystem.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.nio.BufferUnderflowException;
import java.util.*;

/**
 * Class that extends JFrame and handles context change's in window.
 * @author Zombori Dániel
 */
public class AdWindow extends JFrame{
	
	private java.util.List<AdGUI> prevGUIs = new ArrayList<AdGUI>();
	private AdGUI gui;
	
	/**
	 * Init's new window.
	 * Set's layout to FlowLayout</br>
	 * Disable's resizing window by user</br>
	 * @author Zombori Dániel
	 * @param title The window title
	 * @param exitOnClose	If true the program will exit when the window closes.<br/>
	 * 						If false only the window will be closed.
	 * @param width The window width
	 * @param height The window height
	 */
	public AdWindow(String title, boolean exitOnClose,int width, int height){
		super(title);
		setLayout(new FlowLayout());
		pack();
		
		if (exitOnClose){
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}else{
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		
		Point location = new Point(0,0);
		//location.move(-width/2,-height/2);
		setLocation(location);
		
		setSize(width,height);
		setResizable(false);
		
	}
	
	/**
	 * Deletes saved GUIs.
	 * @author Zombori Dániel
	 */
	public void deleteSavedGUI(){
		prevGUIs.clear();
	}
	
	/**
	 * Gets back the last saved GUI.
	 * @author Zombori Dániel
	 * @return True if displaying previous gui failed
	 */
	public boolean toSavedGUI(){
		return toSavedGUI(1);
	}
	
	/**
	 * Gets back stepth saved GUI.
	 * @author Zombori Dániel
	 * @param steps Make specified steps toward to the first saved GUI.
	 * @return True if displaying previous gui failed
	 */
	public boolean toSavedGUI(int steps){
		if (steps <= 0){
			throw new BufferUnderflowException();
		}
		AdGUI gui;
		if (prevGUIs.size() != 0){
			
			if (prevGUIs.size() < steps){
				gui = prevGUIs.remove(prevGUIs.size()-1);
				prevGUIs.clear();
			}else{			
				gui = prevGUIs.remove(steps-1);
				while(steps > 1){
					steps--;
					prevGUIs.remove(steps-1);
				}
			}
			
			gui.getParent().refreshGUI(gui.getDynamicSingleton(),false);
			return false;
		}
		return true;
	}
	
	/**
	 * Sets contents of window.
	 * @author Zombori Dániel
	 * @param gui Component holder class to show
	 * @param	savePrevGUI Saves actual GUI if true.<br/>
	 * 			Otherwise not delete saved previous GUI's.
	 */
	public void refreshGUI(AdGUI gui, boolean savePrevGUI){
		if (this.gui != null) this.gui.destroy();
		getContentPane().removeAll();
		if (savePrevGUI){
			prevGUIs.add(0,this.gui);
		}
		this.gui = gui;
		gui.show(this,getContentPane());
		getContentPane().repaint();
		validate();
	}
	
	/**
	 * Sets contents of window.
	 * @author Zombori Dániel
	 * @param gui Component holder class to show
	 */
	public void refreshGUI(AdGUI gui){
		prevGUIs.clear();
		
		if (this.gui != null) this.gui.destroy();
		getContentPane().removeAll();
		
		this.gui = gui;
		gui.show(this,getContentPane());
		
		getContentPane().repaint();
		validate();
	}
}
