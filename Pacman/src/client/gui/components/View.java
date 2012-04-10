package client.gui.components;

import java.awt.Font;

import javax.swing.JPanel;

import client.gui.ClientGUI;

public class View extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title = null;
	private ClientGUI client = null;
	
	public View(String title, ClientGUI client){
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
	
	public ClientGUI getClientGUI(){
		return this.client;
	}

	public void printMessage(String msg){
		System.out.println(msg);
	}
}
