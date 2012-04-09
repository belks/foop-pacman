package common.communication;

public class CommMsg_Level extends CommMsg {

	private byte[][] level;
	private String msg;

	public CommMsg_Level(byte[][] level) {
		this.level = level;

	}

	public CommMsg_Level(String msg) {
		super(msg);

	}

	public String getPrefix() {
		return "LEVEL:";
	}

	private String levelToMsg() {
		StringBuilder b = new StringBuilder();
		b.append(getPrefix());

		int length = level.length;
		int width = level[0].length;
		b.append(length).append(SEPARATOR).append(width);

		for (byte[] row : level) {
			for (byte cell : row) {
				b.append(cell).append(SEPARATOR);
			}
		}
		b.deleteCharAt(b.length() - 1); // Remove last separator

		return b.toString();
	}

}
