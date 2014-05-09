package Res.Edit;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Background Color window to choose background color.
 * 
 * @author FENVABT.SZE
 * */
public class BackColor extends JDialog {
	/** backColor to store new color */
	private Color backColor = new Color(0, 0, 0);

	private JColorChooser colorChooser; // Instantiation JColorChooser

	/**
	 * Default constructor to JDialog.
	 */
	public BackColor() {
		setTitle("Back Color"); // Set title of window
		setModalityType(ModalityType.APPLICATION_MODAL); // Set modality of window

		colorChooser = new JColorChooser();

		colorChooser.getSelectionModel().addChangeListener(new ChangeListener() { // Change listener to color chooser
					@Override
					public void stateChanged(ChangeEvent e) { // If choose color, store the new color
						backColor = colorChooser.getBackground();
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
	 * Getter to get background color.
	 * 
	 * @return backColor
	 * */
	public Color getBackColor() {
		return backColor;
	}

	/**
	 * Setter to set background color.
	 * 
	 * @param backColor
	 * */
	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}
}