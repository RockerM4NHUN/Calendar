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
import Res.Data.DataModel;
import Res.GUI.Views.CalendarView;
import Res.GUI.Views.WeekView;

public class ViewGUI implements GUIGenerator {

	private Window parent;
	private WeekView w;
	private JLabel label;
	private JScrollPane scroll;
	public static CalendarEntry selectedEntry;

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
		scroll.setPreferredSize(new Dimension(WeekView.getViewWidth() + barWidth + 6, parent.getHeight() - 100));
		System.out.println(parent.size.getHeight());
		System.out.println(parent.getHeight());

		label = new JLabel(w.getDisplayedInterval().getStartTimestamp().toString());

		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				w.nextInterval();
				label.setText(w.getDisplayedInterval().getStartTimestamp().toString());
				System.out.println(parent.getHeight());
			}
		});
		JButton btnPrev = new JButton("Prev");
		btnPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				w.prevInterval();
				label.setText(w.getDisplayedInterval().getStartTimestamp().toString());
			}
		});

		container.add(btnPrev);
		container.add(label);
		container.add(btnNext);
		container.add(scroll);
		// container.add(w);
		return w;
	}

	@Override
	public void destroy() {
	}
}
