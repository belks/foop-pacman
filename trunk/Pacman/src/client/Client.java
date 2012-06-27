package client;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;

import server.ServerMain;
//import server.TestServer;
import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.gameobjects.IStrategy;
import client.gui.PacmanGUI;
import client.gui.GUIListener;




public class Client implements GUIListener, CommEventListener{
	private PacmanGUI gui = null;	
	private Map<String, CommWorker_Client> comms = new LinkedHashMap<String, CommWorker_Client>();
	private ServerMain localServer = null;
	
	
	public Client(String[] args){
		gui = new PacmanGUI(args);
		gui.addListener(this);
	}


	@Override
	public void changeDirection(String player, IStrategy newDir) {
		if(! comms.isEmpty()){
			System.out.println("Changing direction "+newDir);
			comms.get(player).ChangeDirection(newDir.toString());
		}else{
			System.out.println("Cannot change direction! No connection/player!");
		}
	}


	@Override
	public boolean connect(String address, int port, String playerName) {
		try {
			System.out.println("Trying to connect to "+address+":"+port);
			CommWorker_Client comm = new CommWorker_Client(new Socket(address, port));
			comm.addCommEventListener(this);
			comm.start();
			comm.ChangeName(playerName);
			this.comms.put(playerName, comm);
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
		for(String player : comms.keySet()){
			CommWorker_Client comm = comms.get(player);
			if(comm != null && comm.isConnected()){
				System.out.print("Sending disconnect sign.");
				comm.shutdown();
				comm = null;			
			}else{
				System.out.print("Disconnect not possible - no connection.");
			}
		}
		
		if(this.localServer != null){
			localServer.shutdown();
			localServer = null;
		}	
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
		
	}
	
	
	public void ready(){
		System.out.print("Sending ready sign.");
		for(String player : comms.keySet()){
			CommWorker_Client comm = comms.get(player);
			if(comm!=null){
				comm.ChangeReady(true);
			}
		}		
	}


	@Override
	public void handleCommEvent(CommEventObject e) {
		this.gui.handleCommEvent(e,comms.get(comms.keySet().iterator().next()).getGame());
	}


	
	
	
}
