package server;

import java.net.Socket;

import common.communication.CommMsg_ServerFull;
import common.communication.CommWorker;

public class CommWorker_Server extends CommWorker {

	private int clientNum;

	public CommWorker_Server(Socket address, int clientNum) {
		super(address);
		this.clientNum = clientNum;
	}

	@Override
	protected void processInput(String line) {
		System.out.println(clientNum + ": " + line);
	}
	
	public void sendServerFull() {
		CommMsg_ServerFull m = new CommMsg_ServerFull();
		sendMessageAndShutdown(m);
	}
}
