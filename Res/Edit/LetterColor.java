package Res.Edit;

import java.awt.Color;
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
	private Color letterColor = new Color(255, 255, 255);

	/** JColorChooser to choose color. */
	private JColorChooser colorChooser;

	/**
	 * Default constructor to JDialog.
	 */
	public LetterColor() {
		setTitle("Letter Color"); // Set title of window
		setModalityType(ModalityType.APPLICATION_MODAL); // Set modality of window

		colorChooser = new JColorChooser(); // Instantiation JColorChooser class

		colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {// Change listener to color chooser
					@Override
					public void stateChanged(ChangeEvent e) { // If choose color, store the new color
						letterColor = colorChooser.getBackground();
					}
				});

		colorChooser.setBounds(0, 0, 694, 450); // Set bounds of color chooser

		add(colorChooser); // Ad color chooser to JDialog

		setLayout(null);
		Point location = new Point(400, 300);
		setLocation(location);
		setResizable(false);
		setSize(700, 500);
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