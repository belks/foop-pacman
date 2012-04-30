package common.communication;

public class CommEventObject extends java.util.EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4765056446403616088L;
	
	private CommMsg msg;

	public CommMsg getMsg() {
		return msg;
	}

	public CommEventObject(Object source, CommMsg msg) {
		super(source);
		this.msg = msg;
	}
}
