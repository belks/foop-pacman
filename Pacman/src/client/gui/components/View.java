package client.gui.components;

import java.awt.Font;

import javax.swing.JPanel;

import client.gui.GUI;

public class View extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title = null;
	private GUI client = null;
	
	public View(String title, GUI client){
		super();
		this.client = client;
		this.title = title;
		this.setOpaque(false);
	}
	
	public Font getDefaultFont(){
		return new Font(Font.SANS_SERIF, Font.BOLD, 20);
	}
	
	public String toString(){
		return this.title;
	}
	
	public GUI getClientGUI(){
		return this.client;
	}

	public void printMessage(String msg){
		System.out.println(msg);
	}
}
