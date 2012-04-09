package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;
import java.util.logging.Level;
import common.Comm;
import common.Logging;

/**
 * 
 * @author Chris
 *
 * This class listens to a socket for client connections and creates a 
 * separate worker thread for each connection. Usage: (with 1234 as port)
 * <code>Thread t = new Thread(new CommServer(1234));</code>
 */
public class Comm_Server extends Comm implements Runnable {

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
				t.start();
				Logging.log("Thread for Client started.", Level.INFO);
			} catch (IOException e) {
				if (!disconnect) {
					Logging.log("ServerSocket accept failed: ", Level.SEVERE);
					Logging.logException(e);
					System.exit(-1); //Exit -> Wenn listen auf Port nicht klappt, macht Server keinen Sinn
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
}
