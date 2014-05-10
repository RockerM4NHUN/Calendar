//package Res.GUI;
//
//import java.awt.Container;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JButton;
//
//import Res.GUIGenerator;
//import Res.Window;
//
//public class SampleGUI implements GUIGenerator {
//
//	private Window parent;
//	private JButton btn;
//
//	@Override
//	public void show(final Window parent, Container container) {
//		this.parent = parent;
//
//		btn = new JButton("Test sample");
//		btn.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent event) {
//				parent.refreshGUI(new ViewGUI());
//			}
//		});
//
//		container.add(btn);
//	}
//
//	@Override
//	public void destroy() {
//	}
// }
