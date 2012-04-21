package common.communication;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public abstract class CommMsg {

	protected static final char SEPARATOR = ',';
	private static Map<String, Class> allMsgTypes;

	static {
		allMsgTypes = new HashMap<String, Class>();
		allMsgTypes.put(CommMsg_ChangeDirection.PREFIX,
				CommMsg_ChangeDirection.class);
		allMsgTypes.put(CommMsg_Fin.PREFIX, CommMsg_Fin.class);
		allMsgTypes.put(CommMsg_Level.PREFIX, CommMsg_Level.class);
		allMsgTypes.put(CommMsg_Pacman.PREFIX, CommMsg_Pacman.class);
		allMsgTypes.put(CommMsg_ServerFull.PREFIX, CommMsg_ServerFull.class);
	}

	protected String msg;

	String getMsg() {
		return msg;
	}

	protected CommMsg() {
		this.msg = getPrefix() + SEPARATOR;
	}

	protected CommMsg(String msg) {
		if (!msg.startsWith(getPrefix()))
			throw new IllegalArgumentException("msg beginnt nicht mit Prefix "
					+ getPrefix());
		this.msg = msg;
	}

	abstract String getPrefix();

	@SuppressWarnings("unchecked")
	public static CommMsg fromMessage(String msg) {
		int separatorIndex = msg.indexOf(SEPARATOR);
		if (separatorIndex < 1)
			return null;
		try {
			// Klasse zum Prefix suchen und mit der Nachricht instanzieren
			Class cl = allMsgTypes.get(msg.substring(0, separatorIndex));
			if (cl == null)
				return null;			
			Constructor co = cl.getConstructor(new Class[] { String.class });
			CommMsg m = (CommMsg) co.newInstance(new Object[] { msg });
			return m;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	protected String stringToMsg(String s) {
		return s.replace(SEPARATOR, '-').replace((char) 10, '-')
				.replace((char) 13, '-');
	}
	
	protected String booleanToMsg(boolean bool) {
		return bool ? "1" : "0";
	}
	
	protected boolean msgToBoolean(String msgPart) {
		return (msgPart == "1"); 
	}
}
