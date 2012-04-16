package client.gui.components.game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.communication.CommMsg_Level;
import common.gameobjects.Level;
import client.gui.GUIListener;
import client.gui.PacmanGUI;
import client.gui.components.View;
import client.gui.components.menu.MainMenu;




public class ActiveGame extends View implements CommEventListener, Runnable, ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameArea gameArea = new GameArea();
	private JButton abortGame = null;
	private JButton toggleFullScreen = null;
	private JButton ready = null;

	public ActiveGame(PacmanGUI client){
		super("ActiveGame", client);	
		this.setLayout(new BorderLayout());
		
		
		this.add(createInfoArea(), BorderLayout.WEST);
		this.add(gameArea, BorderLayout.CENTER);
		
		
		this.createLevel(randomTestLevel());
		
		(new Thread(this)).start();
	}
	
	
	
	
	private JPanel createInfoArea(){
		JPanel infoArea = new JPanel(new BorderLayout());
		infoArea.setOpaque(false);
		
		
		JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
		north.setOpaque(false);
		
		
		//---------------------------------
		ready = new JButton(this.getGUI().getConfig().get("client.activegame.button.ready"));
		abortGame = new JButton(this.getGUI().getConfig().get("client.activegame.button.abort"));
		toggleFullScreen = new JButton(this.getGUI().getConfig().get("client.activegame.button.toggleFullScreen"));
		
		JPanel south = new JPanel();
		south.setOpaque(false);
		south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
				
		JButton[] buttons = {ready,toggleFullScreen, abortGame};
		for(JButton b:buttons){
			b.setFont(this.getDefaultFont());
			b.addActionListener(this);
			south.add(b);
		}
		
		
		infoArea.add(south, BorderLayout.SOUTH);
		
		return infoArea;
	}
	
	
	private Level randomTestLevel(){
		Random r = new Random();
		byte[][] temp = new byte[60][30];
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[0].length; j++) {
				temp[i][j] = (byte) r.nextInt(3);
			}
		}
		
		Level level = new Level(60,30);
		level.setMap(temp);
		
		return level;
	}
	
	
	
	public void createLevel(Level level){
		System.out.println("Creating level.");
		this.gameArea.setLevel(level);
	}


	@Override
	public void handleCommEvent(CommEventObject e) {
		System.out.println(e.getMsg());
		if(e.getSource() instanceof CommMsg_Level){
			CommMsg_Level message = (CommMsg_Level) e.getSource();
			this.createLevel(message.getLevel());
		}
	}






	@Override
	public void run() {
		int millis = this.getGUI().getConfig().getInteger("client.activegame.repaint.intervall.milliseconds");
		System.out.println("Automatic repainting enabled. Intervall= "+millis+" ms.");
		
		while(true){
			try {
				Thread.sleep(millis);
				this.gameArea.repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}




	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(abortGame)){
			for(GUIListener l : this.getGUI().getListeners()){
				l.disconnect();
			}
			this.getGUI().setView(new MainMenu(this.getGUI()));
		}	
		
		if(arg0.getSource().equals(toggleFullScreen)){
			if(this.getGUI().isFullScreen()){
				this.getGUI().setFullScreen(false);
			}else{
				this.getGUI().setFullScreen(true);
			}
		}
		
		
		if(arg0.getSource().equals(ready)){
			for(GUIListener l : this.getGUI().getListeners()){
				l.ready();
			}
			this.ready.setEnabled(false);
		}
	}
	
}
