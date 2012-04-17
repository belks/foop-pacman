package common.communication;

import common.gameobjects.Game;
import common.gameobjects.Level;

public class CommMsg_Game extends CommMsg {

	public static final String PREFIX = "LEVEL:";
	
	private Game game = null;
	
	public Game getGame() {
		return game;
	}

	public CommMsg_Game(Game game) {
		this.game = game;
		levelToMsg();
	}

	public CommMsg_Game(String msg) {
		super(msg);
		msgToLevel();
	}

	public String getPrefix() {
		return "GAME:";
	}

	private void levelToMsg() {
		StringBuilder b = new StringBuilder();
		b.append(getPrefix()).append(SEPARATOR); // Added Separator for split

		byte[][] map = game.getLevel().getMap();
		
		int length = map.length;
		int width = map[0].length;
		b.append(length).append(SEPARATOR).append(width).append(SEPARATOR);

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				b.append(map[i][j]).append(SEPARATOR);
			}
		}
		b.deleteCharAt(b.length() - 1); // Remove last separator

		this.msg = b.toString();
	}

	
	
	private void msgToLevel() {
		String[] split = msg.split(",");

		// split[0] = "LEVEL:,"
		int length = Integer.parseInt(split[1]);
		int width = Integer.parseInt(split[2]);
		byte[][] map = new byte[length][width];

		int current = 3;

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				map[i][j] = Byte.parseByte(split[current++]);
			}
		}
		
		Level level = new Level(length,width);
		level.setMap(map);
		this.game.setLevel(level);
	}
}
