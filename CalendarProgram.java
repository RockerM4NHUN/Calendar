import java.awt.Color;
import java.util.*;
import java.io.*;
import java.util.zip.DataFormatException;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import Res.Window;
import Res.Bin.*;
import Res.Data.*;
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

		// Start GUI
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
		
		
		String input;
		while(true){
			//*
			System.out.print("Press enter to write entries: ");
			input = System.console().readLine();
			if (input.equals("e")){
				System.exit(0);
			}
			
			// Test Save
			if (input.length() != 0){
				try{
					DataHandler.writeData("TestSave.csv",DataModel.getEntryList());
					System.out.println("Action performed");
				}catch(IOException e){
					System.err.println(e.getMessage());
				}/**/
			}
			
			System.out.print("Press enter to read entries: ");
			input = System.console().readLine();
			if (input.equals("e")){
				System.exit(0);
			}
			
			if (input.length() != 0){
				List<CalendarEntry> list = new LinkedList<CalendarEntry>();
				try{
					DataHandler.readData("TestSave.csv",list);
					DataModel.getEntryList().resetList(list);
					System.out.println("Action performed");
				}catch(IOException e){
					System.err.println("IOException");
					e.printStackTrace();
				}catch(DataFormatException e){
					System.err.println("DataFormatException");
					System.err.println(e.getMessage());
				}
			}
		}
	}
}
