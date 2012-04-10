package client.gui.components.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JPanel;
import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.communication.CommMsg_Level;
import common.gameobjects.Level;
import client.gui.ClientGUI;
import client.gui.components.View;




public class Ingame extends View implements CommEventListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tile[][] tiles = new Tile[30][30]; 
	//private JPanel gamefield = new JPanel(new GridLayout(30,30));
	private JPanel infoArea = new JPanel();
	private Canvas canvas = new Canvas();

	public Ingame(ClientGUI client){
		super("InGame", client);	
		this.setLayout(new BorderLayout());
		
		//gamefield.setOpaque(false);
		//infoArea.setOpaque(false);
		//this.add(gamefield, BorderLayout.CENTER);
		this.add(infoArea, BorderLayout.WEST);
		
		
		/*
		Random r = new Random();
		byte[][] temp = new byte[30][30];
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[0].length; j++) {
				temp[i][j] = (byte) r.nextInt(4);
			}
		}*/
		byte[][] temp = {{0}};
		Level level = new Level(1,1);
		level.setMap(temp);
		
		this.createLevel(level);
	}
	
	
	public void createLevel(Level level){
		System.out.println("Creating level.");
		
		int width = level.getMapSize().width;
		int height = level.getMapSize().height;
		
		
		
		
		/*gamefield.removeAll();
		gamefield.setLayout(new GridLayout(width, height));
		
		tiles = new Tile[width][height];
		
		byte[][] map = level.getMap();
		
		
		for(byte[] line : map){
			
			for(byte state : line){
				gamefield.add(new Tile(state));
			}
			
		}
		
		
		this.getClientGUI().repaint();
		*/
	}


	@Override
	public void handleCommEvent(CommEventObject e) {
		System.out.println(e.getMsg());
		if(e.getSource() instanceof CommMsg_Level){
			CommMsg_Level message = (CommMsg_Level) e.getSource();
			this.createLevel(message.getLevel());
		}
	}
	
}
