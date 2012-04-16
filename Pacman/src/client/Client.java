package client;


import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.gameobjects.Direction;
import common.gameobjects.IStrategy;
import client.gui.PacmanGUI;
import client.gui.GUIListener;




public class Client extends Thread implements GUIListener, CommEventListener{
	private PacmanGUI gui = null;	
	
	
	public Client(String[] args){
		gui = new PacmanGUI(args, this);
	}


	@Override
	public void changeDirection(IStrategy newDir) {
		// TODO
	}


	@Override
	public void connect(String address, int port) {
		
	}


	@Override
	public void disconnect() {
	}
	
	
	
	
	@Override
	public void createServer(int port) {
		
	}
	
	
	public void ready(){
		
	}


	@Override
	public void handleCommEvent(CommEventObject e) {
		System.out.println(e.getMsg());
		this.gui.handleCommEvent(e);
	}


	
	
	
}
