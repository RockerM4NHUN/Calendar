package Res.Edit;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * Delete Edit Window to delete entry.
 * 
 * @author FENVABT.SZE
 * */
public class DeleteEditWindow extends JDialog {
	/** JLabel to show text. */
	private JLabel sure;
	/** JButton to actions. */
	private JButton yesButton;
	private JButton noButton;

	/** Default constructor to JDialog. */
	public DeleteEditWindow() {
		setTitle("Sure"); // Set title of window.
		setModalityType(ModalityType.APPLICATION_MODAL); // Set modality of window

		sure = new JLabel("Are you sure to delete it?"); // Instantiation JLabel
		sure.setFont(new Font("Arial", Font.BOLD, 18)); // Set font
		yesButton = new JButton("Yes"); // Instantiation JButtons
		noButton = new JButton("No");

		yesButton.addActionListener(new ActionListener() { // Action listener to yes button
					@Override
					public void actionPerformed(ActionEvent e) { // If push the button delete entry
						Res.Data.DataModel.getEntryList().remove(Res.GUI.ViewGUI.selectedEntry);
						Res.GUI.ViewGUI.w.repaint();
						dispose();
					}
				});

		noButton.addActionListener(new ActionListener() { // Action listener to no button
			@Override
			public void actionPerformed(ActionEvent e) { // If push the button do nothing
				dispose();
			}
		});

		sure.setBounds(35, 25, 250, 20); // Set bounds
		yesButton.setBounds(90, 65, 50, 30);
		noButton.setBounds(150, 65, 50, 30);

		add(sure); // Add items to window
		add(yesButton);
		add(noButton);

		setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Point location = new Point(400, 300);
		setLocation(location);
		setResizable(false);
		setSize(300, 150);
	}
}
