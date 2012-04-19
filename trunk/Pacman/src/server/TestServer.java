package server;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import common.gameobjects.Game;
import common.gameobjects.Pacman;
import common.gameobjects.Level;

public class TestServer implements Runnable{
	
	Comm_Server test = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		new TestServer(4444);
	}
	
	
	public TestServer(int port) throws IOException {
		test = new Comm_Server(port);
		Thread t = new Thread(test);
		t.start();

		(new Thread(this)).start();
	}
	
	
	public void run(){
		while (test!= null) {
			Game game = getRandomGame();

			test.sendGame(game);
			System.out.println(test.getDirection(1));

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void shutdown(){
		test = null;
	}
	

	private static Game getRandomGame() {
		Random r = new Random();
		byte[][] temp = new byte[20][20];
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[0].length; j++) {
				temp[i][j] = (byte) r.nextInt(4);
			}
		}

		Level level = new Level(temp.length, temp[0].length);
		level.setMap(temp);

		List<Pacman> pacmans = new Vector<Pacman>();

		for (int i = 0; i < 3; i++) {
			Pacman p = new Pacman(String.valueOf(i), new Color(i));
			p.setPosition(new Point(r.nextInt(15) + 1, r.nextInt(15) + 1));
			pacmans.add(p);
		}

		Game game = new Game(level, pacmans);
		return game;
	}

	

}
