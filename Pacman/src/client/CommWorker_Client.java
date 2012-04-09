package client;

import java.net.Socket;

import common.communication.CommWorker;

public class CommWorker_Client extends CommWorker {

	public CommWorker_Client(Socket address) {
		super(address);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void processInput(String line) {
		// TODO Auto-generated method stub
	}

}
