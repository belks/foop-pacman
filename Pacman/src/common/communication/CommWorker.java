package common.communication;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import common.tools.Logging;

/**
 * 
 * @author Chris
 * 
 *         This is the base class for the communication worker threads, used by
 *         both client and server. Usage: <code>
 * Thread t = new Thread(new CommWorker(socket));
 * t.start();
 * </code>
 */
public abstract class CommWorker extends Thread {

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private volatile boolean disconnect;
	private Exception lastException;
	private int exceptionCounter;

	public boolean isConnected() {
		return !disconnect;
	}

	public Exception getLastException() {
		return lastException;
	}

	public CommWorker(Socket socket) {
		this.disconnect = false;
		this.socket = socket;
		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			Logging.logException(e);
			lastException = e;
			shutdown();
		}
	}

	/**
	 * Tries to read lines from the socket in a loop. in.readline() blocks,
	 * until a message is received or an exception occurs (closing the socket
	 * also throws one)
	 */
	@Override
	public void run() {
		String line;

		while (!disconnect) {
			try {
				line = in.readLine(); // Blocks
				if (line != null)
					processInput(line);
			} catch (IOException e) {
				if (!disconnect) {
					Logging.log("CommWorker: ", Level.FINEST);
					Logging.logException(e);
					lastException = e;
					exceptionCounter++;
					if (exceptionCounter > 5)
						shutdown();
				}
			}
		}
		shutdown();
	}

	/**
	 * Overwritten by the client and server classes
	 */
	protected abstract void processInput(String line);

	/**
	 * Sends a message
	 */
	protected void println(String line) {
		synchronized (out) {
			out.println(line);
		}
	}

	/**
	 * Shut down the socket, should be called before exiting the program.
	 */
	public void shutdown() {
		try {
			disconnect = true;
			synchronized (this) {
				if (!socket.isClosed())
					socket.close();
			}
		} catch (IOException e) {
			Logging.logException(e);
			lastException = e;
		}
	}

	/**
	 * Just to be safe.
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		shutdown();
	}

	public void sendMessage(CommMsg msg) {
		println(msg.getMsg());
		Logging.log(msg.getMsg(), Level.FINE);
	}

	public void sendMessages(List<CommMsg> msgs) {
		StringBuilder completeMsg = new StringBuilder();
		for (int i = 0; i < msgs.size(); i++) {
			if (i > 0)
				completeMsg.append("\n");
			CommMsg msg = msgs.get(i);
			completeMsg.append(msg.getMsg());
		}
		String str = completeMsg.toString();
		println(str);
		Logging.log(str, Level.FINE);
	}

	protected void sendMessageAndShutdown(CommMsg msg) {
		sendMessage(msg);
		shutdown();
	}

	private List<CommEventListener> _listeners = new ArrayList<CommEventListener>();

	public void addCommEventListener(CommEventListener listener) {
		synchronized (_listeners) {
			_listeners.add(listener);
		}
	}

	public void removeCommEventListener(CommEventListener listener) {
		synchronized (_listeners) {
			_listeners.remove(listener);
		}
	}

	protected void fireEvent(CommMsg msg) {
		synchronized (_listeners) {
			CommEventObject event = new CommEventObject(this, msg);
			for (CommEventListener listener : _listeners) {
				listener.handleCommEvent(event);
			}
		}
	}
}