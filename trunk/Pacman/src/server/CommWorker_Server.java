package server;

import java.net.Socket;
import common.communication.*;


public class CommWorker_Server extends CommWorker {

	private int clientNum;

	public CommWorker_Server(Socket address, int clientNum) {
		super(address);
		this.clientNum = clientNum;
	}

	
	public void sendServerFull() {
		CommMsg_ServerFull m = new CommMsg_ServerFull();
		sendMessageAndShutdown(m);
	}

	public int getClientNum() {
		return clientNum;
	}
	
	@Override
	protected void processInput(String line) {
		CommMsg msg = CommMsg.fromMessage(line);
		if (msg != null) {
			if (msg instanceof CommMsg_ChangeDirection) {
				fireEvent(msg);
			}
		}
	}
}
