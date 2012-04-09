package common.communication;

public class CommMsg_Level extends CommMsg {

	public static final String PREFIX = "LEVEL:";
	private byte[][] level;
	
	public byte[][] getLevel() {
		return level;
	}

	public CommMsg_Level(byte[][] level) {
		this.level = level;
		levelToMsg();
	}

	public CommMsg_Level(String msg) {
		super(msg);
		msgToLevel();
	}

	public String getPrefix() {
		return "LEVEL:";
	}

	private void levelToMsg() {
		StringBuilder b = new StringBuilder();
		b.append(getPrefix()).append(SEPARATOR); // Added Separator for split

		int length = level.length;
		int width = level[0].length;
		b.append(length).append(SEPARATOR).append(width).append(SEPARATOR);

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				b.append(level[i][j]).append(SEPARATOR);
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
		level = new byte[length][width];

		int current = 3;

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				level[i][j] = Byte.parseByte(split[current++]);
			}
		}
	}
}
