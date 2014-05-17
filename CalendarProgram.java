import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.*;
import java.util.zip.DataFormatException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import Res.Window;
import Res.Bin.CalendarEntry;
import Res.Data.*;

/**
 * Main entry point of program
 * 
 * @author ZODVAAT.SZE
 * @author FENVABT.SZE
 */
public class CalendarProgram {
	public static String path;
	
	public static final String configPath = "Config.txt";
	
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
		DataModel.getTypeList().addAll(Arrays.asList(DataModel.getDefaultTypeArray()));

		// Last calendar load if exists
		path = null;
		if (new File(configPath).exists()){
			BufferedReader read = new BufferedReader(new FileReader(configPath));
			String line;
			try {
				while ((line = read.readLine()) != null) {
					path = line;
				}
			} catch (IOException exception) {
			}
			read.close();
		}
		
		// Start GUI
		Window w = new Window(path);
		
		if (path != null) {
			boolean rmConfig = false;
			List<CalendarEntry> list = new LinkedList<CalendarEntry>();
			try {
				DataHandler.readData(path, list);
				DataModel.getEntryList().resetList(list);
			} catch (IOException exception) {
				exception.printStackTrace();
				//JOptionPane.showMessageDialog(null,"Could not open save file.","Read error",JOptionPane.ERROR_MESSAGE);
			} catch (DataFormatException exception) {
				exception.printStackTrace();
				//JOptionPane.showMessageDialog(null,"File is corrupted.","Read error",JOptionPane.ERROR_MESSAGE);
			}
			if (rmConfig){
				new File(configPath).delete();
			}
		}

	}
}
