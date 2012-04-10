package client.gui.components.game;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.communication.CommMsg_Level;
import common.gameobjects.Level;
import client.gui.ClientGUI;
import client.gui.components.View;




public class GameField extends View implements CommEventListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tile[][] tiles = new Tile[30][30]; 
	private JPanel gamefield = new JPanel();
	private JPanel infoArea = new JPanel();

	public GameField(ClientGUI client){
		super("GameField", client);	
		this.setLayout(new BorderLayout());
		
		gamefield.setLayout(new GridLayout(30,30));
		this.add(gamefield, BorderLayout.CENTER);
		this.add(infoArea, BorderLayout.WEST);
	}
	
	
	public void createLevel(Level level){
		System.out.println("Drawing level.");
		
		int width = level.getMapSize().width;
		int height = level.getMapSize().height;
		tiles = new Tile[width][height];
		
		byte[][] map = level.getMap();
		
		
		for(byte[] line : map){
			
			for(byte state : line){
				gamefield.add(new Tile(state));
			}
			
		}
		
		
		
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
