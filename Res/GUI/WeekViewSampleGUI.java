package Res.GUI;

import java.awt.event.*;
import java.awt.Container;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.util.*;

import Res.*;
import Res.Bin.*;
import Res.GUI.Views.*;

public class WeekViewSampleGUI implements GUIGenerator{
	
	private Window parent;
	private WeekView w;
	private JLabel label;
	
	public void show(final Window parent, Container container){
		this.parent = parent;
		
		w = new WeekView();
		java.util.List<CalendarEntry> elist = new ArrayList<CalendarEntry>();
		Date now = new Date(System.currentTimeMillis());
		
		elist.add(new CalendarEntry(0,new Interval(now.getTime() - WeekView.HOUR_MILLIS,now.getTime() + WeekView.HOUR_MILLIS * 5 / 2),"TestType1",Color.BLACK,new Color(255,200,80),"Test Description1"));
		elist.add(new CalendarEntry(0,new Interval(now.getTime() - WeekView.HOUR_MILLIS * 7,now.getTime() - WeekView.HOUR_MILLIS * 4),"TestType2",Color.BLACK,new Color(200,200,80),"Test Description2"));
		//elist.add(new CalendarEntry(0,new Interval(now.getTime() - WeekView.HOUR_MILLIS * 6,now.getTime() + WeekView.HOUR_MILLIS * 4),"TestType",Color.BLACK,new Color(200,200,80),"Test Description"));
		
		w.setCalendarEntries(elist);
		w.toInterval(now);
		w.addSelectionChangedListener(new CalendarSelectionChangedListener(){
			public void selectionChanged(CalendarEntry e){
				if (e == null){
					System.out.println("Nothing selected");
					return;
				}
				System.out.println(e.getType());
			}
		});
		
		label = new JLabel(w.getDisplayedIntervalStart().toString());
		
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
		
		
		container.add(btnPrev);
		container.add(label);
		container.add(btnNext);
		container.add(w);
	}
	
	public void destroy(){}
}
