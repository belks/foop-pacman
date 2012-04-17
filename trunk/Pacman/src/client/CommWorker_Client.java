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
}
