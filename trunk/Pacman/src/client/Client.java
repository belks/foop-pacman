package client;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import server.ServerMain;
import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.gameobjects.IStrategy;
import client.gui.PacmanGUI;
import client.gui.GUIListener;




public class Client extends Thread implements GUIListener, CommEventListener{
	private PacmanGUI gui = null;	
	private CommWorker_Client comm = null;
	private ServerMain localServer = null;
	
	public Client(String[] args){
		gui = new PacmanGUI(args);
		gui.addListener(this);
	}


	@Override
	public void changeDirection(IStrategy newDir) {
		// TODO
	}


	@Override
	public void connect(String address, int port) {
		try {
			System.out.println("Trying to connect to "+address+":"+port);
			comm = new CommWorker_Client(new Socket(address, port));
			comm.addCommEventListener(this);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void disconnect() {
		if(comm != null && comm.isConnected()){
			System.out.print("Sending disconnect sign.");
			comm.shutdown();
		}else{
			System.out.print("Disconnect not possible - no connection.");
		}
	}
	
	
	
	
	@Override
	public void createServer(int port) {
		if(localServer == null){
			try {
				System.out.print("Starting a server at port "+port);
				localServer = new ServerMain(port);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			System.out.print("A server is already running!");
		}
	}
	
	
	public void ready(){
		System.out.print("Sending ready sign.");
		// TODO
	}


	@Override
	public void handleCommEvent(CommEventObject e) {
		System.out.println(e.getMsg());
		this.gui.handleCommEvent(e);
	}


	
	
	
}
