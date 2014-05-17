package Res.Edit;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

/**
 * Displays a window for choosing date.
 * 
 * @author ZODVAAT.SZE
 */
public class DateChooserWindow extends JDialog{
	
	private JSpinner years;
	private JSpinner months;
	private JSpinner days;
	
	private static String[] monthNamesBackwards;
	private static int[] monthDaysBackwards;
	private int direction;
	
	/**
	 * Constructor with direction controlling.
	 * 
	 * @param forward Specifies if the time increases when user push arrows pointing upwards.
	 * @author ZODVAAT.SZE
	 */
	public DateChooserWindow(boolean forward) {
		setVisible(false);
		setTitle("Choose date"); // Set title of window
		setModalityType(ModalityType.APPLICATION_MODAL); // Set modality of window
		
		if (forward){
			direction = 1;
			monthNamesBackwards = new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};
			monthDaysBackwards = new int[]{31,28,31,30,31,31,30,31,30,31,30,31};
		}else{
			direction = -1;
			monthNamesBackwards = new String[]{"December","November","October","September","August","July","June","May","April","March","February","January"};
			monthDaysBackwards = new int[]{31,30,31,30,31,31,30,31,30,31,28,31};
		}
		
		setLayout(null);
		Point location = new Point(400, 300);
		setLocation(location);
		setResizable(false);
		getContentPane().setPreferredSize(new Dimension(340, 90));
		pack();
	}
	
	/**
	 * Displays the dialog and if the user press the Ok button fires the ActionListener.
	 * 
	 * @param l Action listener for callback.
	 * @author ZODVAAT.SZE
	 */
	public void show(final ActionListener l){
		years = new JSpinner(new SpinnerNumberModel(new Date(System.currentTimeMillis()).getYear() + 1900, 1970, 5000, direction));
		months = new JSpinner(new SpinnerListModel(monthNamesBackwards));
		days = new JSpinner(new SpinnerNumberModel(1, 1, 31, direction));
		
		months.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				int year = (int)years.getValue();
				int monthBackward = Arrays.asList(monthNamesBackwards).indexOf(months.getValue());
				int day = (int)days.getValue();
				
				int max;
				
				boolean isLeapYear = ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
				
				if (monthBackward == 11 - 1 && isLeapYear){
					max = 29;
				}else{
					max = monthDaysBackwards[monthBackward];
				}
				
				if (max < day){
					days.setValue(max);
				}
			}
		});
		days.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				int year = (int)years.getValue();
				int monthBackward = Arrays.asList(monthNamesBackwards).indexOf(months.getValue());
				int day = (int)days.getValue();
				
				int max;
				
				boolean isLeapYear = ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
				
				if (monthBackward == 11 - 1 && isLeapYear){
					max = 29;
				}else{
					max = monthDaysBackwards[monthBackward];
				}
				
				if (max < day){
					days.setValue(max);
				}
			}
		});
		
		JButton btnOK = new JButton("Ok");
		btnOK.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				l.actionPerformed(null);
				dispose();
			}
		});
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		years.setBounds(10,10,80,30);
		months.setBounds(100,10,140,30);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		months.setValue(monthNamesBackwards[11 * ((1 - direction) / 2) + direction * (c.get(Calendar.MONTH))]);
		days.setBounds(250,10,80,30);
		days.setValue(c.get(Calendar.DAY_OF_MONTH));
		
		btnOK.setBounds(70,50,90,30);
		btnCancel.setBounds(170,50,90,30);
		
		add(years);
		add(months);
		add(days);
		add(btnOK);
		add(btnCancel);
		
		setVisible(true);
	}
	
	/**
	 * @author ZODVAAT.SZE
	 * @return The choosen date.
	 */
	public Date getDate(){
		Calendar c = Calendar.getInstance();
		
		c.set(Calendar.YEAR, (int)years.getValue());
		c.set(Calendar.MONTH, 11 * ((1 - direction) / 2) + direction * Arrays.asList(monthNamesBackwards).indexOf(months.getValue()));
		c.set(Calendar.DAY_OF_MONTH, (int)days.getValue());
		c.set(Calendar.AM_PM, Calendar.AM);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
}
