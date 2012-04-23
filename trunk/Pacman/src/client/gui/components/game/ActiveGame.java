package client.gui.components.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import common.communication.CommEventObject;
import common.gameobjects.Down;
import common.gameobjects.IStrategy;
import common.gameobjects.Left;
import common.gameobjects.Right;
import common.gameobjects.Up;
import client.gui.ExtendedCommEventListener;
import client.gui.GUIListener;
import client.gui.PacmanGUI;
import client.gui.components.View;
import client.gui.components.menu.MainMenu;
import common.gameobjects.Pacman;
import common.tools.Config;
import common.gameobjects.Game;


/**
 * This is the game area which consists of the game field and a status/control panel.
 * @author Stefan
 *
 */
public class ActiveGame extends View implements KeyEventDispatcher, ExtendedCommEventListener, Runnable, ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameArea gameArea = null;
	private JButton abortGame = null;
	private JButton toggleFullScreen = null;
	private JButton ready = null;
	private StatsPanel currentRoundStats = new StatsPanel();
	private int lastKeyCode = -1;
	private boolean threadRunning = true;
	private JLabel currentRound = new JLabel();
	

	/**
	 * Constructor
	 * @param client
	 */
	public ActiveGame(PacmanGUI gui){
		super("ActiveGame", gui);	
		this.setLayout(new BorderLayout());
		this.gameArea = new GameArea(gui.getConfig());
		
		this.add(createInfoArea(), BorderLayout.WEST);
		this.add(gameArea, BorderLayout.CENTER);
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(this);
        
		(new Thread(this)).start();
	}
	
	
	/**
	 * Makes this client the host of the server -> enables additional controls for the user
	 * @param b
	 */
	public void setAsServerHost(boolean b){
		ready.setVisible(b);
	}
	
	
	
	/**
	 * Builds the left panel whith status display and buttons
	 * @return
	 */
	private JPanel createInfoArea(){
		JPanel infoArea = new JPanel(new BorderLayout());
		infoArea.setOpaque(false);
		
		
		this.setLabelStyle(currentRound);
		currentRound.setText(this.getGUI().getConfig().get("client.activegame.label.currentRound")+" 0/0");
		infoArea.add(currentRound, BorderLayout.NORTH);
		
		
		infoArea.add(currentRoundStats, BorderLayout.CENTER);
		
		
		//---------------------------------
		ready = new JButton(this.getGUI().getConfig().get("client.activegame.button.ready"));
		abortGame = new JButton(this.getGUI().getConfig().get("client.activegame.button.abort"));
		toggleFullScreen = new JButton(this.getGUI().getConfig().get("client.activegame.button.toggleFullScreen"));
		
		JPanel south = new JPanel();
		south.setOpaque(false);
		south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
				
		JButton[] buttons = {ready,toggleFullScreen, abortGame};
		for(JButton b:buttons){
			b.setFont(View.getDefaultFont());
			b.addActionListener(this);
			south.add(b);
		}
		
		ready.setVisible(false);
		
		infoArea.add(south, BorderLayout.SOUTH);
		
		return infoArea;
	}
	
	/*
	private Level randomTestLevel(){
		Random r = new Random();
		byte[][] temp = new byte[25][25];
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[0].length; j++) {
				temp[i][j] = (byte) r.nextInt(3);
			}
		}
		
		Level level = new Level(25,25);
		level.setMap(temp);
		
		
		stats.addPlayer(1, "Player1", 10);
		stats.addPlayer(2, "Player2", 0);
		stats.addPlayer(3, "Player3", 100);
		
		return level;
	}
	
	
	*/


	@Override
	public void handleCommEvent(CommEventObject e, Game g) {
		//System.out.println(this+" recieved comm event "+e.getClass().getSimpleName());
		
		/*
		if(e.getMsg() instanceof CommMsg_Level){
			System.out.println("Got new level from server.");
			CommMsg_Level message = (CommMsg_Level) e.getMsg();
			this.gameArea.setLevel(message.getLevel());
		}
		
		
		if(e.getMsg() instanceof CommMsg_Pacman){
			System.out.println("Got pacman update from server");
			CommMsg_Pacman message = (CommMsg_Pacman) e.getMsg();
			Pacman p  = message.getPacman();
			this.gameArea.setPacman(p.getId(), p);
			
			if( ! this.stats.hasPlayer(p.getId())){
				this.stats.addPlayer(p.getId(), p.getName(), p.getCoints());
			}
			
			this.stats.setPlayerPoints(p.getId(), p.getCoints());
			this.stats.setPlayerColor(p.getId(), p.getColor());
		}
		
		
		if(e.getMsg() instanceof CommMsg_Fin){
			System.out.println("Got fin message from server");
			CommMsg_Fin msg = (CommMsg_Fin) e.getMsg();
		
		}
		
		
		if(e.getMsg() instanceof CommMsg_ServerFull){
			System.out.println("Got server full message from server");
			
		}
		*/
		
		if(g != null){
			System.out.println("Got pacman update from server");
			
			this.currentRound.setText(this.getGUI().getConfig().get("client.activegame.label.currentRound")
					+" "+g.getCurrentRound()+"/"+g.getTotalRounds());
			
			this.gameArea.setLevel(g.getLevel());
			List<Pacman> pacmans  = g.getPacmans();
						
			this.gameArea.setPacmans(pacmans);
			this.currentRoundStats.updatePacmans(pacmans);			
		}
		
	}



	

	/**
	 * Used for periodically repainting the game area. Thread is started automatically.
	 */
	@Override
	public void run() {
		int millis = this.getGUI().getConfig().getInteger("client.activegame.repaint.intervall.milliseconds");
		System.out.println("Automatic repainting enabled. Intervall= "+millis+" ms.");
		
		
		while(this.threadRunning){
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





	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		Config config = this.getGUI().getConfig();
		
		if (e.getID() == KeyEvent.KEY_PRESSED && this.lastKeyCode!=e.getKeyCode()) {
			IStrategy move = null;
			
			if(e.getKeyCode() == config.getInteger("client.keys.up")){
				move = new Up();
				
			}else if(e.getKeyCode() == config.getInteger("client.keys.down")){
				move = new Down();
			
			}else if(e.getKeyCode() == config.getInteger("client.keys.left")){
				move = new Left();
				
			}else if(e.getKeyCode() == config.getInteger("client.keys.right")){
				move = new Right();
				
			}else{
				move = null;
			}
			
			
			if(move != null){
				this.lastKeyCode = e.getKeyCode();
				for(GUIListener l : this.getGUI().getListeners()){
					l.changeDirection(move);
				}
			}
		}
		
		return false;
	}


	/**
	 * Stops repainting. 
	 * @param threadRunning
	 */
	public void setRepaintThread(boolean running) {
		if(running==true){
			this.threadRunning = true;
			(new Thread(this)).start();
		}else{
			this.threadRunning = false;
		}		
	}


}	



//-----------INTERNAL CLASSES--------------------------
	

/**
 * Displays the players names, colors and points at the left side of the game.
 * Uses StatsRow for this.
 * @author Stefan
 *
 */
class StatsPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private LinkedHashMap<Integer, StatsRow> stats = new LinkedHashMap<Integer, StatsRow>();
	
	StatsPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
	}
	
	public void updatePacmans(List<Pacman> pacs){
		for(Pacman p : pacs){
						
			if( ! stats.keySet().contains(p.getId())){
				StatsRow row = new StatsRow(p.getName(),p.getCoints());
				this.stats.put(p.getId(), row);
				this.add(row);
				this.validate();
			}
			
			StatsRow row = this.stats.get(p.getId());
			row.setValues(p);
		}
	}
}


/**
 * Used by StatsPanel. One row displays the stats of a single player.
 * @author Stefan
 *
 */
class StatsRow extends JPanel{
	private static final long serialVersionUID = 1L;
	private JLabel points = new JLabel("0");
	private JLabel name = new JLabel("Player");
	
	StatsRow(String name, int initPoints){
		this.setLayout(new GridLayout(1,2));
		this.setOpaque(false);
		
		this.name.setOpaque(false);
		this.name.setFont(View.getDefaultFont());
		this.name.setText(name+":");
		this.name.setHorizontalAlignment(JLabel.CENTER);
		this.name.setForeground(Color.WHITE);
		
		this.points.setOpaque(false);
		this.points.setFont(View.getDefaultFont());
		this.points.setText("0/0");
		this.points.setForeground(Color.WHITE);
		
		this.add(this.name);
		this.add(this.points);
	}
	
	/**
	 * Changes the players points and color.
	 * @param x
	 */
	public void setValues(Pacman p){
		this.name.setText(p.getName());
		this.points.setText(p.getCoints()+"/"+p.getTotalCoints());
		this.name.setForeground(p.getColor());
		this.points.setForeground(p.getColor());
	}
	
}





