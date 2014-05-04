package Res;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class NewEditWindow extends JFrame{
	JLabel dateLabel;
	JLabel hourFromLabel;
	JLabel hourTillLabel;
	JLabel eventLabel;
	JTextField dateField;
	JTextField hourFromField;
	JTextField hourTillFiled;
	JTextField eventField;
	JButton okButton;
	JButton cancleButton;
	
	NewEditWindow(){
		super("New");
		
		dateLabel = new JLabel("The date of event:");
		hourFromLabel = new JLabel("Beginning of event:");
		hourTillLabel = new JLabel("End of event:");
		eventLabel = new JLabel("Event:");
		dateField = new JTextField();
		hourFromField = new JTextField();
		hourTillFiled = new JTextField();
		eventField = new JTextField();
		okButton = new JButton("Ok");
		cancleButton = new JButton("Cancle");
		
		dateLabel.setBounds(10, 10, 50, 100);
		dateField.setBounds(10, 70, 50, 100);
		
		add(dateLabel);
		add(dateField);
		add(okButton);
		add(cancleButton);
		
		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setSize(400,400);
	}
}