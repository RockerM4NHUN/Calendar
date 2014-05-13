package Res.Edit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import Res.Bin.CalendarEntry;
import Res.Bin.Interval;
import Res.Data.*;
import Res.GUI.Views.CalendarView;

/**
 * New Edit Window to edit new events.
 * 
 * @author FENVABT.SZE
 * */
public class NewEditWindow extends JDialog {
	private static final long serialVersionUID = 1L;
	/** JLabels to show texts on the window. */
	private JLabel hourFromLabel;
	private JLabel hourTillLabel;
	private JLabel typeLabel;
	private JLabel titleLabel;
	private JLabel eventLabel;
	private JLabel newTypeLabel;
	private JLabel letterColorLabel;
	private JLabel backColorLabel;
	/** JTextFields to input data. */
	private JTextField titleField;
	private JTextArea eventField;
	private JTextField newTypeField;
	/** JSpinners to selected data. */
	private JSpinner yearDateFrom;
	private JSpinner monthDateFrom;
	private JSpinner dayDateFrom;
	private JSpinner yearDateTill;
	private JSpinner monthDateTill;
	private JSpinner dayDateTill;
	private JSpinner hourFrom;
	private JSpinner minFrom;
	private JSpinner hourTill;
	private JSpinner minTill;
	/** SpinnerModels to JSpinner. */
	private SpinnerModel yearFromM;
	private SpinnerModel monthFromM;
	private SpinnerModel dayFromM;
	private SpinnerModel yearTillM;
	private SpinnerModel monthTillM;
	private SpinnerModel dayTillM;
	private SpinnerModel hourFromM;
	private SpinnerModel hourTillM;
	private SpinnerModel minFromM;
	private SpinnerModel minTillM;
	/** JComboBox to selected data. */
	private JComboBox<String> boxType;
	/** JButtons to do actions. */
	private JButton selectLetterColor;
	private JButton selectBackColor;
	private JButton okButton;
	private JButton cancelButton;
	/** Instantiation classes. */
	private LetterColor lc;
	private BackColor bc;
	private Date date;
	private JScrollPane eventScroll;

	/**
	 * Default constructor to JDialog.
	 * */
	public NewEditWindow(final CalendarView usingCalendarView) {
		setTitle("New"); // Set title of window
		setModalityType(ModalityType.APPLICATION_MODAL); // Set modality of window

		date = new Date();

		yearFromM = new SpinnerNumberModel(date.getYear() + 1900, date.getYear() + 1900, 5000, 1); // Instantiation
																									// classes
		monthFromM = new SpinnerNumberModel(date.getMonth() + 1, 1, 12, 1);
		dayFromM = new SpinnerNumberModel(date.getDate(), 1, 31, 1);
		yearTillM = new SpinnerNumberModel(date.getYear() + 1900, date.getYear() + 1900, 5000, 1);
		monthTillM = new SpinnerNumberModel(date.getMonth() + 1, 1, 12, 1);
		dayTillM = new SpinnerNumberModel(date.getDate(), 1, 31, 1);
		hourFromM = new SpinnerNumberModel(date.getHours(), 0, 23, 1);
		hourTillM = new SpinnerNumberModel(date.getHours(), 0, 23, 1);
		minFromM = new SpinnerNumberModel(date.getMinutes(), 0, 59, 1);
		minTillM = new SpinnerNumberModel(date.getMinutes(), 0, 59, 1);
		hourFromLabel = new JLabel("Beginning of event:");
		hourTillLabel = new JLabel("End of event:");
		typeLabel = new JLabel("Type of event:");
		titleLabel = new JLabel("Title of event:");
		eventLabel = new JLabel("Event:");
		newTypeLabel = new JLabel("New type of entry:");
		letterColorLabel = new JLabel("Color of letters:");
		backColorLabel = new JLabel("Color of background:");
		yearDateFrom = new JSpinner(yearFromM);
		monthDateFrom = new JSpinner(monthFromM);
		dayDateFrom = new JSpinner(dayFromM);
		yearDateTill = new JSpinner(yearTillM);
		monthDateTill = new JSpinner(monthTillM);
		dayDateTill = new JSpinner(dayTillM);
		hourFrom = new JSpinner(hourFromM);
		minFrom = new JSpinner(minFromM);
		hourTill = new JSpinner(hourTillM);
		minTill = new JSpinner(minTillM);
		titleField = new JTextField();
		eventField = new JTextArea();
		newTypeField = new JTextField();
		boxType = new JComboBox<String>(Res.Data.DataModel.getTypeList().toArray(new String[0]));
		selectLetterColor = new JButton();
		selectBackColor = new JButton();
		//selectLetterColor = new JButton("Select color of letters");
		//selectBackColor = new JButton("Select color of background");
		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");
		lc = new LetterColor();
		bc = new BackColor();

		hourFromLabel.setBounds(10, 10, 200, 20); // Set bounds all of item in window
		yearDateFrom.setBounds(10, 30, 65, 30);
		monthDateFrom.setBounds(75, 30, 50, 30);
		dayDateFrom.setBounds(125, 30, 50, 30);
		hourFrom.setBounds(200, 30, 50, 30);
		minFrom.setBounds(250, 30, 50, 30);
		hourTillLabel.setBounds(10, 70, 200, 20);
		yearDateTill.setBounds(10, 90, 65, 30);
		monthDateTill.setBounds(75, 90, 50, 30);
		dayDateTill.setBounds(125, 90, 50, 30);
		hourTill.setBounds(200, 90, 50, 30);
		minTill.setBounds(250, 90, 50, 30);
		typeLabel.setBounds(10, 130, 200, 20);
		boxType.setBounds(10, 150, 100, 30);
		newTypeLabel.setBounds(10, 130, 200, 20);
		newTypeField.setBounds(10, 150, 100, 30);
		titleLabel.setBounds(10, 190, 200, 20);
		titleField.setBounds(10, 210, 150, 30);
		eventLabel.setBounds(10, 250, 200, 20);
		//eventField.setBounds(10, 270, 365, 100);
		eventScroll = new JScrollPane(eventField,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		eventScroll.setBounds(10, 270, 365, 100);
		letterColorLabel.setBounds(10, 380, 140, 30);
		backColorLabel.setBounds(10, 410, 140, 30);
		selectLetterColor.setBounds(150, 380, 50, 30);
		selectBackColor.setBounds(150, 410, 50, 30);
		//selectLetterColor.setBounds(10, 310, 200, 30);
		//selectBackColor.setBounds(220, 310, 200, 30);
		okButton.setBounds(260, 435, 50, 30);
		cancelButton.setBounds(320, 435, 100, 30);

		selectLetterColor.setBackground(lc.getLetterColor());
		selectBackColor.setBackground(bc.getBackColor());
		

		boxType.addItemListener(new ItemListener() { // Item listener to box list
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (((String) e.getItem()).equals("New")) { // If select new, you can make new type of event
					remove(typeLabel); // If make new type, delete box list
					remove(boxType);
					add(newTypeLabel);
					add(newTypeField);
					repaint();
				}
			}
		});

		selectLetterColor.addActionListener(new ActionListener() { // Action listener to color select button
					@Override
					public void actionPerformed(ActionEvent e) { // If push the button show letter color window to
																	// choose color
						lc.setVisible(true);
						selectLetterColor.setBackground(lc.getLetterColor());
					}
				});

		selectBackColor.addActionListener(new ActionListener() {// Action listener to color select button
					@Override
					public void actionPerformed(ActionEvent e) { // If push the button show background color window to
																	// choose color
						bc.setVisible(true);
						selectBackColor.setBackground(bc.getBackColor());
					}
				});

		okButton.addActionListener(new ActionListener() { // Action listener to Ok button
			@Override
			public void actionPerformed(ActionEvent e) {
				Calendar fromDate = Calendar.getInstance();
				fromDate.set((int) yearDateFrom.getValue(), (int) monthDateFrom.getValue() - 1,
						(int) dayDateFrom.getValue(), (int) hourFrom.getValue(), (int) minFrom.getValue());
				Calendar tillDate = Calendar.getInstance();
				tillDate.set((int) yearDateTill.getValue(), (int) monthDateTill.getValue() - 1,
						(int) dayDateTill.getValue(), (int) hourTill.getValue(), (int) minTill.getValue());
				if ((int) yearDateFrom.getValue() == (int) yearDateTill.getValue()
						&& (int) monthDateFrom.getValue() == (int) monthDateTill.getValue()
						&& (int) dayDateFrom.getValue() == (int) dayDateTill.getValue()
						&& (int) hourFrom.getValue() == (int) hourTill.getValue()
						&& (int) minFrom.getValue() == (int) minTill.getValue()) { // If have any error show error
																					// window
					JOptionPane.showMessageDialog(getContentPane(), "Begening of the event equals end of event!",
							"Date Error", JOptionPane.ERROR_MESSAGE);
				} else if (((int) yearDateFrom.getValue() > (int) yearDateTill.getValue())
						|| ((int) yearDateFrom.getValue() == (int) yearDateTill.getValue() && (int) monthDateFrom
								.getValue() > (int) monthDateTill.getValue())
						|| ((int) yearDateFrom.getValue() == (int) yearDateTill.getValue()
								&& (int) monthDateFrom.getValue() == (int) monthDateTill.getValue() && (int) dayDateFrom
								.getValue() > (int) dayDateTill.getValue())
						|| ((int) yearDateFrom.getValue() == (int) yearDateTill.getValue()
								&& (int) monthDateFrom.getValue() == (int) monthDateTill.getValue()
								&& (int) dayDateFrom.getValue() == (int) dayDateTill.getValue() && (int) hourFrom
								.getValue() > (int) hourTill.getValue())
						|| ((int) yearDateFrom.getValue() == (int) yearDateTill.getValue()
								&& (int) monthDateFrom.getValue() == (int) monthDateTill.getValue()
								&& (int) dayDateFrom.getValue() == (int) dayDateTill.getValue()
								&& (int) hourFrom.getValue() == (int) hourTill.getValue() && (int) minFrom.getValue() > (int) minTill
								.getValue())) {
					JOptionPane.showMessageDialog(getContentPane(),
							"Begening of the event mustn't be leater than the and of it!", "Date Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (newTypeField.getText().equals("")) { // If no have new type make a new Entry with choose type
					Res.Data.DataModel.getEntryList().add(
							new CalendarEntry(new Interval(fromDate.getTimeInMillis(), tillDate.getTimeInMillis()),
									(String) boxType.getSelectedItem(), titleField.getText(), lc.getLetterColor(), bc
											.getBackColor(), eventField.getText()));
					usingCalendarView.repaint();
					dispose();
				} else { // Else make new Entry with new tpye
					Object o = new Object();
					CalendarEntry entry = new CalendarEntry();
					entry.setInterval(new Interval(fromDate.getTimeInMillis(), tillDate.getTimeInMillis()));
					entry.setType(newTypeField.getText());
					entry.setTitle(titleField.getText());
					entry.setForegroundColor((lc.change) ? lc.getLetterColor() : CalendarEntry.DEFAULT_FOREGROUND_COLOR);
					entry.setBackgroundColor((bc.change) ? bc.getBackColor() : CalendarEntry.DEFAULT_BACKGROUND_COLOR);
					entry.setDescription(eventField.getText());

					boolean faild = false;
					for (String str : DataModel.getTypeList()) {
						if (str.equals(newTypeField.getText())) {
							faild = true;
							entry.setType(str);
							break;
						}
					}
					if (!faild) {
						DataModel.getTypeList().add(newTypeField.getText());
					}

					// modification finished
					DataModel.getEntryList().add(entry);
					dispose();
				}
			}
		});

		cancelButton.addActionListener(new ActionListener() { // Action listener to cancel button
					@Override
					public void actionPerformed(ActionEvent e) { // If push the button close the window
						dispose();
					}
				});
		
		
		add(hourFromLabel); // Ad items to JDialog
		add(yearDateFrom);
		add(monthDateFrom);
		add(dayDateFrom);
		add(hourFrom);
		add(minFrom);
		add(hourTillLabel);
		add(yearDateTill);
		add(monthDateTill);
		add(dayDateTill);
		add(hourTill);
		add(minTill);
		add(typeLabel);
		add(boxType);
		add(titleLabel);
		add(titleField);
		add(eventLabel);
		add(eventScroll);
		add(letterColorLabel);
		add(backColorLabel);
		add(selectLetterColor);
		add(selectBackColor);
		add(okButton);
		add(cancelButton);

		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Point location = new Point(400, 300);
		setLocation(location);
		setResizable(false);
		setSize(450, 500);
	}
}
