package server;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import java.awt.Color;
import common.gameobjects.Down;
import common.gameobjects.FieldState;
import common.gameobjects.Game;
import common.gameobjects.Left;
import common.gameobjects.Level;
import common.gameobjects.Pacman;
import common.gameobjects.Right;
import common.gameobjects.Stop;
import common.gameobjects.Up;

public class PacmanController implements IController{
	private Game game = null;
	private static PacmanController instance = null;
	private MovingThread movingThread = null;
	private Comm_Server comServer = null;
	
	private PacmanController(){
		init();
	}
	
	public synchronized static PacmanController getInstance(){
		if(null == instance){
			instance = new PacmanController();
		}
		
		return instance;
	}

	private synchronized void init() {
		Level level = LevelGenerator.GetLevel();
		List<Pacman> pacmans = new ArrayList<Pacman>();
		
		Pacman bluePacman = new Pacman(1, "Player1", Color.BLUE);
		bluePacman.setPosition(new Point(1,1));
		Pacman redPacman = new Pacman(2, "Player2", Color.RED);
		redPacman.setPosition(new Point(1,18));
		Pacman greenPacman = new Pacman(3, "Player3", Color.GREEN);
		greenPacman.setPosition(new Point(18,18));
		
		pacmans.add(bluePacman);
		pacmans.add(redPacman);
		pacmans.add(greenPacman);
		
		level.setFiel(bluePacman.getPosition(), FieldState.Pacman);
		level.setFiel(redPacman.getPosition(), FieldState.Pacman);
		level.setFiel(greenPacman.getPosition(), FieldState.Pacman);
		
		game = new Game(level, pacmans, 3);
		
		movingThread = new MovingThread();
	}

	@Override
	public synchronized void startGame() {	
			if(null != movingThread && !movingThread.isAlive()){
				game.incrementRoundCount();
				if(game.getTotalRounds() >= game.getCurrentRound()){
					movingThread.start();
				} else{
					// TODO send finisch command
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
	public synchronized void changePacmanDirection(int id, String direction) {
		List<Pacman> pacmans = game.getPacmans();
		for (Pacman pac : pacmans) {
			if(id == pac.getId()){
				if("UP".equals(direction.toUpperCase())){
					pac.setDirection(new Up());
				} else if("DOWN".equals(direction.toUpperCase())){
					pac.setDirection(new Down());
				} else if("LEFT".equals(direction.toUpperCase())){
					pac.setDirection(new Left());
				} else if("RIGHT".equals(direction.toUpperCase())){
					pac.setDirection(new Right());
				} else if("STOP".equals(direction.toUpperCase())){
					pac.setDirection(new Stop());
				}
				
				game.setPacmans(pacmans);
				break;
			}
		}
	}
	
	@Override
	public synchronized List<Pacman> getPacmanList(){
		return game.getPacmans();
	}
	
	public synchronized void setPacmanList(List<Pacman> pacmans){
		game.setPacmans(pacmans);
	}
	
	@Override
	public synchronized void setPacmanPosition(int id, Point position){
		List<Pacman> pacmans = game.getPacmans();
		for (Pacman pac : pacmans) {
			if(id == pac.getId()){
				pac.setPosition(position);
			}
		}
		
		game.setPacmans(pacmans);
	}
	
	@Override
	public synchronized void addPacmanCoints(int id, int coints){
		List<Pacman> pacmans = game.getPacmans();
		for (Pacman pac : pacmans) {
			if(id == pac.getId()){
				pac.addCoints(coints);
			}
		}
		
		game.setPacmans(pacmans);
	}
	
	@Override
	public synchronized void setPacmanCoints(int id, int coints){
		List<Pacman> pacmans = game.getPacmans();
		for (Pacman pac : pacmans) {
			if(id == pac.getId()){
				pac.setCoints(coints);
			}
		}
		
		game.setPacmans(pacmans);
	}
	
	@Override
	public synchronized FieldState getFieldState(Point field){
		return game.getLevel().getField(field);
	}
	
	@Override
	public synchronized void setFieldState(Point field, FieldState fieldState){
		game.getLevel().setFiel(field, fieldState);
	}
	
	@Override
	public synchronized void setPacmanName(int id, String name) {
		List<Pacman> pacmans = game.getPacmans();
		for (Pacman pac : pacmans) {
			if(id == pac.getId()){
				pac.setName(name);
			}
		}
		
		game.setPacmans(pacmans);
	}
	
	@Override
	public synchronized void setPacmanColor(int id, Color color){
		List<Pacman> pacmans = game.getPacmans();
		for (Pacman pac : pacmans) {
			if(id == pac.getId()){
				pac.setColor(color);
			}
		}
		
		game.setPacmans(pacmans);
	}
	
	@Override
	public void connect() {
		comServer.sendGame(game);
	}
	
	@Override
	public void sendChanges() {
		comServer.sendGame(game);
	}
	
	public synchronized void decrementLevelCoints(){
		game.getLevel().decrementCoints();
	}
	
	public synchronized int getLevelCoints(){
		return game.getLevel().getCoints();
	}
	
	public synchronized void incrementRoundCount(){
		game.incrementRoundCount();
	}
	
	public synchronized Comm_Server getComServer() {
		return comServer;
	}

	public synchronized void setComServer(Comm_Server comServer) {
		this.comServer = comServer;
	}
}
