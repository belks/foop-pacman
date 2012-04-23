package server;

import java.awt.Point;
import java.util.List;

import java.awt.Color;
import common.gameobjects.FieldState;
import common.gameobjects.Pacman;


public interface IController{
	/**
	 * starts a round
	 */
	public void startGame();
	
	/**
	 * reset the game. initialize all values to default values
	 */
	public void resetGame();
	
	/**
	 * stops a round
	 */
	public void stopGame();
	
	/**
	 * stops the game, close the server socket 
	 */
	public void serverShutdown();
	
	/**
	 * get a list of pacmans
	 * @return a list of pacmans
	 */
	public List<Pacman> getPacmanList();
	
	/**
	 * initialice a new Game
	 */
	public void initGame();
	
	/**
	 * change the direction for a given pacman id
	 * @param id - the id of the pacman
	 * @param direction - the new direction of the pacman
	 */
	public void changePacmanDirection(int id, String direction);
	
	/**
	 * change the posiont for a given pacman id
	 * @param id - the id of the pacman
	 * @param position - the new position of the pacman
	 */
	public void setPacmanPosition(int id, Point position);
	
	/**
	 * add coints to a pacman
	 * @param id - the id of the pacman
	 * @param coints - the coints that should be added
	 */
	public void addPacmanCoints(int id, int coints);
	
	/**
	 * set the coints of a pacman
	 * @param id - the id of the pacman
	 * @param coints - the new coint amount of the pacman
	 */
	public void setPacmanCoints(int id, int coints);
	
	/**
	 * set the color of a pacman
	 * @param id - the id of the pacman
	 * @param color - the new color of the pacman
	 */
	public void setPacmanColor(int id, Color color);
	
	/**
	 * set the name of a pacman
	 * @param id - the id of the pacman
	 * @param name - the new name of the pacman
	 */
	public void setPacmanName(int id, String name);
	
	/**
	 * get the field state for a given field
	 * @param field - the specific field (as Point)
	 * @return the FieldState for the given point
	 */
	public FieldState getFieldState(Point field);
	
	/**
	 * sets the state for a specific field
	 * @param field - the field that state should be changed
	 * @param fieldState - the new field state
	 */
	public void setFieldState(Point field, FieldState fieldState);
	
	/**
	 * use to sign that a new client have been connected. 
	 * send the game object to the new client. 
	 */
	public void connect();
	
	/**
	 * use to sign that a new client have been connected. 
	 * send the game object to the new client.
	 * @return - the id of the pacman 
	 */
	public int connectClient();
	
	/**
	 * use to disconnect a client
	 * @param id - the id of the pacman
	 */
	public void disconnectClient(int id);
	/**
	 * send the changes to all clients
	 */
	public void sendChanges();
}
