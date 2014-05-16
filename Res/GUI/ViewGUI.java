package Res.GUI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import Res.GUIGenerator;
import Res.Window;
import Res.Bin.CalendarEntry;
import Res.Bin.CalendarSelectionChangedListener;
import Res.Bin.EventedList;
import Res.Bin.Interval;
import Res.Data.DataModel;
import Res.GUI.Views.CalendarView;
import Res.GUI.Views.WeekView;

public class ViewGUI implements GUIGenerator {

	private Window parent;
	private WeekView w;
	private JLabel label;
	private JScrollPane scroll;
	public static CalendarEntry selectedEntry;
	
	private static final int heightCorrection = 75;
	
	public static int getGUIHeight(){
		return WeekView.getViewHeight() + heightCorrection;
	}

	@Override
	public CalendarView show(final Window parent, Container container) {
		this.parent = parent;

		Date now = new Date(System.currentTimeMillis());
		EventedList<CalendarEntry> elist = DataModel.getEntryList();
		w = new WeekView(elist);

		w.toInterval(now);
		w.addSelectionChangedListener(new CalendarSelectionChangedListener() {
			@Override
			public void selectionChanged(CalendarEntry e) {
				if (e == null) {
					selectedEntry = null;
					System.out.println("Nothing selected");
					return;
				}
				selectedEntry = e;
				System.out.println(e.getType() + " | " + e.getInterval().getStartTimestamp().toString() + " --- "
						+ e.getInterval().getEndTimestamp().toString());
			}
		});

		scroll = new JScrollPane(w);
		int barWidth = 15;
		scroll.getVerticalScrollBar().setPreferredSize(new Dimension(barWidth, 0));
		if ((int)parent.size.getHeight() < getGUIHeight()){
			scroll.setPreferredSize(new Dimension(WeekView.getViewWidth() + barWidth + 6, (int)parent.size.getHeight() - heightCorrection));
		}else{
			scroll.setPreferredSize(new Dimension(WeekView.getViewWidth() + barWidth + 6, WeekView.getViewHeight() + 6));
		}

		label = new JLabel(getDateString(w.getDisplayedInterval()));

		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				w.nextInterval();
				label.setText(getDateString(w.getDisplayedInterval()));
			}
		});
		JButton btnPrev = new JButton("Prev");
		btnPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				w.prevInterval();
				label.setText(getDateString(w.getDisplayedInterval()));
			}
		});

		container.add(btnPrev);
		container.add(label);
		container.add(btnNext);
		container.add(scroll);
		return w;
	}
	
	private static String getDateString(Interval time){
		return time.getStartTimestamp().toString().split(" ")[0] + " --- " + time.getEndTimestamp().toString().split(" ")[0];
	}

	@Override
	public void destroy() {
	}
}
