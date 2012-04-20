package server;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import java.awt.Color;
import common.gameobjects.Down;
import common.gameobjects.FieldState;
import common.gameobjects.Left;
import common.gameobjects.Level;
import common.gameobjects.Pacman;
import common.gameobjects.Right;
import common.gameobjects.Stop;
import common.gameobjects.Up;

public class PacmanController implements IController{
	private List<Pacman> pacmans = null;
	private Level level = null;
	private static PacmanController instance = null;
	private MovingThread movingThread = null;
	private int roundCount = 0;
	private boolean isRoundEnd = false;
	private boolean isGameEnd = false;
	private Comm_Server comServer = null;
	
	private PacmanController(){
		pacmans = new ArrayList<Pacman>();
		
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
		
		Pacman bluePacman = new Pacman(1, "Player1", Color.BLUE);
		bluePacman.setPosition(new Point(1,1));
		Pacman redPacman = new Pacman(2, "Player2", Color.RED);
		bluePacman.setPosition(new Point(1,18));
		Pacman whitePacman = new Pacman(3, "Player3", Color.GREEN);
		bluePacman.setPosition(new Point(18,18));
		
		pacmans.add(bluePacman);
		pacmans.add(redPacman);
		pacmans.add(whitePacman);
		
		level.setFiel(bluePacman.getPosition(), FieldState.Pacman);
		level.setFiel(redPacman.getPosition(), FieldState.Pacman);
		level.setFiel(whitePacman.getPosition(), FieldState.Pacman);
		
		movingThread = new MovingThread();
		
		isGameEnd = false;
		isRoundEnd = false;
		roundCount = 3;
	}

	@Override
	public synchronized void startGame() {	
			if(null != movingThread && !movingThread.isAlive()){
				isRoundEnd = false;
				movingThread.start();
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
			isGameEnd = true;
			isRoundEnd = true;
		}
	}
	
	@Override
	public synchronized void changePacmanDirection(int pacmanId, String direction) {

		for (Pacman pac : pacmans) {
			if(pacmanId == pac.getId()){
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
				break;
			}
		}
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
	
	public synchronized void setRoundCount(int rc){
		this.roundCount = rc;
	}
	
	public synchronized void decrementRoundCount(){
		this.roundCount--;
		if(0 >= roundCount){
			isGameEnd = true;
		}
	}

	public synchronized boolean isRoundEnd() {
		return isRoundEnd;
	}

	public synchronized void setRoundEnd(boolean isRoundEnd) {
		this.isRoundEnd = isRoundEnd;
	}

	public synchronized boolean isGameEnd() {
		return isGameEnd;
	}

	public synchronized void setGameEnd(boolean isGameEnd) {
		this.isGameEnd = isGameEnd;
	}

	@Override
	public void connect() {
		comServer.sendLevel(level);
	}

	public synchronized Comm_Server getComServer() {
		return comServer;
	}

	public synchronized void setComServer(Comm_Server comServer) {
		this.comServer = comServer;
	}
}
