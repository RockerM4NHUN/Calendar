package Res;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class NewEditWindow extends JFrame {
	JLabel dateLabel;
	JLabel hourFromLabel;
	JLabel hourTillLabel;
	JLabel typeLabel;
	JLabel titleLabel;
	JLabel eventLabel;
	JLabel newTypeLabel;
	JTextField dateField;
	JTextField hourFromField;
	JTextField hourTillField;
	JTextField titleField;
	JTextField eventField;
	JTextField newTypeField;
	JComboBox boxType;
	JColorChooser colorChooser;
	JButton selectLeterColor;
	JButton selectBackColor;
	JButton okButton;
	JButton cancleButton;

	NewEditWindow() {
		super("New");

		dateLabel = new JLabel("The date of event:");
		hourFromLabel = new JLabel("Beginning of event:");
		hourTillLabel = new JLabel("End of event:");
		typeLabel = new JLabel("Type of event:");
		titleLabel = new JLabel("Title of event:");
		eventLabel = new JLabel("Event:");
		newTypeLabel = new JLabel("New type of entry:");
		dateField = new JTextField("0000-00-00");
		hourFromField = new JTextField("00:00");
		hourTillField = new JTextField("00:00");
		titleField = new JTextField();
		eventField = new JTextField();
		newTypeField = new JTextField();
		boxType = new JComboBox();
		colorChooser = new JColorChooser();
		selectLeterColor = new JButton("Select color of letters");
		selectBackColor = new JButton("Select color of background");
		okButton = new JButton("Ok");
		cancleButton = new JButton("Cancle");

		boxType.addItem("Meeting");
		boxType.addItem("Work");
		boxType.addItem("Family");
		boxType.addItem("New");

		dateLabel.setBounds(10, 10, 200, 20);
		dateField.setBounds(10, 30, 80, 30);
		hourFromLabel.setBounds(10, 60, 200, 20);
		hourFromField.setBounds(10, 80, 50, 30);
		hourTillLabel.setBounds(10, 110, 200, 20);
		hourTillField.setBounds(10, 130, 50, 30);
		typeLabel.setBounds(10, 160, 200, 20);
		boxType.setBounds(10, 180, 100, 30);
		newTypeLabel.setBounds(10, 160, 200, 20);
		newTypeField.setBounds(10, 180, 100, 30);
		titleLabel.setBounds(10, 210, 200, 20);
		titleField.setBounds(10, 230, 150, 30);
		eventLabel.setBounds(10, 260, 200, 20);
		eventField.setBounds(10, 280, 365, 30);
		selectLeterColor.setBounds(10, 330, 200, 30);
		selectBackColor.setBounds(220, 330, 200, 30);
		okButton.setBounds(260, 400, 50, 30);
		cancleButton.setBounds(320, 400, 100, 30);

		boxType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (((String) e.getItem()).equals("New")) {
					remove(typeLabel);
					remove(boxType);
					add(newTypeLabel);
					add(newTypeField);
					repaint();
				}
			}
		});

		add(dateLabel);
		add(dateField);
		add(hourFromLabel);
		add(hourFromField);
		add(hourTillLabel);
		add(hourTillField);
		add(typeLabel);
		add(boxType);
		add(titleLabel);
		add(titleField);
		add(eventLabel);
		add(eventField);
		add(selectLeterColor);
		add(selectBackColor);
		add(okButton);
		add(cancleButton);

		// colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
		// @Override
		// public void stateChanged(ChangeEvent e) {
		// Color newColor = colorChooser.getColor();
		// System.out.println(newColor);
		// }
		// });

		// add(colorChooser);

		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setSize(450, 500);
	}
}