package Res;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.sql.Timestamp;

import Res.Bin.*;
import Res.GUI.Views.*;

public class Window extends JFrame{
	public Window(){
		super("Calendar");
		
		gui = null;
		GUIPanel = null;
		
		setLayout(new FlowLayout());
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Point location = new Point(400,300);
		setLocation(location);
		
		setResizable(false);
		setSize(1024+20,768+50);
		
		//refreshGUI(new DefaultGUI()); //loads the first GUI
		final WeekView w = new WeekView();
		java.util.List<CalendarEntry> elist = new ArrayList<CalendarEntry>();
		Date now = new Date(System.currentTimeMillis());
		
		elist.add(new CalendarEntry(0,new Interval(now.getTime(),now.getTime() + WeekView.HOUR_MILLIS * 5 / 2),"TestType1",Color.BLACK,new Color(255,200,80),"Test Description1"));
		elist.add(new CalendarEntry(0,new Interval(now.getTime() - WeekView.HOUR_MILLIS * 7,now.getTime() - WeekView.HOUR_MILLIS * 4),"TestType2",Color.BLACK,new Color(200,200,80),"Test Description2"));
		//elist.add(new CalendarEntry(0,new Interval(now.getTime() - WeekView.HOUR_MILLIS * 6,now.getTime() + WeekView.HOUR_MILLIS * 4),"TestType",Color.BLACK,new Color(200,200,80),"Test Description"));
		count = 0;
		w.setCalendarEntries(elist);
		w.toInterval(now);
		w.addSelectionChangedListener(new CalendarSelectionChangedListener(){
			public void selectionChanged(CalendarEntry e){
				count++;
				final String str = (char)(0x000D) + "";
				System.out.print(str + count);
			}
		});
		
		final JLabel label = new JLabel(w.getDisplayedIntervalStart().toString());
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				w.nextInterval();
				label.setText(w.getDisplayedIntervalStart().toString());
			}
		});
		JButton btnPrev = new JButton("Prev");
		btnPrev.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				w.prevInterval();
				label.setText(w.getDisplayedIntervalStart().toString());
			}
		});
		
		
		add(btnPrev);
		add(label);
		add(btnNext);
		add(w);
		//add(new JPanel());
		
		setVisible(true);
	}
	
	private GUIGenerator gui;
	private JPanel GUIPanel; //JPanel for changable GUIs
	
	int count;//test variable
	
	/**
	 * #UNTESTED
	 * 
	 * Sets contents of window.
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
