package server;

import java.net.Socket;
import common.communication.*;

public class CommWorker_Server extends CommWorker {

	private final int clientNum; // Fängt bei 0 an

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

	public int getPacmanId() {
		return clientNum + 1;
	}

	@Override
	protected void processInput(String line) {
		CommMsg msg = CommMsg.fromMessage(line);
		if (msg != null) {
			PacmanController c = PacmanController.getInstance();
			int pacmanID = getPacmanId();
			if (msg instanceof CommMsg_ChangeDirection) {
				String direction = ((CommMsg_ChangeDirection) msg)
						.getDirectionString();
				c.changePacmanDirection(pacmanID, direction);
			} else if (msg instanceof CommMsg_ChangeName) {
				String name = ((CommMsg_ChangeName) msg).getName();
				c.setPacmanName(pacmanID, name);
			} else if (msg instanceof CommMsg_ChangeReady) {
				boolean ready = ((CommMsg_ChangeReady) msg).getReady();
				c.pacmanReadyChanged(pacmanID, ready);
			}
		}
	}

	@Override
	public void shutdown() {
		super.shutdown();
		PacmanController.getInstance().setPacmanConnected(getPacmanId(), false);
	}
}
