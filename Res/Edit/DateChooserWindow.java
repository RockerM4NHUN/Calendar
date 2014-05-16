package Res.Edit;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class DateChooserWindow extends JDialog{
	
	private JSpinner years;
	private JSpinner months;
	private JSpinner days;
	
	private static final String[] monthNames = new String[]{"December","November","October","September","August","July","June","May","April","March","February","January"};
	
	public DateChooserWindow() {
		setVisible(false);
		setTitle("Choose date"); // Set title of window
		setModalityType(ModalityType.APPLICATION_MODAL); // Set modality of window
		
		setLayout(null);
		Point location = new Point(400, 300);
		setLocation(location);
		setResizable(false);
		setSize(340, 90);
	}
	
	public void show(final ActionListener l){
		years = new JSpinner(new SpinnerNumberModel(new Date(System.currentTimeMillis()).getYear() + 1900, 1970, 5000, 1));
		months = new JSpinner(new SpinnerListModel(monthNames));
		days = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
		
		
		JButton btnOK = new JButton("Ok");
		btnOK.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				l.actionPerformed(null);
				dispose();
			}
		});
		JButton btnCancel = new JButton("Cancel");
		btnOK.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		years.setBounds(10,10,80,30);
		months.setBounds(100,10,140,30);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		months.setValue(monthNames[11 - (c.get(Calendar.MONTH))]);
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
	
	public Date getDate(){
		Calendar c = Calendar.getInstance();
		
		c.set(Calendar.YEAR, (int)years.getValue());
		c.set(Calendar.MONTH, 11 - Arrays.asList(monthNames).indexOf(months.getValue()));
		c.set(Calendar.DAY_OF_MONTH, (int)days.getValue());
		c.set(Calendar.AM_PM, Calendar.AM);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
}
