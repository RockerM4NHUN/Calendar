package Res.Edit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * No select window to error.
 * 
 * @author FENVABT.SZE
 * */
public class NoSelect extends JDialog {
	/** JLabel to show text. */
	private JLabel errorLabel;

	/**
	 * Default constructor to JDialog.
	 * */
	public NoSelect() {
		setTitle("Error"); // Set title of window
		setModalityType(ModalityType.APPLICATION_MODAL); // Set modality of window

		errorLabel = new JLabel("No selected event!", JLabel.CENTER); // Instantiation JLabel
		errorLabel.setForeground(Color.red); // Set color
		errorLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font

		add(errorLabel); // Add JLabel

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Point location = new Point(400, 300);
		setLocation(location);
		setResizable(false);
		setSize(300, 100);
	}
}