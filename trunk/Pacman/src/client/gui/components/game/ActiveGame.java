package client.gui.components.game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.communication.CommMsg_Level;
import common.gameobjects.Level;
import client.gui.GUI;
import client.gui.components.View;
import client.gui.components.menu.ConnectMenu;




public class ActiveGame extends View implements CommEventListener, Runnable, ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameArea gameArea = new GameArea(null);

	public ActiveGame(GUI client){
		super("ActiveGame", client);	
		this.setLayout(new BorderLayout());
		
		
		this.add(createInfoArea(), BorderLayout.WEST);
		this.add(gameArea, BorderLayout.CENTER);
		
		
		
		
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
		
		(new Thread(this)).start();
	}
	
	
	
	
	private JPanel createInfoArea(){
		JPanel infoArea = new JPanel(new BorderLayout());
		infoArea.setOpaque(false);
		
		JButton abortGame = new JButton(this.getClientGUI().getConfig().get("client.activegame.button.abort"));
		abortGame.addActionListener(this);
		return infoArea;
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
		while(true){
			try {
				Thread.sleep(500);
				this.gameArea.repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}




	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.getClientGUI().getListener().disconnect();
		this.getClientGUI().setView(new ConnectMenu(this.getClientGUI()));
	}
	
}
