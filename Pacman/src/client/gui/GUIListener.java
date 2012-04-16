package client.gui;


import common.gameobjects.IStrategy;

public interface GUIListener {
	
	public void createServer(int port);
	public void connect(String address, int port);
	public void disconnect();
	public void changeDirection(IStrategy newDir);
	public void ready();

}
