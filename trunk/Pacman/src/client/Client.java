package client;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import server.ServerMain;
//import server.TestServer;
import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.gameobjects.IStrategy;
import client.gui.PacmanGUI;
import client.gui.GUIListener;




public class Client extends Thread implements GUIListener, CommEventListener{
	private PacmanGUI gui = null;	
	private CommWorker_Client comm = null;
	private ServerMain localServer = null;
	//private TestServer testServer = null;
	
	public Client(String[] args){
		gui = new PacmanGUI(args);
		gui.addListener(this);
	}


	@Override
	public void changeDirection(IStrategy newDir) {
		if(comm != null){
			System.out.println("Changing direction "+newDir);
			comm.ChangeDirection(newDir.toString());
		}else{
			System.out.println("Cannot change direction! No connection!");
		}
	}


	@Override
	public void connect(String address, int port) {
		try {
			System.out.println("Trying to connect to "+address+":"+port);
			comm = new CommWorker_Client(new Socket(address, port));
			comm.addCommEventListener(this);
			comm.start();			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
		}
	}


	@Override
	public void disconnect() {
		if(comm != null && comm.isConnected()){
			System.out.print("Sending disconnect sign.");
			comm.shutdown();
			comm = null;			
		}else{
			System.out.print("Disconnect not possible - no connection.");
		}
		
		
		if(this.localServer != null){
			localServer = null;
		}
		
		
		/*
		if(this.testServer != null){
			testServer.shutdown();
			testServer = null;
		}
		*/
	}
	
	
	
	
	@Override
	public void createServer(int port) {
		
		if(localServer == null){
			try {
				System.out.print("Starting a server at port "+port);
				localServer = new ServerMain(port);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.print(e.getMessage());
			}
		}else{
			System.out.print("A server is already running!");
		}
		
		/*
		if(testServer == null){
			try {
				System.out.print("Starting a server at port "+port);
				testServer = new TestServer(port);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.print(e.getMessage());
			}
		}else{
			System.out.print("A server is already running!");
		}
		*/
	}
	
	
	public void ready(){
		System.out.print("Sending ready sign.");
		// TODO
	}


	@Override
	public void handleCommEvent(CommEventObject e) {
		//System.out.println("Incoming communication event : "+e.getClass().getSimpleName());
		//System.out.println(g.getLevel().getMapSize());
		this.gui.handleCommEvent(e,comm.getGame());
	}


	
	
	
}
