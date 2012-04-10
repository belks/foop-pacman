package common.communication;

public class CommEventObject extends java.util.EventObject {

	private CommMsg msg;

	public CommMsg getMsg() {
		return msg;
	}

	public CommEventObject(Object source, CommMsg msg) {
		super(source);
		this.msg = msg;
	}
}
