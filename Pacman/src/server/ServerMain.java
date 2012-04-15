package server;

import java.io.IOException;

public class ServerMain {
	public static void main(String[] args) throws IOException {
		Comm_Server test = new Comm_Server(4444);
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
