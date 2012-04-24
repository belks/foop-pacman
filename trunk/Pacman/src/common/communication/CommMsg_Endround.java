package common.communication;

public class CommMsg_Endround extends CommMsg {

	static final String PREFIX = "ENDROUND:";
	
	@Override
	String getPrefix() {
		return PREFIX;
	}

	public CommMsg_Endround() {
		super();
	}
	
	public CommMsg_Endround(String msg) {
		super(msg);
	}
}
