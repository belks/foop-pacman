package server;

import java.net.Socket;
import common.communication.*;

public class CommWorker_Server extends CommWorker {

	private final int clientNum; // F�ngt bei 0 an

	public CommWorker_Server(Socket address, int clientNum) {
		super(address);
		this.clientNum = clientNum;
		PacmanController.getInstance().setPacmanConnected(getPacmanId(), false);
	}

	public void sendServerFull() {
		CommMsg_ServerFull m = new CommMsg_ServerFull();
		sendMessageAndShutdown(m);
	}

	public int getClientNum() {
		return clientNum;
	}

	private int getPacmanId() {
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
			}
		}
	}

	@Override
	public void shutdown() {
		super.shutdown();
		PacmanController.getInstance().setPacmanConnected(getPacmanId(), false);
	}
}
