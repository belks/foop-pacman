package server;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import common.gameobjects.Color;
import common.gameobjects.FieldState;
import common.gameobjects.Level;
import common.gameobjects.Pacman;

public class PacmanController extends Observable implements IController{
	private List<Pacman> pacmans = null;
	private Map<Integer, IStrategy> movings = null;
	private Level level = null;
	private static PacmanController instance = null;
	private MovingThread movingThread = null;
	
	private PacmanController(){
		pacmans = new ArrayList<Pacman>();
		movings = new HashMap<Integer, IStrategy>();
		
		init();
	}
	
	public synchronized static PacmanController getInstance(){
		if(null == instance){
			instance = new PacmanController();
		}
		
		return instance;
	}

	private synchronized void init() {
		level = LevelGenerator.GetLevel();
		
		Pacman bluePacman = new Pacman(0, "Player1", Color.Blue);
		bluePacman.setPosition(new Point(1,1));
		Pacman redPacman = new Pacman(1, "Player2", Color.Red);
		bluePacman.setPosition(new Point(1,18));
		Pacman whitePacman = new Pacman(2, "Player3", Color.Green);
		bluePacman.setPosition(new Point(18,18));
		
		pacmans.add(bluePacman);
		pacmans.add(redPacman);
		pacmans.add(whitePacman);
		
		movings.put(bluePacman.getId(), new Stop());
		movings.put(redPacman.getId(), new Stop());
		movings.put(whitePacman.getId(), new Stop());
		
		level.setFiel(bluePacman.getPosition(), FieldState.Pacman);
		level.setFiel(redPacman.getPosition(), FieldState.Pacman);
		level.setFiel(whitePacman.getPosition(), FieldState.Pacman);
		
		movingThread = new MovingThread();
	}

	@Override
	public synchronized void startGame() {		
		if(null != movingThread && !movingThread.isAlive()){
			movingThread.start();
			
			try {
				movingThread.join();
				
				//TODO add round end sign
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public synchronized void resetGame(){
		if(null != movingThread){
			movingThread.close();
		}
		
		init();
	}

	@Override
	public synchronized void stopGame() {
		if(null != movingThread){
			movingThread.close();
		}
	}
	
	@Override
	public synchronized void changePacmanDirection(Pacman pacman, String direction) {
		if(!movings.containsKey(pacman.getId()))
			return;
		
		if("UP".equals(direction.toUpperCase())){
			movings.put(pacman.getId(), new Up());
		} else if("DOWN".equals(direction.toUpperCase())){
			movings.put(pacman.getId(), new Down());
		} else if("LEFT".equals(direction.toUpperCase())){
			movings.put(pacman.getId(), new Left());
		} else if("RIGHT".equals(direction.toUpperCase())){
			movings.put(pacman.getId(), new Right());
		} else if("STOP".equals(direction.toUpperCase())){
			movings.put(pacman.getId(), new Stop());
		}
	}
	
	public synchronized Map<Integer, IStrategy> getMovings(){
		return movings;
	}
	
	@Override
	public synchronized List<Pacman> getPacmanList(){
		return pacmans;
	}
	
	@Override
	public synchronized void setPacmanPosition(int id, Point position){
		for (Pacman pac : pacmans) {
			if(id == pac.getId()){
				pac.setPosition(position);
			}
		}
	}
	
	@Override
	public synchronized void addPacmanCoints(int id, int coints){
		for (Pacman pac : pacmans) {
			if(id == pac.getId()){
				pac.addCoints(coints);
			}
		}
	}
	
	@Override
	public synchronized void setPacmanCoints(int id, int coints){
		for (Pacman pac : pacmans) {
			if(id == pac.getId()){
				pac.setCoints(coints);
			}
		}
	}
	
	@Override
	public synchronized FieldState getFieldState(Point field){
		return level.getField(field);
	}
	
	@Override
	public synchronized void setFieldState(Point field, FieldState fieldState){
		level.setFiel(field, fieldState);
	}
	
	public synchronized void decrementLevelCoints(){
		level.decrementCoints();
	}
	
	public synchronized int getLevelCoints(){
		return level.getCoints();
	}
	
	@Override
	public synchronized void setPacmanColor(int id, Color color){
		for (Pacman pac : pacmans) {
			if(id == pac.getId()){
				pac.setColor(color);
			}
		}
	}
}
