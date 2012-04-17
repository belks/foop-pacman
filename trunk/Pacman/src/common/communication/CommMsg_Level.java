package common.communication;

import common.gameobjects.Level;

public class CommMsg_Level extends CommMsg {

	static final String PREFIX = "LEVEL:";
	
	private Level level;
	
	public Level getLevel() {
		return level;
	}

	public CommMsg_Level(Level level) {
		this.level = level;
		levelToMsg();
	}

	CommMsg_Level(String msg) {
		super(msg);
		msgToLevel();
	}

	String getPrefix() {
		return "LEVEL:";
	}

	private void levelToMsg() {
		StringBuilder b = new StringBuilder();
		b.append(getPrefix()).append(SEPARATOR); // Added Separator for split

		byte[][] map = level.getMap();
		
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
		
		this.level=new Level(length,width);
		this.level.setMap(map);
	}
}
