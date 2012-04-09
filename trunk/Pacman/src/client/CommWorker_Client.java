package client;

import java.net.Socket;

import common.communication.CommMsg;
import common.communication.CommWorker;

public class CommWorker_Client extends CommWorker {

	private byte[][] level;
	
	public CommWorker_Client(Socket address) {
		super(address);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void processInput(String line) {
		CommMsg msg = CommMsg.fromMessage(line);
		
	}

}
