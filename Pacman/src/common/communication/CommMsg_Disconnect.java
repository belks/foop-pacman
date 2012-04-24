package common.communication;

public class CommMsg_Disconnect extends CommMsg {

	static final String PREFIX = "DISCONNECT:";
	
	@Override
	String getPrefix() {
		return PREFIX;
	}
	
	public CommMsg_Disconnect() {
		super();
	}
	
	public CommMsg_Disconnect(String msg) {
		super(msg);
	}

}
