package server;

import java.util.List;

import common.gameobjects.Color;
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
				if(Color.Red == p.getColor()){
					pc.setPacmanColor(p.getId(), Color.Blue);
				} else if(Color.Blue == p.getColor()){
					pc.setPacmanColor(p.getId(), Color.Green);
				} else if(Color.Green == p.getColor()){
					pc.setPacmanColor(p.getId(), Color.Red);
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
