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
import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.communication.CommMsg_Fin;
import common.communication.CommMsg_Level;
import common.communication.CommMsg_Pacman;
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



public class ActiveGame extends View implements KeyEventDispatcher, ExtendedCommEventListener, Runnable, ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameArea gameArea = new GameArea();
	private JButton abortGame = null;
	private JButton toggleFullScreen = null;
	private JButton ready = null;
	private StatsPanel stats = new StatsPanel();
	private int lastKeyCode = -1;

	
	public ActiveGame(PacmanGUI client){
		super("ActiveGame", client);	
		this.setLayout(new BorderLayout());
		
		
		this.add(createInfoArea(), BorderLayout.WEST);
		this.add(gameArea, BorderLayout.CENTER);
		
		
		//this.createLevel(randomTestLevel());
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(this);
		
		(new Thread(this)).start();
	}
	
	
	
	
	
	private JPanel createInfoArea(){
		JPanel infoArea = new JPanel(new BorderLayout());
		infoArea.setOpaque(false);
		
		
		infoArea.add(stats, BorderLayout.NORTH);
		
		
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
	
	
	
	public void createLevel(Level level){
		System.out.println("Creating level.");
		this.gameArea.setLevel(level);
	}
	*/


	@Override
	public void handleCommEvent(CommEventObject e, Game g) {
		System.out.println(this+" recieved comm event "+e.getClass().getSimpleName());
		
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
			this.gameArea.setLevel(g.getLevel());
			List<Pacman> pacmans  = g.getPacmans();
			
			for(Pacman p : pacmans){
				this.gameArea.setPacman(p.getId(), p);
				
				if( ! this.stats.hasPlayer(p.getId())){
					this.stats.addPlayer(p.getId(), p.getName(), p.getCoints());
				}
				
				this.stats.setPlayerPoints(p.getId(), p.getCoints());
				this.stats.setPlayerColor(p.getId(), p.getColor());
			}
			
		}
	}



	


	@Override
	public void run() {
		int millis = this.getGUI().getConfig().getInteger("client.activegame.repaint.intervall.milliseconds");
		System.out.println("Automatic repainting enabled. Intervall= "+millis+" ms.");
		
		/*
		//TESTING
		LinkedList<Pacman> pl = new LinkedList<Pacman>();
		pl.add(new Pacman(1,"lol",Color.RED));
		pl.add(new Pacman(2,"lol2",Color.GREEN));
		pl.add(new Pacman(3,"lol3",Color.BLUE));
		pl.get(0).setPosition(new Point(5,10));
		pl.get(1).setPosition(new Point(10,5));
		pl.get(2).setPosition(new Point(15,15));
		int move = 1;
		*/
		
		while(true){
			try {
				Thread.sleep(millis);
				
				/*
				//TESTING
				for(Pacman p : pl){
					if(p.getPosition().x == 20){
						move = -1;
						this.stats.setPlayerColor(p.getId(), Color.RED);
					}
					
					if(p.getPosition().x == 1){
						move = 1;
						this.stats.setPlayerColor(p.getId(), Color.GREEN);
					}
					p.setPosition(new Point(p.getPosition().x+move,p.getPosition().y));
				}
				
				this.gameArea.setPacmans(pl);
				*/
				//
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
	
	
	
}	
	


class StatsPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedHashMap<Integer, StatsRow> stats = new LinkedHashMap<Integer, StatsRow>();
	
	StatsPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
	}
	
	public boolean hasPlayer(int id){
		return stats.keySet().contains(id);
	}
	
	public void addPlayer(int id, String name, int initPoints){
		this.stats.put(id, new StatsRow(name,initPoints));
		this.add(this.stats.get(id));
	}
	
	public void setPlayerPoints(int id, int points){
		this.stats.get(id).setPoints(points);
	}
	
	public void setPlayerColor(int id, Color c){
		this.stats.get(id).setTextColor(c);
	}
}



class StatsRow extends JPanel{
	/**
	 * 
	 */
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
		this.points.setOpaque(false);
		this.points.setFont(View.getDefaultFont());
		this.setTextColor(Color.WHITE);
		this.setPoints(initPoints);
		this.add(this.name);
		this.add(this.points);
	}
	
	public void setPoints(int x){
		this.points.setText(""+x);
	}
	
	public void setTextColor(Color c){
		this.name.setForeground(c);
		this.points.setForeground(c);
	}
}





