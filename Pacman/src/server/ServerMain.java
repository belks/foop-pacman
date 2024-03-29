package server;

import java.io.IOException;
/**
 * @author m-mundorf
 * the servers main class.
 */
public class ServerMain {
	public static void main(String[] args) throws IOException {
		new ServerMain(4444);
	}
	
	public ServerMain(String[] args) throws IOException{
		new ServerMain(4444);
	}
	
	
	public ServerMain(int port) throws IOException{
		Comm_Server comServer = new Comm_Server(port);
		Thread t = new Thread(comServer);
		PacmanController pc = PacmanController.getInstance();	
		pc.initGame();
		pc.setComServer(comServer);		
		t.start();
	}
	
	
	public void shutdown(){
		PacmanController pc = PacmanController.getInstance();
		pc.serverShutdown();
	}
}
