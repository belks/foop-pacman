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
	public boolean connect(String address, int port, String playerName) {
		try {
			System.out.println("Trying to connect to "+address+":"+port);
			comm = new CommWorker_Client(new Socket(address, port));
			comm.addCommEventListener(this);
			comm.start();
			comm.ChangeName(playerName);
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
			return false;
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
			localServer.shutdown();
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
	public boolean createServer(int port) {
		
		if(localServer == null){
			try {
				System.out.print("Starting a server at port "+port);
				localServer = new ServerMain(port);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.print(e.getMessage());
				return false;
			}
		}else{
			System.out.print("A server is already running!");
			return false;
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
		if(comm!=null){
			comm.ChangeReady(true);
		}
	}


	@Override
	public void handleCommEvent(CommEventObject e) {
		//System.out.println("Incoming communication event : "+e.getClass().getSimpleName());
		this.gui.handleCommEvent(e,comm.getGame());
	}


	
	
	
}
