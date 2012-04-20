package server;

import java.awt.Point;
import java.util.List;

import java.awt.Color;
import common.gameobjects.FieldState;
import common.gameobjects.Pacman;


public interface IController{
	public void startGame();
	public void resetGame();
	public void stopGame();
	
	public List<Pacman> getPacmanList();
	public void changePacmanDirection(int id, String direction);
	public void setPacmanPosition(int id, Point position);
	public void addPacmanCoints(int id, int coints);
	public void setPacmanCoints(int id, int coints);
	public void setPacmanColor(int id, Color color);
	public void setPacmanName(int id, String name);
	
	public FieldState getFieldState(Point field);
	public void setFieldState(Point field, FieldState fieldState);
	
	public void connect();
	public void sendChanges();
}
