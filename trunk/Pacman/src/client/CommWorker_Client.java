package client;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import common.communication.CommEventObject;
import common.communication.CommEventListener;
import common.communication.CommMsg;
import common.communication.CommMsg_Level;
import common.communication.CommWorker;
import common.gameobjects.Level;

public class CommWorker_Client extends CommWorker {

	private Level level = null;

	public Level getLevel() {
		return level;
	}

	public CommWorker_Client(Socket address) {
		super(address);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void processInput(String line) {
		CommMsg msg = CommMsg.fromMessage(line);
		if (msg != null) {
			if (msg instanceof CommMsg_Level) {
				level = ((CommMsg_Level) msg).getLevel();
			}
			fireEvent(msg);
		}
	}

	
	
	private List<CommEventListener> _listeners = new ArrayList<CommEventListener>();

	public synchronized void addCommEventListener(CommEventListener listener) {
		_listeners.add(listener);
	}

	public synchronized void removeCommEventListener(CommEventListener listener) {
		_listeners.remove(listener);
	}

	private synchronized void fireEvent(CommMsg msg) {
		CommEventObject event = new CommEventObject(this, msg);
		for (CommEventListener listener : _listeners) {
			listener.handleCommEvent(event);
		}

	}
}
