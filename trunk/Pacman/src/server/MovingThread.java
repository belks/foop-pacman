package server;

import java.awt.Point;
import java.util.List;

import java.awt.Color;
import common.gameobjects.FieldState;
import common.gameobjects.IStrategy;
import common.gameobjects.Pacman;

public class MovingThread extends Thread{
	private volatile Thread _thisTread = null;
	
	public MovingThread(){
		_thisTread = this;
	}
	
	@Override
	public void run() {
		try {
			Thread thisThread = Thread.currentThread();
			PacmanController pc = PacmanController.getInstance();
			List<Pacman> pacmans = null;
			FieldState fs = null;
			int pacmanCount = 3;
			ColoringThread ct = new ColoringThread();
			ct.start();
			
			while(_thisTread == thisThread){
				pacmans = pc.getPacmanList();
				
				for (Pacman pac : pacmans) {
					if(pac.getPosition().equals(new Point(0,0))){
						continue;
					}
					
					IStrategy moveStrategy = pac.getDirection();
					Point position = moveStrategy.move(pac.getPosition());
					
					fs = pc.getFieldState(position);
					
					if(FieldState.Free == fs){
						pc.setFieldState(pac.getPosition(), FieldState.Free);
						pc.setPacmanPosition(pac.getId(), position);
						pc.setFieldState(position, FieldState.Pacman);
					} else if(FieldState.Coin == fs){
						pc.setFieldState(pac.getPosition(), FieldState.Free);
						pc.setPacmanPosition(pac.getId(), position);
						pc.addPacmanCoints(pac.getId(), 1);
						pc.setFieldState(position, FieldState.Pacman);
						pc.decrementLevelCoints();
					} else if(FieldState.Wall == fs){
						pc.changePacmanDirection(pac.getId(), "STOP");
					} else if(FieldState.Pacman == fs){
						List<Pacman> pList = pc.getPacmanList();
						for (Pacman p : pList) {
							if(position.equals(p.getPosition())){
								IStrategy ms = p.getDirection();
								Point pos = ms.move(p.getPosition());
								
								if(pos.equals(pac.getPosition()) || pos.equals(position)){
									if(isEating(pac.getColor(), p.getColor())){
										pc.setFieldState(pac.getPosition(), FieldState.Free);
										pc.setPacmanPosition(pac.getId(), position);
										pc.addPacmanCoints(pac.getId(), p.getCoints());
										pc.setFieldState(position, FieldState.Pacman);
										
										pc.setPacmanPosition(p.getId(), new Point(0, 0));
										pc.setPacmanCoints(p.getId(), 0);
										pc.changePacmanDirection(p.getId(), "STOP");
									} else {
										pc.setFieldState(pac.getPosition(), FieldState.Free);
										pc.setPacmanPosition(pac.getId(), new Point(0,0));
										pc.addPacmanCoints(p.getId(), pac.getCoints());
										pc.setPacmanCoints(pac.getId(), 0);
										pc.changePacmanDirection(pac.getId(), "STOP");
									}
									
									pacmanCount--;
								}
							}
						}
					}
				}
				
				
				if(0 == pc.getLevelCoints() || 1 == pacmanCount){
					ct.close();
					pacmans = pc.getPacmanList();
					for (Pacman pac : pacmans) {
						pac.cpCointsToTotalCoints();
					}
					
					pc.setPacmanList(pacmans);					
					pc.incrementRoundCount();
					pc.sendChanges();
					
					break;
				}
				
				pc.sendChanges();
				sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void close(){
		_thisTread = null;
	}
	
	private boolean isEating(Color color, Color color2){
		if(Color.RED == color && Color.BLUE == color2){
			return true;
		} else if(Color.BLUE == color && Color.GREEN == color2){
			return true;
		} else if(Color.GREEN == color && Color.RED == color2){
			return true;
		}
		
		return false;
	}
}
