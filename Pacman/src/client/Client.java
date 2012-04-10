package client;


import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.gameobjects.Direction;
import client.gui.ClientGUI;
import client.gui.GUIListener;




public class Client extends Thread implements GUIListener, CommEventListener{
	private ClientGUI gui = null;	
	
	
	public Client(String[] args){
		gui = new ClientGUI(args, this);
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
