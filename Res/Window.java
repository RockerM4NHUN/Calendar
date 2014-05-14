package Res;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.DataFormatException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Res.Bin.CalendarEntry;
import Res.Data.DataHandler;
import Res.Data.DataModel;
import Res.Edit.NewEditWindow;
import Res.Edit.RewriteEditWindow;
import Res.GUI.ViewGUI;
import Res.GUI.Views.CalendarView;
import Res.GUI.Views.WeekView;

/**
 * Window class, which extends JFrame to whole GUI.
 * 
 * @author ZODVAAT.SZE / FENVABT.SZE
 */
public class Window extends JFrame {
	/** File's path. */
	private String openPath;
	private String savePath;
	/** Now using calendar view. */
	private CalendarView usingCalendarView;
	/** JMenuBar to menu items. */
	private JMenuBar menu;
	/** JMenu into the menu bar. */
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu viewMenu;
	/** JMenuItem into the menus. */
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem saveAsItem;
	private JMenuItem exitItem;
	private JMenuItem reWriteItem;
	private JMenuItem deleteItem;
	private JMenuItem newItem;
	private JMenuItem weekItem;
	private JMenuItem monthItem;
	/** JFileChooser to select files to save and open. */
	private JFileChooser fileChooser;

	public Dimension size;

	/**
	 * Default constructor, which make window.
	 */
	public Window(String path) {
		super("Calendar");

		final ViewGUI wgui = new ViewGUI();
		openPath = path;
		savePath = path;

		gui = null;
		GUIPanel = getContentPane();

		menu = new JMenuBar();
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open");
		openItem.addActionListener(new ActionListener() { // Action listener to openItem
			@Override
			public void actionPerformed(ActionEvent e) { // If select open item show chooser window and store file path
				fileChooser = new JFileChooser(openPath);
				fileChooser.showOpenDialog(null);
				openPath = fileChooser.getSelectedFile().getPath();
				List<CalendarEntry> list = new LinkedList<CalendarEntry>();
				try {
					DataHandler.readData(openPath, list);
					DataModel.getEntryList().resetList(list);
				} catch (IOException exception) {
					exception.printStackTrace();
				} catch (DataFormatException exception) {
					JOptionPane.showMessageDialog(getContentPane(), "exception.getMessage()", "Read Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		saveItem = new JMenuItem("Save");
		saveItem.addActionListener(new ActionListener() { // Action listener to saveItem
			@Override
			public void actionPerformed(ActionEvent e) { // If select save item show chooser window and store file path
				fileChooser = new JFileChooser(openPath);
				fileChooser.showSaveDialog(null);
				savePath = fileChooser.getSelectedFile().getPath();
				try {
					DataHandler.writeData(savePath, DataModel.getEntryList());
					Writer write = new FileWriter("Config.txt");
					write.write(savePath);
					write.close();
				} catch (IOException exception) {
					JOptionPane.showMessageDialog(getContentPane(), "exception.getMessage()", "Write Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		saveAsItem = new JMenuItem("Save As");
		saveAsItem.addActionListener(new ActionListener() { // Action listener to saveAsItem
					@Override
					public void actionPerformed(ActionEvent e) { // If select save as item show chooser window and store
																	// file path
						fileChooser = new JFileChooser(openPath);
						fileChooser.showSaveDialog(null);
						savePath = fileChooser.getSelectedFile().getPath();
						try {
							DataHandler.writeData(savePath, DataModel.getEntryList());
							Writer write = new FileWriter("Config.txt");
							write.write(savePath);
							write.close();
						} catch (IOException exception) {
							JOptionPane.showMessageDialog(getContentPane(), "exception.getMessage()", "Write Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});
		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() { // Action listener to exitItem
			@Override
			public void actionPerformed(ActionEvent e) { // If select exit item close the program
				System.exit(0);
			}
		});
		editMenu = new JMenu("Edit");
		newItem = new JMenuItem("New");
		newItem.addActionListener(new ActionListener() { // Action listener to newItem
			@Override
			public void actionPerformed(ActionEvent e) { // If select new item show new edit window
				NewEditWindow n = new NewEditWindow(usingCalendarView);
				n.setVisible(true);
			}
		});
		reWriteItem = new JMenuItem("Rewrite");
		reWriteItem.addActionListener(new ActionListener() { // Action listener to reWriteItem
					@Override
					public void actionPerformed(ActionEvent e) { // If select rewrite item show rewrite edit window
						if (wgui.selectedEntry == null) {
							JOptionPane.showMessageDialog(getContentPane(), "No selected event!", "Selected Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							RewriteEditWindow r = new RewriteEditWindow(wgui.selectedEntry);
							r.setVisible(true);
						}
					}
				});
		deleteItem = new JMenuItem("Delete");
		deleteItem.addActionListener(new ActionListener() { // Action listener to deleteItem
					@Override
					public void actionPerformed(ActionEvent e) { // If select delete item show delete edit window
						if (wgui.selectedEntry == null) {
							JOptionPane.showMessageDialog(getContentPane(), "No selected event!", "Selected Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							int res = JOptionPane.showConfirmDialog(getContentPane(), "Are you sure to delete it?",
									"Warning", JOptionPane.YES_NO_OPTION);
							if (res == JOptionPane.YES_OPTION) {
								Res.Data.DataModel.getEntryList().remove(Res.GUI.ViewGUI.selectedEntry);
								usingCalendarView.repaint();
							}
						}
					}
				});
		viewMenu = new JMenu("View");
		weekItem = new JMenuItem("Week");
		weekItem.addActionListener(new ActionListener() { // Action listener to weekItem
			@Override
			public void actionPerformed(ActionEvent e) { // If select week item refresh gui to week view
				refreshGUI(wgui);
			}
		});
		monthItem = new JMenuItem("Month");
		// monthItem.addActionListener(new ActionListener() { // Action listener to monthItem
		// @Override
		// public void actionPerformed(ActionEvent e) { // If select month item refresh gui to month view
		// refreshGUI(new ViewGUI());
		// }
		// });

		setJMenuBar(menu); // Add menu bar to window
		menu.add(fileMenu); // Add menus to menu bar
		menu.add(editMenu);
		menu.add(viewMenu);
		fileMenu.add(openItem); // Add menu items to file menu
		fileMenu.addSeparator();
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(saveAsItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		editMenu.add(newItem); // Add menu items to edit menu
		editMenu.addSeparator();
		editMenu.add(reWriteItem);
		editMenu.addSeparator();
		editMenu.add(deleteItem);
		viewMenu.add(weekItem); // Add menu items to view menu
		viewMenu.addSeparator();
		viewMenu.add(monthItem);

		setLayout(new FlowLayout());
		pack();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Point location = new Point(400, 300);
		setLocation(location);

		setResizable(false);

		// setting Window size according to WeekView's size
		int reqWidth = WeekView.getViewWidth();
		int reqHeight = WeekView.getViewHeight();
		// Dimension actualSize = getContentPane().getSize();
		int extraW = 50;// reqWidth - actualSize.width;
		int extraH = 100;// reqHeight - actualSize.height;

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		// int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		// System.out.println(height + " - " + (reqHeight + extraH));
		// height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		if (height < reqHeight + extraH) {
			setSize(reqWidth + extraW, height - 50);
			size = new Dimension(reqWidth + extraW, height - 50);
		} else {
			setSize(reqWidth + extraW, reqHeight + extraH);
			size = new Dimension(reqWidth + extraW, reqHeight + extraH);
		}

		refreshGUI(new ViewGUI()); // loads the first GUI
		setVisible(true);
	}

	private GUIGenerator gui;
	private Container GUIPanel; // Container for changable GUIs

	/**
	 * Sets contents of window.
	 * 
	 * @author ZODVAAT.SZE
	 * @param gui
	 *            Component holder class to show
	 */
	public void refreshGUI(GUIGenerator gui) {
		if (this.gui != null) {
			this.gui.destroy();
		}
		GUIPanel.removeAll();

		this.gui = gui;
		usingCalendarView = gui.show(this, GUIPanel);

		validate();
		GUIPanel.repaint();

	}
}
