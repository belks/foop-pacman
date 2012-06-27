package client.gui;


import common.gameobjects.IStrategy;

/**
 * A client which serves as a relay between the communication/server and the GUI should implement this interface
 * to get the GUI's generated events.
 * @author Stefan
 *
 */
public interface GUIListener {
	
	public boolean createServer(int port);
	public boolean connect(String address, int port, String playerName);
	public void disconnect();
	public void changeDirection(String player, IStrategy newDir);
	public void ready();

}
