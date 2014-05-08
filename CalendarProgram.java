
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import Res.Window;

/**
 * Main entry point of program
 * @author Zombori Dániel
 */
public class CalendarProgram{
	
	/**
	 * Main entry point of program
	 * @author Zombori Dániel
	 * @param args	Passes arguments via command line
	 */
	public static void main(String[] args){
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
		
		//belépés az ablakos rendszerbe
		Window w = new Window();
	}
}
