package Res.Edit;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import Res.Data.*;
import Res.Bin.*;

/**
 * Rewriter Edit Window to edit events.
 * 
 * @author FENVABT.SZE
 * */
public class RewriteEditWindow extends JDialog {
	/** JLabels to show texts on the window. */
	private JLabel hourFromLabel;
	private JLabel hourTillLabel;
	private JLabel typeLabel;
	private JLabel titleLabel;
	private JLabel eventLabel;
	/** JTextFields to input data. */
	private JTextField titleField;
	private JTextField eventField;
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
	/** JButtons to do actions. */
	private JButton selectLetterColor;
	private JButton selectBackColor;
	private JButton okButton;
	private JButton cancelButton;
	/** Instantiation classes. */
	private LetterColor lc;
	private BackColor bc;
	private Date date;

	/**
	 * Default constructor to JDialog.
	 * */
	public RewriteEditWindow(final CalendarEntry entry) {
		setTitle("New"); // Set title of window
		setModalityType(ModalityType.APPLICATION_MODAL); // Set modality of window

		date = new Date();
		Date from = new Date();
		Date till = new Date();
		Interval interval = entry.getInterval();
		from.setTime(interval.getStart());
		till.setTime(interval.getEnd());

		yearFromM = new SpinnerNumberModel(from.getYear() + 1900, date.getYear() + 1900, 5000, 1); // Instantiation
																									// classes
		monthFromM = new SpinnerNumberModel(from.getMonth() + 1, 1, 12, 1);
		dayFromM = new SpinnerNumberModel(from.getDate(), 1, 31, 1);
		yearTillM = new SpinnerNumberModel(till.getYear() + 1900, date.getYear() + 1900, 5000, 1);
		monthTillM = new SpinnerNumberModel(till.getMonth() + 1, 1, 12, 1);
		dayTillM = new SpinnerNumberModel(till.getDate(), 1, 31, 1);
		hourFromM = new SpinnerNumberModel(from.getHours(), 0, 23, 1);
		hourTillM = new SpinnerNumberModel(till.getHours(), 0, 23, 1);
		minFromM = new SpinnerNumberModel(from.getMinutes(), 0, 59, 1);
		minTillM = new SpinnerNumberModel(till.getMinutes(), 0, 59, 1);
		hourFromLabel = new JLabel("Beginning of event:");
		hourTillLabel = new JLabel("End of event:");
		typeLabel = new JLabel("Type of event:");
		titleLabel = new JLabel("Title of event:");
		eventLabel = new JLabel("Event:");
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
		titleField = new JTextField(entry.getTitle());
		eventField = new JTextField(entry.getDescription());
		newTypeField = new JTextField(entry.getType());
		selectLetterColor = new JButton("Select color of letters");
		selectBackColor = new JButton("Select color of background");
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
		newTypeField.setBounds(10, 150, 100, 30);
		titleLabel.setBounds(10, 190, 200, 20);
		titleField.setBounds(10, 210, 150, 30);
		eventLabel.setBounds(10, 250, 200, 20);
		eventField.setBounds(10, 270, 365, 30);
		selectLetterColor.setBounds(10, 310, 200, 30);
		selectBackColor.setBounds(220, 310, 200, 30);
		okButton.setBounds(260, 365, 50, 30);
		cancelButton.setBounds(320, 365, 100, 30);

		selectLetterColor.addActionListener(new ActionListener() { // Action listener to color select button
					@Override
					public void actionPerformed(ActionEvent e) { // If push the button show letter color window to
																	// choose color
						lc.setVisible(true);
					}
				});

		selectBackColor.addActionListener(new ActionListener() {// Action listener to color select button
					@Override
					public void actionPerformed(ActionEvent e) { // If push the button show background color window to
																	// choose color
						bc.setVisible(true);
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
						(int) dayDateFrom.getValue(), (int) hourTill.getValue(), (int) minTill.getValue());

				if (((int) yearDateFrom.getValue() == (int) yearDateTill.getValue()
						&& (int) monthDateFrom.getValue() == (int) monthDateTill.getValue()
						&& (int) dayDateFrom.getValue() == (int) dayDateTill.getValue()
						&& (int) hourFrom.getValue() == (int) hourTill.getValue() && (int) minFrom.getValue() == (int) minTill
						.getValue())
						|| ((int) yearDateFrom.getValue() > (int) yearDateTill.getValue())
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
								.getValue())) { // If have any error show error window
					BadData error = new BadData();
					error.setVisible(true);
				} else { // Else rewrite entry
					
					TODO "allitsd be az osszes adattagot"
					
					entry.setTitle(titleField.getText());
					entry.setForegroundColor(lc.getLetterColor());
					entry.setBackgroundColor(bc.getBackColor());
					
					//modification finished
					EventedList<CalendarEntry> elist = DataModel.getEntryList();
					elist.remove(entry);
					elist.add(entry);
					
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
		add(newTypeField);
		add(titleLabel);
		add(titleField);
		add(eventLabel);
		add(eventField);
		add(selectLetterColor);
		add(selectBackColor);
		add(okButton);
		add(cancelButton);

		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Point location = new Point(400, 300);
		setLocation(location);
		setResizable(false);
		setSize(450, 450);
	}
}
