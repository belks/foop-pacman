package client.gui.components;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;

import client.gui.PacmanGUI;

public class View extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title = null;
	private PacmanGUI client = null;
	private View currentSubView = null;
	
	public View(String title, PacmanGUI client){
		super();
		this.client = client;
		this.title = title;
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
	}
	
	public Font getDefaultFont(){
		return new Font(Font.SANS_SERIF, Font.BOLD, 20);
	}
	
	public String toString(){
		return this.title;
	}
	
	public PacmanGUI getGUI(){
		return this.client;
	}

	public void printMessage(String msg){
		System.out.println(msg);
	}
	
	public void setSubView(View c){
		System.out.println("Changing from "+currentSubView+" to "+c);
		
		if(currentSubView!=null){
			this.remove(currentSubView);
		}
		this.add(c, BorderLayout.CENTER);
		this.currentSubView = c;
		
		this.validate();
	}
	
	
}
