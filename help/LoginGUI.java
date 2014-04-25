package AdSystem.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import AdSystem.Data.*;
import AdSystem.Users.*;

/**
 * Special type of AdGUI that shows login interface.
 * @author Zombori Dániel
 */
public class LoginGUI implements AdGUI{
	private LoginGUI(){}
	private static AdGUI singleton;
	
	/**
	 * Gets an instance of LoginGUI.
	 * @author Zombori Dániel
	 * @return Return single instance of LoginGUI
	 */
	public static AdGUI getSingleton(){if (singleton == null) singleton =  new LoginGUI(); return singleton;}
	
	/**
	 * Gets an instance of LoginGUI.
	 * @author Zombori Dániel
	 * @return Single instance of LoginGUI
	 * @throws NullPointerException if GUI not created
	 */
	public AdGUI getDynamicSingleton(){if (singleton == null) throw new NullPointerException("LoginGUI not builded correctly"); return singleton;}
	
	/**
	 * Resets the single instance.
	 * @author Zombori Dániel
	 */
	 public static void resetSingleton(){singleton=null;}
	
	/**
	 * Gets the GUIs parent.
	 * @author Zombori Dániel
	 *@return Parent AdWindow of GUI
	 */
	public AdWindow getParent(){
		return parent;
	}
	
	private AdWindow parent;
	
	private JPanel panelGrid;
	private JTextField textFieldName;
	private JLabel labelStatus;
	private JLabel labelEmpty;
	private JPasswordField textFieldPassword;
	private JButton btnLogin;
	private JButton btnRegister;
	
	private static boolean created = false;
	
	/**
	 * Sets window contents to login interface.
	 * Handles the user interactions.
	 * @author Zombori Dániel
	 * @param newParent AdWindow that displays LoginGUI
	 * @param container Method adds elements to this container
	 */
	public void show(final AdWindow newParent, Container container){
		parent = newParent;
		if (!created){
			
			ActionListener al = new ActionListener(){
				public void actionPerformed(ActionEvent event){
					if (DataBase.login(textFieldName.getText(),Secret.encrypt(new String(textFieldPassword.getPassword()))) == null){
						//bejelentkezés sikertelen
						labelStatus.setText("Rossz felhasználónév vagy jelszó");
					}else{
						//bejelentkezve
						labelStatus.setText("Sikeres bejelentkezés");
						UserGUI.resetSingleton();
						parent.refreshGUI(UserGUI.getSingleton());
					}
					textFieldPassword.setText("");
				}
			};
			
			//grid
			panelGrid = new JPanel();
			panelGrid.setLayout(new GridLayout(6,1));
			
			labelEmpty = new JLabel();
			
			//név és jelszó
			textFieldName = new JTextField("Bejelentkezési név",30);
			textFieldPassword = new JPasswordField("12345",30);
			
			textFieldName.addActionListener(al);
			textFieldPassword.addActionListener(al);
			
			//gombok
			btnLogin = new JButton("Bejelentkezés");
			btnRegister = new JButton("Regisztráció");
			
			btnLogin.addActionListener(al);
			
			btnRegister.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event){
					parent.refreshGUI(RegisterGUI.getSingleton(),true);
				}
			});
			
			created = true;
		}
		labelStatus = new JLabel("Adja meg adatait",JLabel.CENTER);
		textFieldPassword.setText("");
		
		//grid
		panelGrid.add(labelStatus);
		panelGrid.add(textFieldName);
		panelGrid.add(textFieldPassword);
		panelGrid.add(btnLogin);
		panelGrid.add(labelEmpty);
		panelGrid.add(btnRegister);
		
		//container
		container.add(panelGrid);
	}
	
	/**
	 * Removes window contents.
	 * @author Zombori Dániel
	 */
	public void destroy(){
		if (created) panelGrid.removeAll();
		created = false;
	}
}
