package Res;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.sql.Timestamp;

import Res.Bin.*;
import Res.GUI.*;
import Res.GUI.Views.WeekView;

/**
 * Window class, which extends JFrame to whole GUI.
 * 
 * @author FENVABT.SZE
 */
public class Window extends JFrame{
	/** File's path. */
	String path;
	
	JMenuBar menu;
	JMenu fileMenu;
	JMenu editMenu;
	JMenu viewMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	JMenuItem reWriteItem;
	JMenuItem deleteItem;
	JMenuItem newItem;
	JMenuItem weekItem;
	JMenuItem monthItem;
	JFileChooser fileChooser;
	
	/**
	 * Default constructor, which make window.
	 */
	public Window(){
		super("Calendar");
		
		gui = null;
		GUIPanel = getContentPane();
		
		menu = new JMenuBar();
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open");
		openItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(null);
				path = fileChooser.getSelectedFile().getPath();
			}
		});
		saveItem = new JMenuItem("Save");
		saveItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fileChooser = new JFileChooser();
				fileChooser.showSaveDialog(null);
				path = fileChooser.getSelectedFile().getPath();
			}
		});
		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		editMenu = new JMenu("Edit");
		newItem = new JMenuItem("New");
		newItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				NewEditWindow n = new NewEditWindow();
				n.setVisible(true);
			}
		});
		reWriteItem = new JMenuItem("Rewrite");
		reWriteItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				RewriteEditWindow r = new RewriteEditWindow();
				r.setVisible(true);
			}
		});
		deleteItem = new JMenuItem("Delete");
		deleteItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			}
		});
		viewMenu = new JMenu("View");
		weekItem = new JMenuItem("Week");
		weekItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				refreshGUI(new WeekViewSampleGUI());
			}
		});
		monthItem = new JMenuItem("Month");
		/*monthItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				refreshGUI(new MonthViewSampleGUI());
			}
		});*/
		
		setJMenuBar(menu);
		menu.add(fileMenu);
		menu.add(editMenu);
		menu.add(viewMenu);
		fileMenu.add(openItem);
		fileMenu.addSeparator();
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		editMenu.add(newItem);
		editMenu.addSeparator();
		editMenu.add(reWriteItem);
		editMenu.addSeparator();
		editMenu.add(deleteItem);
		viewMenu.add(weekItem);
		viewMenu.addSeparator();
		viewMenu.add(monthItem);
		
		setLayout(new FlowLayout());
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Point location = new Point(400,300);
		setLocation(location);
		
		setResizable(false);
		
		//setting Window size according to WeekView's size
		int reqWidth = WeekView.getViewWidth();
		int reqHeight = WeekView.getViewHeight();
		setSize(reqWidth, reqHeight);
		Dimension actualSize = getContentPane().getSize();
		int extraW = 50;//reqWidth - actualSize.width;
		int extraH = 100;//reqHeight - actualSize.height;
		setSize(reqWidth + extraW, reqHeight + extraH);
		
		refreshGUI(new WeekViewSampleGUI()); //loads the first GUI
		setVisible(true);
	}
	
	private GUIGenerator gui;
	private Container GUIPanel; //Container for changable GUIs
	
	/**
	 * Sets contents of window.
	 * 
	 * @author Zombori Dániel
	 * @param gui Component holder class to show
	 */
	public void refreshGUI(GUIGenerator gui){
		if (this.gui != null) this.gui.destroy();
		GUIPanel.removeAll();
		
		this.gui = gui;
		gui.show(this,GUIPanel);
		
		validate();
		GUIPanel.repaint();
	}
}
