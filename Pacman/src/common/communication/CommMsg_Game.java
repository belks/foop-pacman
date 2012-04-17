package common.communication;

import common.gameobjects.Game;

public class CommMsg_Game extends CommMsg {

	public static final String PREFIX = "GAME:";
	
	private Game game = null;
	
	public Game getGame() {
		return game;
	}

	public CommMsg_Game(Game game) {
		this.game = game;
	}

	public CommMsg_Game(String msg) {
		super(msg);
	}

	String getPrefix() {
		return PREFIX;
	}

}
