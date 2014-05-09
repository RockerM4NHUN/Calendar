package Res.Edit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * Bad data window to error.
 * 
 * @author FENVABT.SZE
 * */
public class BadData extends JDialog {
	/** JLabel to show text. */
	private JLabel errorLabel;

	/**
	 * Default constructor to JDialog.
	 * */
	public BadData() {
		setTitle("Error"); // Set title of window
		setModalityType(ModalityType.APPLICATION_MODAL); // Set modality of window

		errorLabel = new JLabel("Bad data!", JLabel.CENTER); // Instantiation JLabel
		errorLabel.setForeground(Color.red); // Set color
		errorLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font

		add(errorLabel); // Add JLabel

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Point location = new Point(400, 300);
		setLocation(location);
		setResizable(false);
		setSize(200, 100);
	}
}