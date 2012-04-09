package common.communication;

public abstract class CommMsg {

	protected final char SEPARATOR = ','; 
	
	protected CommMsg(){}
	protected CommMsg(String msg) {
		if (!msg.startsWith(getPrefix()))
			throw new IllegalArgumentException("msg beginnt nicht mit Prefix " + getPrefix());
	}
	
	public abstract String getPrefix();
	
}
