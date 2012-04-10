package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import server.Comm_Server;
import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.gameobjects.Direction;
import common.gameobjects.Level;
import client.gui.ClientGUI;
import client.gui.GUIListener;




public class Client extends Thread implements GUIListener, CommEventListener{
	private ClientGUI gui = null;
	private CommWorker_Client comm = null;
	
	
	//for testing
	private Comm_Server test = null;
	private Level level = null;
	private boolean recieved = false;
	
	
	
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
			(new Thread(comm)).start();
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
		try {
			test = new Comm_Server(port);
			(new Thread(test)).start();


			Random r = new Random();
			byte[][] temp = new byte[20][20];
			for (int i = 0; i < temp.length; i++) {
				for (int j = 0; j < temp[0].length; j++) {
					temp[i][j] = (byte) r.nextInt(4);
				}
			}
			
			
			
			level = new Level(20,20);
			level.setMap(temp);
			
			
			(new Thread(this)).start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	


	@Override
	public void handleCommEvent(CommEventObject e) {
		System.out.println(e.getMsg());
		System.out.println(comm.getLevel());
		this.recieved = true;
		this.gui.handleCommEvent(e);
	}


	
	
	
	public void run(){
		while(recieved == false){
			test.sendLevel(level);
		}
	}
}
