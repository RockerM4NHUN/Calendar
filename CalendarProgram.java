import java.awt.Color;
import java.util.Date;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import Res.Window;
import Res.Bin.CalendarEntry;
import Res.Bin.EventedList;
import Res.Bin.Interval;
import Res.Data.DataModel;
import Res.GUI.Views.WeekView;

/**
 * Main entry point of program
 * 
 * @author Zombori Dániel
 */
public class CalendarProgram {

	/**
	 * Main entry point of program
	 * 
	 * @author Zombori Dániel
	 * @param args
	 *            Passes arguments via command line
	 */
	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("Error: Nimbus LAF not available");
			System.exit(1);
		}

		// belépés az ablakos rendszerbe
		Window w = new Window();

		// Init BoxList
		Res.Data.DataModel.getTypeList().add("Work");
		Res.Data.DataModel.getTypeList().add("Family");
		Res.Data.DataModel.getTypeList().add("New");

		// Test Entries
		Date now = new Date(System.currentTimeMillis() - WeekView.HOUR_MILLIS * 48 * 1);
		EventedList<CalendarEntry> elist = DataModel.getEntryList();

		elist.add(new CalendarEntry(new Interval(now.getTime() - WeekView.HOUR_MILLIS * 5, now.getTime()
				- WeekView.HOUR_MILLIS * 2), "TestType0", "Long Title0", Color.BLACK, new Color(255, 200, 80),
				"Test Description0"));

		elist.add(new CalendarEntry(new Interval(now.getTime() - WeekView.HOUR_MILLIS * 2, now.getTime()
				+ WeekView.HOUR_MILLIS * 36), "TestType1", "Long Title1", Color.BLACK, new Color(255, 200, 80),
				"Test Description1"));
		elist.add(new CalendarEntry(new Interval(now.getTime() - WeekView.HOUR_MILLIS * 2, now.getTime()
				- WeekView.HOUR_MILLIS * 1), "TestType2", "Long Title2", Color.BLACK, new Color(200, 200, 80),
				"Test Description2"));
		elist.add(new CalendarEntry(new Interval(now.getTime() - WeekView.HOUR_MILLIS * 0, now.getTime()
				+ WeekView.HOUR_MILLIS * 2), "TestType3", "Long Title3", CalendarEntry.DEFAULT_FOREGROUND_COLOR,
				CalendarEntry.DEFAULT_BACKGROUND_COLOR, "Test Description3"));
		elist.add(new CalendarEntry(new Interval(now.getTime() - WeekView.HOUR_MILLIS * 2, now.getTime()
				+ WeekView.HOUR_MILLIS * 0), "TestType4", "Long Title4", Color.BLACK, new Color(255, 200, 80),
				"Test Description4"));
		elist.add(new CalendarEntry(new Interval(now.getTime() - WeekView.HOUR_MILLIS * 1, now.getTime()
				+ WeekView.HOUR_MILLIS * 2), "TestType5", "Long Title5", Color.BLACK, new Color(255, 200, 80),
				"Test Description5"));

	}
}
