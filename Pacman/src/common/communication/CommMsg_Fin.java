package common.communication;

public class CommMsg_Fin extends CommMsg {

	public static final String PREFIX = "FIN:";

	@Override String getPrefix() {
		return PREFIX;
	}
	
	public CommMsg_Fin() {
		super();
	}
	
	public CommMsg_Fin(String msg) {
		super(msg);
	}

}
