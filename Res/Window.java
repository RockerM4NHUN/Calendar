package Res;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.sql.Timestamp;

import Res.Bin.*;
import Res.GUI.*;

public class Window extends JFrame{
	public Window(){
		super("Calendar");
		
		gui = null;
		GUIPanel = getContentPane();
		
		setLayout(new FlowLayout());
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Point location = new Point(400,300);
		setLocation(location);
		
		setResizable(false);
		setSize(1024+50,768+50);
		
		refreshGUI(new SampleGUI()); //loads the first GUI
		
		refreshGUI(new WeekViewSampleGUI()); //for quicker testing
		
		setVisible(true);
	}
	
	private GUIGenerator gui;
	private Container GUIPanel; //Container for changable GUIs
	
	/**
	 * Sets contents of window.
	 * 
	 * @author Zombori Dániel
	 * @param gui Component holder class to show
	 */
	public void refreshGUI(GUIGenerator gui){
		if (this.gui != null) this.gui.destroy();
		GUIPanel.removeAll();
		
		this.gui = gui;
		gui.show(this,GUIPanel);
		
		validate();
		GUIPanel.repaint();
	}
}
