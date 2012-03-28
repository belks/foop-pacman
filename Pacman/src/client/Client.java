package client;

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
import client.components.MainMenu;
import client.components.MessageBox;
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
		System.out.println("Changing to fullscreen="+b);
		
		if(b==true && gd.isFullScreenSupported()){
			this.setUndecorated(true);
			this.setPreferredSize(new Dimension(gd.getDisplayMode().getWidth(),gd.getDisplayMode().getHeight()));
			gd.setFullScreenWindow(this);
			this.fullScreenMode = true;
			
		}else{
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
						
			if(e.getKeyCode()==KeyEvent.VK_F1){
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
