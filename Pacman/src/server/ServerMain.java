package server;

import java.io.IOException;

public class ServerMain {
	private Comm_Server comServer = null;
	
	
	public static void main(String[] args) throws IOException {
		new ServerMain(4444);
	}
	
	public ServerMain(String[] args) throws IOException{
		new ServerMain(4444);
	}
	
	
	public ServerMain(int port) throws IOException{
		comServer = new Comm_Server(port);
		Thread t = new Thread(comServer);
		PacmanController pc = PacmanController.getInstance();
		pc.setComServer(comServer);
		
		t.start();
	}
	
	
	public void shutdown(){
		comServer.shutdown();
	}
}
