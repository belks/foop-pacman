package client.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import common.tools.Config;

import client.gui.PacmanGUI;



/**
 * Extends a JPanel for use as game menus and map area. Does some configuration and provides some 
 * methods for easier layout styling and configuration access.
 * @author Stefan
 *
 */
public class View extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title = null;
	private PacmanGUI client = null;
	private JComponent currentSubView = null;
	
	/**
	 * Constructor
	 * @param title The title of the View, which will be dislayed in the log if neccessary.
	 * @param client	A reference to the main JFrame.
	 */
	public View(String title, PacmanGUI client){
		super();
		this.client = client;
		this.title = title;
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
	}
	
	/**
	 * Returns the font used in the whole application for buttons and text.
	 * @return
	 */
	public static Font getDefaultFont(){
		return new Font(Font.SANS_SERIF, Font.BOLD, 20);
	}
	
	
	/**
	 * Returns the config class.
	 * @return
	 */
	public Config getConfig(){
		return this.getGUI().getConfig();
	}
	
	
	public String toString(){
		return this.title;
	}
	
	/**
	 * Returns the main window.
	 * @return
	 */
	public PacmanGUI getGUI(){
		return this.client;
	}


	/**
	 * Adds a View(JPanel) to the center of the current view. Can be used to create nested views.
	 * @param c
	 */
	public void setSubView(JComponent c){
		System.out.println("Changing from "+currentSubView+" to "+c);
		
		if(currentSubView!=null){
			this.remove(currentSubView);
		}
		this.add(c, BorderLayout.CENTER);
		this.currentSubView = c;
		
		this.validate();
	}
	
	
	
	/**
	 * Shorthand for styling panels.
	 * @param p
	 */
	public void setPanelBorder(JPanel p){
		TitledBorder tb = new TitledBorder(title);
		tb.setTitleColor(Color.WHITE);
		p.setBorder(tb);
	}
	

	/**
	 * Shorthand for styling labels.
	 * @param l
	 */
	public void setLabelStyle(JLabel l){
		l.setFont(View.getDefaultFont());
		l.setForeground(Color.WHITE);
	}
}
