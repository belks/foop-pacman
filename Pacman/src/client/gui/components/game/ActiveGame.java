package client.gui.components.game;

import java.awt.BorderLayout;
import java.util.Random;
import javax.swing.JPanel;
import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.communication.CommMsg_Level;
import common.gameobjects.Level;
import client.gui.ClientGUI;
import client.gui.components.View;




public class ActiveGame extends View implements CommEventListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel infoArea = new JPanel();
	private GameArea gameArea = null;

	public ActiveGame(ClientGUI client){
		super("ActiveGame", client);	
		this.setLayout(new BorderLayout());
		
		
		infoArea.setOpaque(false);
		
		this.add(gameArea, BorderLayout.CENTER);
		this.add(infoArea, BorderLayout.WEST);
		
		
		
		Random r = new Random();
		byte[][] temp = new byte[30][30];
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[0].length; j++) {
				temp[i][j] = (byte) r.nextInt(4);
			}
		}
		
		Level level = new Level(30,30);
		level.setMap(temp);
		
		this.createLevel(level);
	}
	
	
	public void createLevel(Level level){
		System.out.println("Creating level.");
		this.gameArea = new GameArea(level);
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
