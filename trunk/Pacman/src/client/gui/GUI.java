package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import client.gui.components.MessageBox;
import client.gui.components.View;
import client.gui.components.menu.MainMenu;
import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.tools.Config;




public class GUI extends JFrame implements KeyEventDispatcher, CommEventListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Config config = new Config(this.getClass().getResourceAsStream("gui.properties"));
	private View currentView = null;
	private MessageBox messages = new MessageBox();
	private boolean fullScreenMode = false;
	private GUIListener listener = null;


	
	public GUI(String[] args, GUIListener listener){
		super();
		System.out.println("Application started.");
		this.setListener(listener);
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



	public void printMessage(String msg){
		this.currentView.printMessage(msg);
	}


	public void setListener(GUIListener listener) {
		this.listener = listener;
	}


	public GUIListener getListener() {
		return listener;
	}


	@Override
	public void handleCommEvent(CommEventObject e) {
		if(this.currentView instanceof CommEventListener){
			CommEventListener l = (CommEventListener) this.currentView;
			l.handleCommEvent(e);
		}else{
			System.out.println("Unhandled communication event: "+e.getMsg());
		}
	}


	
	
	
}
