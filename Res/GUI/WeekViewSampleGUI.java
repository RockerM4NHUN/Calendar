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
		final java.util.List<CalendarEntry> elist = new ArrayList<CalendarEntry>();
		Date now = new Date(System.currentTimeMillis() - WeekView.HOUR_MILLIS * 48 * 1);
		
		elist.add(new CalendarEntry(0,new Interval(now.getTime() - WeekView.HOUR_MILLIS * 5,now.getTime() - WeekView.HOUR_MILLIS * 2),"TestType0",Color.BLACK,new Color(255,200,80),"Test Description0"));
		
		elist.add(new CalendarEntry(0,new Interval(now.getTime() - WeekView.HOUR_MILLIS * 2,now.getTime() + WeekView.HOUR_MILLIS * 36),"TestType1",Color.BLACK,new Color(255,200,80),"Test Description1"));
		elist.add(new CalendarEntry(0,new Interval(now.getTime() - WeekView.HOUR_MILLIS * 2,now.getTime() - WeekView.HOUR_MILLIS * 1),"TestType2",Color.BLACK,new Color(200,200,80),"Test Description2"));
		//w.setCalendarEntries(elist);
		elist.add(new CalendarEntry(0,new Interval(now.getTime() - WeekView.HOUR_MILLIS * 0,now.getTime() + WeekView.HOUR_MILLIS * 2),"TestType3",CalendarEntry.DEFAULT_FOREGROUND_COLOR,CalendarEntry.DEFAULT_BACKGROUND_COLOR,"Test Description3"));
		elist.add(new CalendarEntry(0,new Interval(now.getTime() - WeekView.HOUR_MILLIS * 2,now.getTime() + WeekView.HOUR_MILLIS * 0),"TestType4",Color.BLACK,new Color(255,200,80),"Test Description4"));
		elist.add(new CalendarEntry(0,new Interval(now.getTime() - WeekView.HOUR_MILLIS * 1,now.getTime() + WeekView.HOUR_MILLIS * 2),"TestType5",Color.BLACK,new Color(255,200,80),"Test Description5"));
		
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
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				parent.refreshGUI(new SampleGUI());
			}
		});
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
		JButton btnTest = new JButton("Test");
		btnTest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				if (elist.size() > 0){
					w.removeCalendarEntry(elist.remove(0));
					w.repaint();
				}
			}
		});
		
		
		container.add(btnBack);
		container.add(btnPrev);
		container.add(label);
		container.add(btnNext);
		container.add(btnTest);
		container.add(w);
	}
	
	public void destroy(){}
}
