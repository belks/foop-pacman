package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import gui.components.MainMenu;
import gui.components.MessageBox;
import common.Config;




public class Client extends JFrame implements KeyEventDispatcher{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Config config = new Config(this.getClass().getResourceAsStream("client.properties"));
	private JComponent oldComp = null;
	private MessageBox messages = new MessageBox();
	private boolean fullScreenMode = false;

	
	public static void main(String[] args){
		new Client(args);
	}

	
	public Client(String[] args){
		super();
		System.out.println("Application started.");
		this.setTitle(this.config.get("client.title"));
		this.setLayout(new BorderLayout());
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.getContentPane().setBackground(Color.BLACK);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(messages, BorderLayout.NORTH);
		this.setView(new MainMenu(this));
		
		
		if(config.getBoolean("client.fullscreen")){
			this.setFullScreen(true);
		}else{
			this.setPreferredSize(new Dimension(config.getInteger("client.width"), config.getInteger("client.height")));
		}
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(this);

		
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
		
		if(b==true && gd.isFullScreenSupported()){
			System.out.println("Changing to fullscreen mode.");
			this.setUndecorated(true);
			this.setPreferredSize(new Dimension(gd.getDisplayMode().getWidth(),gd.getDisplayMode().getHeight()));
			gd.setFullScreenWindow(this);
			this.fullScreenMode = true;
			
		}else if(b==true && !gd.isFullScreenSupported()){
			System.out.println("Fullscreen is not supported by this system!");
			
		}else if(b==false && gd.isFullScreenSupported()){
			System.out.println("Changing to window mode.");
			this.setUndecorated(false);
			this.setPreferredSize(new Dimension(config.getInteger("client.width"), config.getInteger("client.height")));
			gd.setFullScreenWindow(null);
			this.fullScreenMode = false;
		}	
		
		this.pack();
		this.validate();
		this.setVisible(true);
	}
	
	
	public boolean isFullScreen(){
		return this.fullScreenMode;
	}
	
	
	
	public void setView(JComponent c){
		System.out.println("Changing from "+oldComp+" to "+c);
		
		if(oldComp!=null){
			this.remove(oldComp);
		}
		this.add(c, BorderLayout.CENTER);
		this.oldComp = c;
		
		this.pack();
		this.validate();
	}
	
	
	


	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
								
			if(e.getKeyCode() == config.getInteger("client.keys.log")){
				//key F1 pressed - open/close MessageBox(log)
				if(messages.isVisible()){
					messages.setVisible(false);
				}else{
					messages.setVisible(true);
				}			
			}
			
			
			
        }

		
		return false;
	}




	
	
	
}
