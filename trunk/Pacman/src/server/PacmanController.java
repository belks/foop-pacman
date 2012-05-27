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
import common.tools.Logging;

public class PacmanController implements IController {
	private Game game = null;
	private static volatile PacmanController instance = null;
	private MovingThread movingThread = null;
	private Comm_Server comServer = null;
	private boolean isStarted = false;

	private PacmanController() {
		Logging.log("Init PacmanController.", java.util.logging.Level.INFO);

		init(true);
	}

	public static PacmanController getInstance() {
		if (null == instance) {
			synchronized (PacmanController.class) {
				if(null == instance){
					instance = new PacmanController();
				}
			}
		}

		return instance;
	}
	
//	//<Chris>ResetInstance für wiederholtes Starten von neuen Games
//	public synchronized static void resetInstance() {
//		PacmanController pc = getInstance();
//		if (pc.movingThread == null) {
//			pc = new PacmanController();			
//		}
//	}
//	//</Chris>

	private synchronized void init(boolean isNewGame) {
		isStarted = false;
	
		Level level = LevelGenerator.GetLevel();
		List<Pacman> pacmans = new ArrayList<Pacman>();
		if(isNewGame){
			Pacman bluePacman = new Pacman(1, "Player1", Color.BLUE);
			bluePacman.setPosition(new Point(1, 1));
			Pacman redPacman = new Pacman(2, "Player2", Color.RED);
			redPacman.setPosition(new Point(1, 18));
			Pacman greenPacman = new Pacman(3, "Player3", Color.GREEN);
			greenPacman.setPosition(new Point(18, 18));
	
			pacmans.add(bluePacman);
			pacmans.add(redPacman);
			pacmans.add(greenPacman);
	
			level.setFiel(bluePacman.getPosition(), FieldState.Pacman);
			level.setFiel(redPacman.getPosition(), FieldState.Pacman);
			level.setFiel(greenPacman.getPosition(), FieldState.Pacman);
		
			game = new Game(level, pacmans, 3);
		} else {
			for(Pacman p : game.getPacmans()){
				if(1 == p.getId()){
					p.setColor(Color.BLUE);
					p.setPosition(new Point(1, 1));
				} else if(2 == p.getId()){
					p.setColor(Color.RED);
					p.setPosition(new Point(1, 18));
				} else if(3 == p.getId()){
					p.setColor(Color.GREEN);
					p.setPosition(new Point(18, 18));
				}
				
				level.setFiel(p.getPosition(), FieldState.Pacman);
				p.setDirection(new Stop());
			}
			
			game.setLevel(level);
		}
		
		movingThread = new MovingThread();
	}

	@Override
	public synchronized void startGame() {
		if (null != movingThread && !isStarted) {
			isStarted = true;
			game.incrementRoundCount();
			if (game.getTotalRounds() >= game.getCurrentRound()) {
				try{
					movingThread.start();
				} catch(Exception e){
					init(false);
					movingThread.start();
				}
			} else {
				sendEndCommand();
			}
		}
	}

	@Override
	public synchronized void resetGame() {
		Logging.log("Reset the game state.", java.util.logging.Level.INFO);
		if (null != movingThread) {
			movingThread.close();
		}

		init(true);
	}

	@Override
	public synchronized void stopGame() {
		if (null != movingThread) {
			movingThread.close();
		}
	}

	@Override
	public synchronized void serverShutdown() {
		Logging.log("shutdown the server.", java.util.logging.Level.INFO);

		stopGame();

		try {
			comServer.shutdown();
		} catch (Exception e) {
			Logging.log("Error while close the socket", java.util.logging.Level.INFO);
		}

		comServer = null;
		movingThread = null;
		game = null;
		instance = null;
	}

	@Override
	public synchronized void changePacmanDirection(int id, String direction) {
		Logging.log(String.format("Change direction of pacman %1$d", id), java.util.logging.Level.INFO);

		if (null != movingThread && movingThread.isAlive()) {
			List<Pacman> pacmans = game.getPacmans();
			for (Pacman pac : pacmans) {
				if (id == pac.getId()) {
					if ("UP".equals(direction.toUpperCase())) {
						pac.setDirection(new Up());
					} else if ("DOWN".equals(direction.toUpperCase())) {
						pac.setDirection(new Down());
					} else if ("LEFT".equals(direction.toUpperCase())) {
						pac.setDirection(new Left());
					} else if ("RIGHT".equals(direction.toUpperCase())) {
						pac.setDirection(new Right());
					} else if ("STOP".equals(direction.toUpperCase())) {
						pac.setDirection(new Stop());
					}

					game.setPacmans(pacmans);
					break;
				}
			}
		}
	}

	@Override
	public synchronized List<Pacman> getPacmanList() {
		return game.getPacmans();
	}

	public synchronized void setPacmanList(List<Pacman> pacmans) {
		game.setPacmans(pacmans);
	}

	@Override
	public synchronized void setPacmanPosition(int id, Point position) {
		List<Pacman> pacmans = game.getPacmans();
		for (Pacman pac : pacmans) {
			if (id == pac.getId()) {
				pac.setPosition(position);
				break;
			}
		}

		game.setPacmans(pacmans);
	}

	@Override
	public synchronized void addPacmanCoints(int id, int coints) {
		List<Pacman> pacmans = game.getPacmans();
		for (Pacman pac : pacmans) {
			if (id == pac.getId()) {
				pac.addCoints(coints);
				break;
			}
		}

		game.setPacmans(pacmans);
	}

	@Override
	public synchronized void setPacmanCoints(int id, int coints) {
		List<Pacman> pacmans = game.getPacmans();
		for (Pacman pac : pacmans) {
			if (id == pac.getId()) {
				pac.setCoints(coints);
				break;
			}
		}

		game.setPacmans(pacmans);
	}

	@Override
	public synchronized FieldState getFieldState(Point field) {
		return game.getLevel().getField(field);
	}

	@Override
	public synchronized void setFieldState(Point field, FieldState fieldState) {
		game.getLevel().setFiel(field, fieldState);
	}

	@Override
	public synchronized void setPacmanName(int id, String name) {
		List<Pacman> pacmans = game.getPacmans();
		for (Pacman pac : pacmans) {
			if (id == pac.getId()) {
				pac.setName(name);
				break;
			}
		}

		game.setPacmans(pacmans);
	}

	@Override
	public synchronized void setPacmanColor(int id, Color color) {
		List<Pacman> pacmans = game.getPacmans();
		for (Pacman pac : pacmans) {
			if (id == pac.getId()) {
				pac.setColor(color);
				break;
			}
		}

		game.setPacmans(pacmans);
	}
	
	@Override
	public void initGame() {
		init(true);
		
	}

	

	@Override
	public void connect() {
		//<Chris> Die Methode wird nach connectClient aufgerufen.
		Logging.log("New client connected.", java.util.logging.Level.INFO);
		if(!isStarted){
			comServer.sendGame(game);
		}
	}

	@Override
	public void sendChanges() {
		comServer.sendGame(game);
	}
	
	public void sendEndCommand(){
		comServer.sendEndRound();
		movingThread.close();
		isStarted = false;
	}

	public synchronized void decrementLevelCoints() {
		game.getLevel().decrementCoints();
	}

	public synchronized int getLevelCoints() {
		return game.getLevel().getCoints();
	}

	public synchronized void incrementRoundCount() {
		game.incrementRoundCount();
	}

	public synchronized Comm_Server getComServer() {
		return comServer;
	}

	public synchronized void setComServer(Comm_Server comServer) {
		this.comServer = comServer;
	}

	
	/* <Chris>
	public synchronized void pacmanReadyChanged(int id, boolean ready) {
		startGame();
	}
	
	public synchronized void setPacmanConnected(int id, boolean connected) {
		List<Pacman> pacmans = game.getPacmans();
		for (Pacman pac : pacmans) {
			if (id == pac.getId()) {
				pac.setConnected(connected);
				break;
			}
		}

		game.setPacmans(pacmans);
	}
	</Chris>
	*/

	@Override
	public synchronized int connectClient() {
		List<Pacman> pacmans = game.getPacmans();
		for (Pacman pac : pacmans) {
			if (!pac.getConnected()) {
				pac.setConnected(true);
				game.setPacmans(pacmans);
				
				//<Chris> 
				// sendGame erst nach der Zuweisung der pacmanID 
				// -> connect() wird später aufgerufen
				//comServer.sendGame(game); 
				return pac.getId();
			}
		}
		
		return -1;
	}

	@Override
	public synchronized void disconnectClient(int id) {
		if (null == game){
			return;
		}
		
		List<Pacman> pacmans = game.getPacmans();
		for (Pacman pac : pacmans) {
			if (id == pac.getId()) {
				pac.setConnected(false);
				break;
			}
		}
		
		comServer.sendGame(game);		
		game.setPacmans(pacmans);
	}
}
