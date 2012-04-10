package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;
import java.util.logging.Level;
import common.communication.CommMsg_Level;
import common.tools.Logging;

/**
 * 
 * @author Chris
 *
 * This class listens to a socket for client connections and creates a 
 * separate worker thread for each connection. Usage: (with 1234 as port)
 * <code>
 * Thread t = new Thread(new CommServer(1234));
 * t.start();
 * </code>
 */
public class Comm_Server implements Runnable {

	private ServerSocket server;
	private volatile boolean disconnect;
	private Vector<CommWorker_Server> workerList;

	public Comm_Server(int port) throws IOException {
		server = new ServerSocket(port);
		disconnect = false;
		workerList = new Vector<CommWorker_Server>();
	}
	
	@Override
	public void run() {
		int i = 1;
		while (!disconnect) {
			CommWorker_Server w;

			try {
				Logging.log("Waiting for Client.", Level.INFO);
				w = new CommWorker_Server(server.accept(), i++);
				workerList.add(w);
				Logging.log("Client connected.", Level.INFO);
				Thread t = new Thread(w);
				//t.setDaemon(true); //TODO: SetDaemon als Sicherheits-Netz?
				t.start();
				Logging.log("Thread for Client started.", Level.INFO);
			} catch (IOException e) {
				if (!disconnect) {
					Logging.log("ServerSocket accept failed: ", Level.SEVERE);
					Logging.logException(e);
					System.exit(-1); //Exit -> Wenn listen auf Port nicht klappt, macht ganzer Server keinen Sinn. Bei multiport müsste man das ändern.
				}
			}
		}
	}

	public void shutdown() {
		try {
			Logging.log("Shutting down server", Level.INFO);
			disconnect = true;

			synchronized (this) {
				if (!server.isClosed())
					server.close(); //close() vor workerList auflösen -> damit workerList.add() in run() nicht mehr ausgeführt wird.

				if (workerList != null) {
					for (CommWorker_Server s : workerList)
						s.shutdown();
					workerList = null;
				}
			}
		} catch (IOException e) {
			Logging.logException(e);
		}

	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		shutdown();
	}
	
	public void sendLevel(common.gameobjects.Level level) {		
		for(CommWorker_Server worker : workerList){
			if (worker.isConnected()) {
				worker.sendMessage(new CommMsg_Level(level));
			}
		}
	}
}
