package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.UIManager;
import common.Config;

public class Client extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Config config = new Config("/common/client.properties");
	
	
	
	public static void main(String[] args){
		new Client(args);
	}

	
	public Client(String[] args){
		super();
		this.setTitle(this.config.getString("gui.title"));
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setPreferredSize(new Dimension(config.getInteger("gui.width"), config.getInteger("gui.height")));
		this.setBackground(Color.BLACK);
		
		this.pack();
		this.validate();
		this.setVisible(true);
		
		if(config.getBoolean("gui.fullscreen")){
			this.setFullScreen(true);
		}
	}
	
	
	
	
	public void setFullScreen(boolean b){
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		if(b=true && gd.isFullScreenSupported()){
			gd.setFullScreenWindow(this);
		}else{
			gd.setFullScreenWindow(null);
		}
	}
	
	
	
	
}
