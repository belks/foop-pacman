package client;

import java.net.Socket;
import common.communication.*;
import common.gameobjects.Game;

public class CommWorker_Client extends CommWorker {

	private Game game = new Game(null, null);

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
				game.setLevel(((CommMsg_Level) msg).getLevel());
			} else if (msg instanceof CommMsg_Fin) {
				fireEvent(msg);
			}
		}
	}
}
