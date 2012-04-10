package client;

import java.net.Socket;

import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.gameobjects.Direction;

import client.gui.ClientGUI;
import client.gui.GUIListener;

public class Client implements GUIListener, CommEventListener{
	private ClientGUI gui = null;
	private CommWorker_Client comm = null;
	
	
	public Client(String[] args){
		gui = new ClientGUI(args, this);
	}


	@Override
	public void changeDirection(Direction newDir) {
		// TODO
	}


	@Override
	public void connect(String address, int port) {
		try {
			comm = new CommWorker_Client(new Socket(address, port));
			comm.addCommEventListener(this);
			Thread t = new Thread(comm);
			//t.setDaemon(true);
			t.start();
		} catch (Exception ex) {
			gui.printMessage(ex.getMessage());
		}
	}


	@Override
	public void disconnect() {
		comm.shutdown();
	}
	
	
	@Override
	public void createServer(int port) {
		// TODO Auto-generated method stub
		
	}
	
	
	


	@Override
	public void handleCommEvent(CommEventObject e) {
		// TODO Auto-generated method stub
		
	}


	
	
	

}
