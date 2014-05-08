package Res.GUI;

import java.awt.event.*;
import java.awt.Container;
import javax.swing.JButton;

import Res.*;
import Res.Bin.*;

public class SampleGUI implements GUIGenerator{
	
	private Window parent;
	private JButton btn;
	
	public void show(final Window parent, Container container){
		this.parent = parent;
		
		btn = new JButton("Test sample");
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				parent.refreshGUI(new WeekViewSampleGUI());
			}
		});
		
		container.add(btn);
	}
	
	public void destroy(){}
}
