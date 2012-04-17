package common.communication;

public class CommMsg_ServerFull extends CommMsg {


	public static final String PREFIX = "SERVERFULL:";
	
	@Override
	public String getPrefix() {
		return PREFIX;
	}

	public CommMsg_ServerFull() {
		super();
	}
	
	public CommMsg_ServerFull(String line) {
		super(line);
	}
}
