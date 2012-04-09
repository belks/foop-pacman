package common.communication;

import java.io.*;
import java.net.*;
import common.Logging;

/**
 * 
 * @author Chris
 *
 * This is the base class for the communication worker threads, used by both 
 * client and server. Usage:
 * <code>
 * Thread t = new Thread(new CommWorker(socket));
 * t.start();
 * </code>
 */
public abstract class CommWorker implements Runnable {

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private volatile boolean disconnect;

	public CommWorker(Socket socket) {
		this.disconnect = false;
		this.socket = socket;
	}


	/**
	 * Tries to read lines from the socket in a loop. 
	 * in.readline() blocks, until a message is received or closing the socket throws exception. 
	 */
	@Override
	public void run() {
		String line;
		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			Logging.logException(e);
		}

		while (!disconnect) {
			try {
				line = in.readLine();
				processInput(line);
			} catch (IOException e) {
				if (!disconnect) {
					Logging.logException(e);					
				}
			}
		}
	}

	/**
	 * Overwritten by the client and server classes 
	 */
	protected abstract void processInput(String line);

	/**
	 * Sends a message
	 */
	protected void println(String line) {
		out.println(line);
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
		println(msg.toString());
	}
}