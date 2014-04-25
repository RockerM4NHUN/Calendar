package Res;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Window extends JFrame{
	public Window(){
		super("Calendar");
		setLayout(new FlowLayout());
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Point location = new Point(400,300);
		setLocation(location);
		
		setResizable(false);
		setSize(400,300);
		
		setVisible(true);
		
		add(new JButton("Hello"));
	}
}
