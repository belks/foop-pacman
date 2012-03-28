package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;

import client.components.MainMenu;
import common.Config;

public class Client extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Config config = new Config(this.getClass().getResourceAsStream("client.properties"));
	private JComponent oldComp = null;
	private LinkedList<String> messages = new LinkedList<String>();
	
	
	public static void main(String[] args){
		new Client(args);
	}

	
	public Client(String[] args){
		super();
		this.setTitle(this.config.get("client.title"));
		this.setLayout(new BorderLayout());
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.getContentPane().setBackground(Color.BLACK);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setView(new MainMenu(this));
		
		if(config.getBoolean("client.fullscreen")){
			this.setFullScreen(true);
		}else{
			this.setPreferredSize(new Dimension(config.getInteger("client.width"), config.getInteger("client.height")));
		}
		
		this.pack();
		this.validate();
		this.setVisible(true);
	}
	
	
	public Config getConfig(){
		return this.config;
	}
	
	
	public void setFullScreen(boolean b){
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		this.dispose();
		
		
		if(b=true && gd.isFullScreenSupported()){
			this.setUndecorated(true);
			this.setPreferredSize(new Dimension(gd.getDisplayMode().getWidth(),gd.getDisplayMode().getHeight()));
			gd.setFullScreenWindow(this);
			
		}else{
			this.setUndecorated(false);
			gd.setFullScreenWindow(null);
			this.setSize(new Dimension(config.getInteger("client.width"), config.getInteger("client.height")));
		}
		
		this.pack();
		this.validate();
		this.setVisible(true);
	}
	
	
	
	public void setView(JComponent c){
		if(oldComp!=null){
			this.remove(oldComp);
		}
		this.add(c, BorderLayout.CENTER);
		this.oldComp = c;
		this.pack();
		this.validate();
	}
	
	
	public void addMessage(String s){
		this.messages.add(s);
		System.out.println(s);
	}
}
