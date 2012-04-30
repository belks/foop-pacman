package client.gui;


import common.gameobjects.IStrategy;

public interface GUIListener {
	
	public boolean createServer(int port);
	public boolean connect(String address, int port, String playerName);
	public void disconnect();
	public void changeDirection(String player, IStrategy newDir);
	public void ready();

}
