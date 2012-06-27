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



/**
 * The main class for the graphical user interface. Extends a JFrame for displaying the game's content.
 * @author Stefan
 *
 */
public class PacmanGUI extends JFrame implements ExtendedCommEventListener, KeyEventDispatcher{
	
	private static final long serialVersionUID = 1L;
	private Config config = new Config(this.getClass().getResourceAsStream("gui.properties"));
	private View currentView = null;
	private MessageBox messages = new MessageBox();
	private boolean fullScreenMode = false;
	private LinkedList<GUIListener> listeners = new LinkedList<GUIListener>();
	

	/**
	 * Constructor
	 * @param args = the application arguments from the start of the came. They are currently not used. 
	 */
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
		
		this.getContentPane().setBackground(Color.GRAY);
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
	
	
	/**
	 * Returns a config class containing the configuration values from the config file.
	 * @return 
	 */
	public Config getConfig(){
		return this.config;
	}
	
	
	/**
	 * Changes the application from windowed to fullscreen and vice-versa.
	 * @param b - true changes the app to fullscreen.
	 */
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
	
	
	/**
	 * 
	 * @return true if the application is currently in fullscreen mode.
	 */
	public boolean isFullScreen(){
		return this.fullScreenMode;
	}
	
	
	/**
	 * Changes the content of the JFrame at once replacing the old view. A view is an extention of a JPanel.
	 * @param c
	 */
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
	
	
	/**
	 * Shows the log messages at the top of the screen. If not changed by the config this can be shown by pressing F1 while the
	 * application is running.
	 * @param b
	 */
	public void showMessageBox(boolean b){
		messages.setVisible(b);;
	}


	/**
	 * Listens to global key events for fullscreen and log.
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
								
			int maxPlayers = this.getConfig().getInteger("client.playable.players");
			for(int i=1; i<=maxPlayers; i++){
				if(e.getKeyCode() == config.getInteger("client.keys.p"+i+".log")){
					//key F1 pressed - open/close MessageBox(log)
					if(messages.isVisible()){
						messages.setVisible(false);
					}else{
						messages.setVisible(true);
					}
					break;
				}
				
				
				if(e.getKeyCode() == config.getInteger("client.keys.p"+i+".togglefullscreen")){
					if(this.isFullScreen()){
						this.setFullScreen(false);
					}else{
						this.setFullScreen(true);
					}
					break;
				}
			}
			
			
			
			
			
        }

		return false;
	}



	public void printMessage(String msg){
		this.currentView.printMessage(msg);
	}


	/**
	 * Add a listener to listen for GUI commands such as connect, disconnect,...
	 * @param listener
	 */
	public void addListener(GUIListener listener) {
		this.listeners.add(listener);
	}


	/**
	 * Remove a GUILIstener.
	 * @param listener
	 */
	public void removeListener(GUIListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Get all currently enlisted listeners.
	 * @return
	 */
	public List<GUIListener> getListeners(){
		return this.listeners;
	}


	/**
	 * Handles the input from the communication interface which transmits changed from the server.
	 */
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
