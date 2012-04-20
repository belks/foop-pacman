package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.UIManager;
import client.gui.components.MessageBox;
import client.gui.components.View;
import client.gui.components.menu.MainMenu;
import common.communication.CommEventObject;
import common.gameobjects.Game;
import common.tools.Config;




public class PacmanGUI extends JFrame implements ExtendedCommEventListener, KeyEventDispatcher{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Config config = new Config(this.getClass().getResourceAsStream("gui.properties"));
	private View currentView = null;
	private MessageBox messages = new MessageBox();
	private boolean fullScreenMode = false;
	private LinkedList<GUIListener> listeners = new LinkedList<GUIListener>();
	

	
	public PacmanGUI(String[] args){
		super();
		System.out.println("Application started.");
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(this);
		
		
		try {
			System.out.println("Searching for config file at...");
			String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
			String decodedPath = URLDecoder.decode(path, "UTF-8")+"config.txt";
			System.out.println(decodedPath);
			File f = new File(decodedPath);
			if(f.exists()){
				System.out.println("Config file found.");
				this.config.setConfig(new FileInputStream(f));
			}else{
				System.out.println("Config file not found. Using default values.");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	
		this.setTitle(this.config.get("client.window.title"));
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
		
		
		if(config.getBoolean("client.window.fullscreen")){
			this.setFullScreen(true);
		}else{
			this.setPreferredSize(new Dimension(config.getInteger("client.window.width"), config.getInteger("client.window.height")));
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
			this.setPreferredSize(new Dimension(config.getInteger("client.window.width"), config.getInteger("client.window.height")));
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
	
	
	
	public void setView(View c){
		System.out.println("Changing from "+currentView+" to "+c);
		
		if(currentView!=null){
			this.remove(currentView);
		}
		this.add(c, BorderLayout.CENTER);
		this.currentView = c;
		
		this.pack();
		this.validate();
	}
	
	
	public void showMessageBox(boolean b){
		messages.setVisible(b);;
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
			
			
			if(e.getKeyCode() == config.getInteger("client.keys.togglefullscreen")){
				if(this.isFullScreen()){
					this.setFullScreen(false);
				}else{
					this.setFullScreen(true);
				}
			}
			
			
			
        }

		return false;
	}



	public void printMessage(String msg){
		this.currentView.printMessage(msg);
	}


	public void addListener(GUIListener listener) {
		this.listeners.add(listener);
	}


	public void removeListener(GUIListener listener) {
		listeners.remove(listener);
	}
	
	public List<GUIListener> getListeners(){
		return this.listeners;
	}



	@Override
	public void handleCommEvent(CommEventObject e, Game g) {
		if(this.currentView instanceof ExtendedCommEventListener){
			ExtendedCommEventListener l = (ExtendedCommEventListener) this.currentView;
			l.handleCommEvent(e,g);
		}else{
			System.out.println("Unhandled communication event: "+e.getMsg());
		}
	}


	
	
	
}
