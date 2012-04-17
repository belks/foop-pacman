package common.communication;

public class CommMsg_ChangeDirection extends CommMsg {

	static final String PREFIX = "CHGDIR:";

	private int direction;

	public int getDirection() {
		return direction;
	}

	public String getDirectionString() {
		switch (direction) {
		case 0:
			return "RIGHT";
		case 1:
			return "UP";
		case 2:
			return "LEFT";
		case 3:
			return "DOWN";
		default:
			return "STOP";
		}
	}

	@Override
	String getPrefix() {
		return PREFIX;
	}

	public static final CommMsg_ChangeDirection RIGHT = new CommMsg_ChangeDirection(
			0);
	public static final CommMsg_ChangeDirection UP = new CommMsg_ChangeDirection(
			1);
	public static final CommMsg_ChangeDirection LEFT = new CommMsg_ChangeDirection(
			2);
	public static final CommMsg_ChangeDirection DOWN = new CommMsg_ChangeDirection(
			3);

	private CommMsg_ChangeDirection(int direction) {
		super();
		this.direction = direction;
		directionToMessage();
	}

	public CommMsg_ChangeDirection(String msg) {
		super(msg);
		messageToDirection();
	}

	private void directionToMessage() {
		StringBuilder b = new StringBuilder();
		b.append(PREFIX).append(SEPARATOR); // 0
		b.append(direction); // 1
		msg = b.toString();
	}

	private void messageToDirection() {
		System.out.println(msg);
		String[] split = msg.split(",");
		direction = Integer.parseInt(split[1]);
	}

}
