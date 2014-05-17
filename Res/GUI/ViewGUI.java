package Res.GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import Res.GUIGenerator;
import Res.Window;
import Res.Bin.CalendarEntry;
import Res.Bin.CalendarSelectionChangedListener;
import Res.Bin.EventedList;
import Res.Bin.Interval;
import Res.Data.DataModel;
import Res.Edit.DateChooserWindow;
import Res.GUI.Views.CalendarView;
import Res.GUI.Views.WeekView;

/**
 * Sets the contents of a window to display CalendarEntry data.
 * 
 * @author ZODVAAT.SZE
 */
public class ViewGUI implements GUIGenerator {

	private Window parent;
	private WeekView w;
	public static JLabel label;
	private JScrollPane scroll;
	private JLabel showLabel;
	private Timer timer;
	public static CalendarEntry selectedEntry;

	private static final int heightCorrection = 75;
	private static final int widthCorrection = 210;

	/**
	 * @author ZODVAAT.SZE
	 * @return The preferred view width.
	 */
	public static int getGUIWidth() {
		return WeekView.getViewWidth() + widthCorrection;
	}

	/**
	 * @author ZODVAAT.SZE
	 * @return The preferred view height.
	 */
	public static int getGUIHeight() {
		return WeekView.getViewHeight() + heightCorrection;
	}

	/**
	 * Adds GUI components to te container.
	 * 
	 * @param parent
	 *            The window that holds the container of view.
	 * @param container
	 *            The container to add GUI components.
	 * @author ZODVAAT.SZE
	 * @return The displayed CalendarView object.
	 */
	@Override
	public CalendarView show(final Window parent, Container container) {
		this.parent = parent;

		container.setLayout(null);

		Date now = new Date(System.currentTimeMillis());
		EventedList<CalendarEntry> elist = DataModel.getEntryList();
		w = new WeekView(elist);

		w.toInterval(now);
		w.addSelectionChangedListener(new CalendarSelectionChangedListener() {
			@Override
			public void selectionChanged(CalendarEntry e) {
				selectedEntry = e;
				showLabel.setText(generateSelectionText(selectedEntry, System.currentTimeMillis()));
				if (selectedEntry == null) {
					System.out.println("Nothing selected");
					return;
				}
				System.out.println(e.getType() + " | " + e.getInterval().getStartTimestamp().toString() + " --- "
						+ e.getInterval().getEndTimestamp().toString());
			}
		});

		showLabel = new JLabel(generateSelectionText(null, 0));
		showLabel.setVerticalAlignment(JLabel.TOP);
		showLabel.setVerticalTextPosition(JLabel.TOP);
		showLabel.setOpaque(true);
		showLabel.setBackground(new Color(250, 232, 155));

		scroll = new JScrollPane(w);
		int barWidth = 15;
		scroll.getVerticalScrollBar().setPreferredSize(new Dimension(barWidth, 0));
		if ((int) parent.size.getHeight() < getGUIHeight()) {
			scroll.setBounds(widthCorrection + 10, 10, WeekView.getViewWidth() + barWidth + 6,
					(int) parent.size.getHeight() - heightCorrection);
			showLabel.setBounds(10, 130, widthCorrection - 10, (int) parent.size.getHeight() - heightCorrection - 130);
		} else {
			scroll.setBounds(widthCorrection + 10, 10, WeekView.getViewWidth() + barWidth + 6,
					WeekView.getViewHeight() + 6);
			showLabel.setBounds(10, 130, widthCorrection - 10, WeekView.getViewHeight() + 6 - 130 + 10);
		}

		label = new JLabel(getDateString(w.getDisplayedInterval()), JLabel.CENTER);
		label.setBounds(10, 10, 200, 30);

		JButton btnNext = new JButton("Next");
		btnNext.setBounds(115, 50, 95, 30);
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				w.nextInterval();
				label.setText(getDateString(w.getDisplayedInterval()));
			}
		});
		JButton btnPrev = new JButton("Previous");
		btnPrev.setBounds(10, 50, 95, 30);
		btnPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				w.prevInterval();
				label.setText(getDateString(w.getDisplayedInterval()));
			}
		});
		JButton btnJump = new JButton("To specific date");
		btnJump.setBounds(10, 90, 200, 30);
		btnJump.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				final DateChooserWindow dateChooser = new DateChooserWindow(true);
				dateChooser.show(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println(dateChooser.getDate());
						if (dateChooser.getDate() == null) {
							return;
						}
						w.toInterval(dateChooser.getDate());
						label.setText(getDateString(w.getDisplayedInterval()));
					}
				});
			}
		});

		// reload event text in every minute, to validate time
		timer = new Timer((int) (WeekView.HOUR_MILLIS / 60), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				showLabel.setText(generateSelectionText(selectedEntry, System.currentTimeMillis()));
			}
		});
		timer.start();

		container.add(btnPrev);
		container.add(label);
		container.add(btnNext);
		container.add(btnJump);
		container.add(showLabel);
		container.add(scroll);
		return w;
	}

	/**
	 * Generates the contents of label that shows CalendarEntry.
	 * 
	 * @param e
	 *            CalendarEntry to display.
	 * @param currentMillis
	 *            Time to relativate entrys occur.
	 * @author ZODVAAT.SZE
	 */
	private static String generateSelectionText(CalendarEntry e, long currentMillis) {
		String ret = "<html><font size=+0>";
		String retEnd = "</font></html>";

		if (e == null) {
			ret += "<b>No entry selected...</b>";
			return ret + retEnd;
		}

		ret += "<b>Type:</b><br/>" + e.getType() + "<br/>";
		ret += "<br/>";
		ret += "<b>Title:</b><br/>" + e.getTitle() + "<br/>";
		ret += "<br/>";

		Interval ival = e.getInterval();
		if (ival.contains(currentMillis)) {
			ret += "<font size=+1 color=red>Event started " + formatTime(currentMillis - ival.getStart())
					+ " ago</font><br/>";
		} else if (currentMillis < ival.getStart()) {
			ret += "Event starts in " + formatTime(ival.getStart() - currentMillis);
		} else {
			ret += "Event ended " + formatTime(currentMillis - ival.getEnd()) + " ago";
		}
		ret += "<br/><br/>";
		ret += "<b>Event description:</b><br/>" + e.getDescription().replaceAll("\n","<br/>");
		return ret + retEnd;
	}

	/**
	 * Generates string to describes an amount of time.
	 * 
	 * @param m
	 *            Time in millis to describe.
	 * @author ZODVAAT.SZE
	 */
	private static String formatTime(long m) {
		int[] times = new int[4];
		int years = times[0] = (int) (m / (WeekView.HOUR_MILLIS * 24 * 365));
		m = (m % (WeekView.HOUR_MILLIS * 24 * 365));
		int days = times[1] = (int) (m / (WeekView.HOUR_MILLIS * 24));
		m = (m % (WeekView.HOUR_MILLIS * 24));
		int hours = times[2] = (int) (m / (WeekView.HOUR_MILLIS));
		m = (m % (WeekView.HOUR_MILLIS));
		int minutes = times[3] = (int) (m / (WeekView.HOUR_MILLIS / 60));
		m = (m % (WeekView.HOUR_MILLIS / 60));

		String ret = "";
		String[] names = new String[] { "year", "day", "hour", "minute" };

		boolean last = false;
		for (int i = 0; i < times.length; i++) {
			if (last) {
				if (times[i] != 0) {
					ret += " and ";
				} else {
					break;
				}
			}
			if (times[i] != 0) {
				ret += times[i] + " " + names[i];
				if (times[i] != 1) {
					ret += "s";
				}

				if (last) {
					break;
				}
				last = true;
			}
		}
		if (!last) {
			ret += "some seconds";
		}
		return ret;
	}

	/**
	 * Generates the string for date interval label.
	 * 
	 * @author ZODVAAT.SZE
	 * @param time Time interval to get.
	 * @return Timestamp.
	 */
	public static String getDateString(Interval time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time.getEndTimestamp());
		c.add(Calendar.DAY_OF_WEEK, -1);
		return time.getStartTimestamp().toString().split(" ")[0] + " --- "
				+ new Timestamp(c.getTimeInMillis()).toString().split(" ")[0];
		// return time.getStartTimestamp().toString().split(" ")[0] + " --- " +
		// time.getEndTimestamp().toString().split(" ")[0];
	}

	@Override
	public void destroy() {
	}
}
