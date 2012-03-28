package client;

import javax.swing.JFrame;
import javax.swing.UIManager;
import common.Config;

public class GUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Config config = new Config("/common/client.properties");
	
	
	
	public static void main(String[] args){
		new GUI(args);
	}

	
	public GUI(String[] args){
		super();
		this.setTitle(this.config.getString("gui.application.title"));
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		this.pack();
		this.validate();
		this.setVisible(true);
	}
	
	
	
}
