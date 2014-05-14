import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.DataFormatException;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import Res.Window;
import Res.Bin.CalendarEntry;
import Res.Data.DataHandler;
import Res.Data.DataModel;

/**
 * Main entry point of program
 * 
 * @author Zombori Dániel
 */
public class CalendarProgram {
	public static String path;

	/**
	 * Main entry point of program
	 * 
	 * @author Zombori Dániel
	 * @param args
	 *            Passes arguments via command line
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
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

		// Init BoxList
		Res.Data.DataModel.getTypeList().add("Work");
		Res.Data.DataModel.getTypeList().add("Family");
		Res.Data.DataModel.getTypeList().add("New");

		// Last calendar load
		BufferedReader read = new BufferedReader(new FileReader("Config.txt"));
		String line;
		path = null;
		try {
			while ((line = read.readLine()) != null) {
				path = line;
			}
		} catch (IOException exception) {
		}
		read.close();
		if (path != null) {
			List<CalendarEntry> list = new LinkedList<CalendarEntry>();
			try {
				DataHandler.readData(path, list);
				DataModel.getEntryList().resetList(list);
			} catch (IOException exception) {
				exception.printStackTrace();
			} catch (DataFormatException exception) {
			}
		}

		// Start GUI
		Window w = new Window(path);
	}
}
