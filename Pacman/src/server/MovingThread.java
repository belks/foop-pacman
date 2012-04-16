package server;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import common.gameobjects.Color;
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
		//TODO moving by pacman
		try {
			Thread thisThread = Thread.currentThread();
			PacmanController pc = PacmanController.getInstance();
			List<Pacman> pacmans = null;
			Map<Integer, IStrategy> movings = null;
			FieldState fs = null;
			
			ColoringThread ct = new ColoringThread();
			ct.start();
			
			while(_thisTread == thisThread){
				pacmans = pc.getPacmanList();
				movings = pc.getMovings();
				
				for (Pacman pac : pacmans) {
					if(pac.getPosition().equals(new Point(0,0))){
						continue;
					}
					
					IStrategy moveStrategy = movings.get(pac.getId());
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
						pc.changePacmanDirection(pac, "STOP");
					} else if(FieldState.Pacman == fs){
						List<Pacman> pList = pc.getPacmanList();
						for (Pacman p : pList) {
							if(position.equals(p.getPosition())){
								IStrategy ms = movings.get(p.getId());
								Point pos = ms.move(p.getPosition());
								
								if(pos.equals(pac.getPosition()) || pos.equals(position)){
									if(isEating(pac.getColor(), p.getColor())){
										pc.setFieldState(pac.getPosition(), FieldState.Free);
										pc.setPacmanPosition(pac.getId(), position);
										pc.addPacmanCoints(pac.getId(), p.getCoints());
										pc.setFieldState(position, FieldState.Pacman);
										
										pc.setPacmanPosition(p.getId(), new Point(0, 0));
										pc.setPacmanCoints(p.getId(), 0);
										pc.changePacmanDirection(p, "STOP");
									} else {
										pc.setFieldState(pac.getPosition(), FieldState.Free);
										pc.setPacmanPosition(pac.getId(), new Point(0,0));
										pc.addPacmanCoints(p.getId(), pac.getCoints());
										pc.setPacmanCoints(pac.getId(), 0);
										pc.changePacmanDirection(pac, "STOP");
									}
								}
							}
						}
					}
				}
				
				
				if(0 == pc.getLevelCoints()){
					ct.close();
					pacmans = pc.getPacmanList();
					for (Pacman pac : pacmans) {
						pac.cpCointsToTotalCoints();
					}
					
					pc.decrementRoundCount();
					pc.setRoundEnd(true);
					
					pc.notifyAll();
					
					break;
				}
				
				pc.notifyAll();
				sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void close(){
		_thisTread = null;
	}
	
	private boolean isEating(Color c1, Color c2){
		if(Color.Red == c1 && Color.Blue == c2){
			return true;
		} else if(Color.Blue == c1 && Color.Green == c2){
			return true;
		} else if(Color.Green == c1 && Color.Red == c2){
			return true;
		}
		
		return false;
	}
}
