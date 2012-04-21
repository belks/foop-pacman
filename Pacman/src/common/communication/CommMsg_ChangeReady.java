package common.communication;

public class CommMsg_ChangeReady extends CommMsg {

	static final String PREFIX = "CHGREADY:";
	private boolean ready;

	@Override
	String getPrefix() {
		return PREFIX;
	}

	public CommMsg_ChangeReady(String msg) {
		super(msg);
		messageToReady();
	}

	public CommMsg_ChangeReady(boolean ready) {
		super();
		this.ready = ready;
		readyToMessage();
	}

	private void readyToMessage() {
		StringBuilder b = new StringBuilder();
		b.append(PREFIX).append(SEPARATOR); // 0
		b.append(booleanToMsg(ready)); // 1
		msg = b.toString();
	}

	private void messageToReady() {
		System.out.println(msg);
		String[] split = msg.split(String.valueOf(SEPARATOR));
		ready = msgToBoolean(split[1]);
	}

}
