package Res.Edit;

import java.awt.*;
import java.awt.Point;

import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Letter Color window to choose letter color.
 * 
 * @author FENVABT.SZE
 * */
public class LetterColor extends JDialog {
	/** letterColor to letter color */
	private Color letterColor = Res.Bin.CalendarEntry.DEFAULT_FOREGROUND_COLOR;
	public boolean change = false;

	/** JColorChooser to choose color. */
	private JColorChooser colorChooser;

	/**
	 * Default constructor to JDialog.
	 */
	public LetterColor() {
		setTitle("Letter Color"); // Set title of window
		setModalityType(ModalityType.APPLICATION_MODAL); // Set modality of window

		colorChooser = new JColorChooser(); // Instantiation JColorChooser class
		change = false;
		colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {// Change listener to color chooser
					@Override
					public void stateChanged(ChangeEvent e) { // If choose color, store the new color
						letterColor = colorChooser.getColor();
						change = true;
					}
				});

		colorChooser.setBounds(0, 0, 694, 450); // Set bounds of color chooser

		add(colorChooser); // Ad color chooser to JDialog

		setLayout(null);
		Point location = new Point(400, 300);
		setLocation(location);
		setResizable(false);
		//setSize(700, 500);
		getContentPane().setPreferredSize(new Dimension(700, 500));
		pack();
	}

	/**
	 * Getter to get letter color.
	 * 
	 * @return letterColor
	 * */
	public Color getLetterColor() {
		return letterColor;
	}

	/**
	 * Setter to set letter color.
	 * 
	 * @param letterColor
	 * */
	public void setLetterColor(Color letterColor) {
		this.letterColor = letterColor;
	}
}
