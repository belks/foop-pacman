package client;

import java.net.Socket;
import java.util.Vector;

import common.communication.*;
import common.gameobjects.Game;
import common.gameobjects.Pacman;

public class CommWorker_Client extends CommWorker {

	private Game game = new Game(null, new Vector<Pacman>());
	private Game newGameState;
	
	public Game getGame() {
		return game;
	}

	public CommWorker_Client(Socket address) {
		super(address);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void processInput(String line) {
		CommMsg msg = CommMsg.fromMessage(line);
		if (msg != null) {
			if (msg instanceof CommMsg_Level) {
				newGameState = new Game(((CommMsg_Level) msg).getLevel(),
						new Vector<Pacman>());
			} else if (msg instanceof CommMsg_Pacman) {
				newGameState.addPacman(((CommMsg_Pacman) msg).getPacman());
			} else if (msg instanceof CommMsg_Fin) {
				game = newGameState;
				fireEvent(msg);
			}
		}
	}

	public void ChangeDirection(String direction) {
		CommMsg msg = CommMsg_ChangeDirection.UP;
		if (direction == "RIGHT") {
			msg = CommMsg_ChangeDirection.RIGHT;
		} else if (direction == "UP") {
			msg = CommMsg_ChangeDirection.UP;
		} else if (direction == "LEFT") {
			msg = CommMsg_ChangeDirection.LEFT;
		} else if (direction == "DOWN") {
			msg = CommMsg_ChangeDirection.DOWN;
		} 
		sendMessage(msg);
	}	
	
	public void ChangeName(String name) {
		CommMsg msg = CommMsg_ChangeName.GetMessage(name);
		sendMessage(msg);
	}
	
	public void ChangeReady(boolean ready) {
		CommMsg msg = new CommMsg_ChangeReady(true);
		sendMessage(msg);		
	}
}
