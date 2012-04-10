package client;


import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.gameobjects.Direction;
import client.gui.GUI;
import client.gui.GUIListener;




public class Client extends Thread implements GUIListener, CommEventListener{
	private GUI gui = null;	
	
	
	public Client(String[] args){
		gui = new GUI(args, this);
	}


	@Override
	public void changeDirection(Direction newDir) {
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
	
	
	


	@Override
	public void handleCommEvent(CommEventObject e) {
		System.out.println(e.getMsg());
		this.gui.handleCommEvent(e);
	}


	
	
	
}
