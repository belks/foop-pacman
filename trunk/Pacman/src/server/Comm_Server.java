package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.communication.CommMsg;
import common.communication.CommMsg_Fin;
import common.communication.CommMsg_Level;
import common.communication.CommMsg_Pacman;
import common.gameobjects.Pacman;
import common.tools.Logging;

/**
 * 
 * @author Chris
 * 
 *         This class listens to a socket for client connections and creates a
 *         separate worker thread for each connection. Usage: (with 1234 as
 *         port) <code>
 * Thread t = new Thread(new CommServer(1234));
 * t.start();
 * </code>
 * @param <p>
 */
public class Comm_Server implements Runnable, CommEventListener {

	private ServerSocket server;
	private volatile boolean disconnect;
	private CommWorker_Server[] workerList;

	public Comm_Server(int port) throws IOException {
		server = new ServerSocket(port);
		disconnect = false;
		workerList = new CommWorker_Server[3];
	}

	@Override
	public void run() {
		while (!disconnect) {
			try {
				Logging.log("Waiting for Client.", Level.INFO);
				Socket s = server.accept();

				synchronized (this) {
					int i = 0;

					// Freie Connection suchen; i ist dann auch die Pacman-ID

					while (i < workerList.length && workerList[i] != null
							&& workerList[i].isConnected())
						i++;

					CommWorker_Server w = new CommWorker_Server(s, i);
					Thread t = new Thread(w);
					if (i < workerList.length) {
						workerList[i] = w;
						Logging.log("Client connected.", Level.INFO);
						w.addCommEventListener(this);
						// t.setDaemon(true); //TODO: SetDaemon als
						// Sicherheits-Netz?
						t.start();
						Logging.log("Thread for Client started.", Level.INFO);
						PacmanController c = PacmanController.getInstance();
						c.setPacmanConnected(w.getPacmanId(), true);
						c.connect();
					} else {
						t.start();
						w.sendServerFull();
					}
				}
			} catch (IOException e) {
				if (!disconnect) {
					Logging.log("ServerSocket accept failed: ", Level.SEVERE);
					Logging.logException(e);
					System.exit(-1); // Exit -> Wenn listen auf Port nicht
										// klappt, macht ganzer Server keinen
										// Sinn. Bei multiport müsste man das
										// ändern.
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
					server.close(); // close() vor workerList auflösen -> damit
									// workerList.add() in run() nicht mehr
									// ausgeführt wird.

				if (workerList != null) {
					for (CommWorker_Server s : workerList)
						if (s != null)
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

	/*
	 * public void sendLevel(common.gameobjects.Level level) { CommMsg_Level msg
	 * = new CommMsg_Level(level); for (CommWorker_Server worker : workerList) {
	 * if (worker.isConnected()) { worker.sendMessage(msg); } } }
	 */

	public void sendGame(common.gameobjects.Game game) {
		List<CommMsg> msgs = new Vector<CommMsg>();

		msgs.add(new CommMsg_Level(game.getLevel()));
		for (Pacman p : game.getPacmans()) {
			msgs.add(new CommMsg_Pacman(p));
		}
		msgs.add(new CommMsg_Fin());

		synchronized (this) {
			for (CommWorker_Server worker : workerList) {
				if (worker != null && worker.isConnected()) {
					worker.sendMessages(msgs);
				}
			}
		}
	}

	@Override
	public void handleCommEvent(CommEventObject e) {
		// CommMsg msg = e.getMsg();
	}

}