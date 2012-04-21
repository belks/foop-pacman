package common.communication;

public class CommMsg_ChangeName extends CommMsg {

	static final String PREFIX = "CHGNAME:";
	private String name;

	@Override
	String getPrefix() {
		return PREFIX;
	}

	public String getName() {
		return name;
	}

	private CommMsg_ChangeName() {
		super();
	}

	public CommMsg_ChangeName(String msg) {
		super(msg);
		messageToName();
	}

	public static CommMsg_ChangeName GetMessage(String name) {
		CommMsg_ChangeName retval = new CommMsg_ChangeName();
		retval.name = name;
		retval.nameToMessage();
		return retval;
	}

	private void nameToMessage() {
		StringBuilder b = new StringBuilder();
		b.append(PREFIX).append(SEPARATOR); // 0
		b.append(stringToMsg(name)); // 1
		msg = b.toString();
	}

	private void messageToName() {
		System.out.println(msg);
		String[] split = msg.split(String.valueOf(SEPARATOR));
		name = split[1];
	}
}
