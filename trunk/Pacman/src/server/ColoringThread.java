package server;

import java.util.List;

import java.awt.Color;
import common.gameobjects.Pacman;

public class ColoringThread extends Thread{
	private volatile Thread thisTread = null;
	
	public ColoringThread() {
		thisTread = this;
	}
	
	@Override
	public void run() {
		Thread thisThread = Thread.currentThread();
		PacmanController pc = PacmanController.getInstance();
		List<Pacman> pacmans = null;
		
		while(this.thisTread == thisThread){
			pacmans = pc.getPacmanList();
			
			for (Pacman p : pacmans) {
				if(Color.RED == p.getColor()){
					pc.setPacmanColor(p.getId(), Color.BLUE);
				} else if(Color.BLUE == p.getColor()){
					pc.setPacmanColor(p.getId(), Color.GREEN);
				} else if(Color.GREEN == p.getColor()){
					pc.setPacmanColor(p.getId(), Color.RED);
				}
			}
			
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close(){
		thisTread = null;
	}
}
