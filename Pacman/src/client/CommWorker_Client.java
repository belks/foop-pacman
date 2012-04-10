package client;

import java.net.Socket;
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
}
