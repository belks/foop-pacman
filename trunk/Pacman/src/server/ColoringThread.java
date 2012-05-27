package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import java.awt.Color;
import common.gameobjects.Pacman;
import common.tools.Logging;

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
		List<Color> assignedColors;
		Random randomGenerator = new Random();
		
		while(this.thisTread == thisThread){
			Logging.log("Change pacman color.", java.util.logging.Level.INFO);
			
			pacmans = pc.getPacmanList();
			assignedColors = new ArrayList<Color>();
			
			for(Pacman p : pacmans){
				List<Color> colors = getOtherColor(p.getColor());
				colors.removeAll(assignedColors);
				
				if(0 < colors.size()){
					int col = randomGenerator.nextInt(colors.size());
					Color newColor = colors.get(col);
					assignedColors.add(newColor);
					
					pc.setPacmanColor(p.getId(), newColor);
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
	
	private List<Color> getOtherColor(Color curColor){
		List<Color> colors = new ArrayList<Color>();	
		colors.add(Color.RED);
		colors.add(Color.BLUE);
		colors.add(Color.GREEN);
		
		colors.remove(curColor);
		
		return colors;
	}
}
