package server;

import java.io.IOException;

public class ServerMain {
	public static void main(String[] args) throws IOException {
		new ServerMain(4444);
	}
	
	public ServerMain(int port) throws IOException{
		Comm_Server test = new Comm_Server(port);
		Thread t = new Thread(test);
		t.start();
		
//		while(true) {
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
}
