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
import java.util.LinkedList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import common.communication.CommEventObject;
import common.communication.CommMsg_Endround;
import common.communication.CommMsg_ServerFull;
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
	private StatsPanel currentRoundStats = null;
	private int[] lastKeyCode = null;
	private boolean threadRunning = true;
	private JLabel currentRound = new JLabel();
	private LinkedList<String> localPlayers = null;
	private boolean lastRound = false;
	

	/**
	 * Constructor
	 * @param client
	 */
	public ActiveGame(PacmanGUI gui, LinkedList<String> localPlayers){
		super("ActiveGame", gui);
		this.lastKeyCode = new int[ this.getConfig().getInteger("client.playable.players") ];
		this.localPlayers = localPlayers;
		this.setLayout(new BorderLayout());
		this.gameArea = new GameArea(gui.getConfig());
		this.currentRoundStats = new StatsPanel(gui.getConfig());
		
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
	
	


	@Override
	public synchronized void handleCommEvent(CommEventObject e, Game g) {	
		
		if(e.getMsg() instanceof CommMsg_ServerFull){
			this.gameArea.setGameMessage(this.getConfig().get("client.activegame.messages.serverfull"));
		}else if(e.getMsg() instanceof CommMsg_Endround){
			this.gameArea.setGameMessage(this.getConfig().get("client.activegame.messages.endround"));
			this.ready.setEnabled(true);
			if(this.lastRound){
				this.getGUI().setView(new Scores("Scores", this.getGUI(), g.getPacmans()));
			}
			
		}else{
			if(g != null){
				this.gameArea.setGameMessage(null);
				System.out.println("Got pacman update from server");
				this.currentRound.setText(" "+this.getConfig().get("client.activegame.label.currentRound")
						+" "+g.getCurrentRound()+"/"+g.getTotalRounds());
				this.gameArea.setLevel(g.getLevel());						
				this.gameArea.setPacmans(g.getPacmans());
				this.currentRoundStats.updatePacmans(g.getPacmans());	
				
				if(g.getCurrentRound() == g.getTotalRounds()){
					this.ready.setVisible(false);
					this.lastRound = true;
				}
			}
		}
	}



	

	/**
	 * Used for periodically repainting the game area. Thread is started automatically.
	 */
	@Override
	public void run() {
		int millis = this.getGUI().getConfig().getInteger("client.activegame.repaint.intervall.milliseconds");
		System.out.println("Automatic repainting enabled. Intervall= "+millis+" ms.");
		this.gameArea.setIgnoreRepaint(true);
		
		
				
		
		while(this.threadRunning){
			long startTime = System.currentTimeMillis();
			
			this.gameArea.repaint();
			
			long endTime = System.currentTimeMillis();
						
			
			if(endTime-startTime < 40){
				try {
					Thread.sleep(millis-(endTime-startTime));			
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}




	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(abortGame)){
			for(GUIListener l : this.getGUI().getListeners()){
				l.disconnect();
			}
			this.setRepaintThread(false);
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
		
		int maxPlayers = config.getInteger("client.playable.players");
		for(int i=1; i<=maxPlayers; i++){
			if(i>this.localPlayers.size()){
				break;
			}
			
			if (e.getID() == KeyEvent.KEY_PRESSED && this.lastKeyCode[i-1]!=e.getKeyCode()) {
				IStrategy move = null;
				
				if(e.getKeyCode() == config.getInteger("client.keys.p"+i+".up")){
					move = new Up();
					
				}else if(e.getKeyCode() == config.getInteger("client.keys.p"+i+".down")){
					move = new Down();
				
				}else if(e.getKeyCode() == config.getInteger("client.keys.p"+i+".left")){
					move = new Left();
					
				}else if(e.getKeyCode() == config.getInteger("client.keys.p"+i+".right")){
					move = new Right();
					
				}else{
					move = null;
				}
				
				
				if(move != null){
					this.lastKeyCode[i-1] = e.getKeyCode();
					for(GUIListener l : this.getGUI().getListeners()){
						l.changeDirection(this.localPlayers.get(i-1), move);
					}
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
	private Config conf = null;
	
	StatsPanel(Config conf){
		this.conf = conf;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
	}
	
	public void updatePacmans(List<Pacman> pacs){
		for(Pacman p : pacs){
						
			if( ! stats.keySet().contains(p.getId())){
				StatsRow row = new StatsRow(p,conf);
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
	private JLabel roundPoints = new JLabel("0");
	private JLabel totalPoints = new JLabel("0");
	private JLabel name = new JLabel("");
	private JLabel canEat = new JLabel();
	private JLabel noConn = new JLabel();

	
	StatsRow(Pacman p, Config conf){
		this.setLayout(new GridLayout(0,2));
		this.setOpaque(false);
				
		this.name.setText(p.getName()+":");
		this.setStyle(name);
		
		if(! p.getConnected()){
			noConn.setText(conf.get("client.activegame.statspanel.title.notconnected"));
		}
		this.setStyle(noConn);
		
		
		JLabel roundPointsText = new JLabel(conf.get("client.activegame.statspanel.title.roundscore"));
		this.setStyle(roundPointsText);
		this.setStyle(roundPoints);
		roundPoints.setText(""+p.getCoints());
		
		
		JLabel totalPointsText = new JLabel(conf.get("client.activegame.statspanel.title.totalscore"));
		this.setStyle(totalPointsText);
		this.setStyle(totalPoints);
		totalPoints.setText(""+p.getTotalCoints());
		
		
		JLabel canEatText = new JLabel(conf.get("client.activegame.statspanel.title.canEat"));
		this.setStyle(canEatText);
		this.setStyle(canEat);
		canEat.setOpaque(true);
		
		this.add(name);
		this.add(noConn);
		this.add(roundPointsText);
		this.add(roundPoints);
		this.add(totalPointsText);
		this.add(totalPoints);
		this.add(canEatText);
		this.add(canEat);
		
		this.setBorder(BorderFactory.createEtchedBorder());
	}
	
	/**
	 * Changes the players points and color.
	 * @param x
	 */
	public void setValues(Pacman p){
		this.name.setText(p.getName());
		this.name.setForeground(p.getColor());
		this.roundPoints.setText(""+p.getCoints());
		this.totalPoints.setText(""+p.getTotalCoints());
		
				
		if(p.getColor().equals(Color.RED)){
			canEat.setBackground(Color.BLUE);
		}else if(p.getColor().equals(Color.GREEN)){
			canEat.setBackground(Color.RED);
		}else{
			canEat.setBackground(Color.GREEN);
		}
		
		
		if(p.getConnected()){
			noConn.setVisible(false);
		}else{
			noConn.setVisible(true);
		}
	}
	
	private void setStyle(JLabel l){
		l.setHorizontalAlignment(JLabel.CENTER);
		l.setForeground(Color.WHITE);
		l.setFont(View.getDefaultFont());
		l.setOpaque(false);
	}
	
}





