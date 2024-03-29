package client;

import java.net.Socket;
import java.util.Vector;

import common.communication.*;
import common.gameobjects.Game;
import common.gameobjects.Pacman;

/**
 * The communication interface for the clients.
 * @author Chris
 *
 */
public class CommWorker_Client extends CommWorker {

	private Game game = new Game(null, new Vector<Pacman>());
	private Game newGameState;

	public Game getGame() {
		return game;
	}

	public CommWorker_Client(Socket address) {
		super(address);
	}

	@Override
	protected void processInput(String line) {
		CommMsg msg = CommMsg.fromMessage(line);
		if (msg != null) {
			if (msg instanceof CommMsg_Level) {
				newGameState = new Game(((CommMsg_Level) msg).getLevel(),
						new Vector<Pacman>());
				newGameState.setCurrentRound(((CommMsg_Level) msg).getCurrentRound());
				newGameState.setTotalRounds(((CommMsg_Level) msg).getTotalRounds());
			} else if (msg instanceof CommMsg_Pacman) {
				newGameState.addPacman(((CommMsg_Pacman) msg).getPacman());
			} else if (msg instanceof CommMsg_Fin) {
				game = newGameState;
				fireEvent(msg);
			} else {
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

	@Override
	public void shutdown() {
		synchronized (this) {
			if (isConnected())
				sendMessage(new CommMsg_Disconnect());

			super.shutdown();
		}
	}
}
