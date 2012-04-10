package server;

import java.io.IOException;
import java.util.Random;

import common.gameobjects.Level;

public class TestServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Comm_Server test = new Comm_Server(4444);
		Thread t = new Thread(test);
		t.start();

		
		Random r = new Random();
		byte[][] temp = new byte[4][6];
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[0].length; j++) {
				temp[i][j] = (byte) r.nextInt(10);
			}
		}
		
		Level level = new Level(temp.length, temp[0].length);
		level.setMap(temp);
		
		while(true) {
			test.sendLevel(level);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}

}
