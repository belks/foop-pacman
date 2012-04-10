package client.gui;

import common.gameobjects.Direction;

public interface GUIListener {
	
	public void createServer(int port);
	public void connect(String address, int port);
	public void disconnect();
	public void changeDirection(Direction newDir);

}
