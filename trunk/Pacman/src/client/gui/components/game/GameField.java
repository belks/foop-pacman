package client.gui.components.game;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import common.gameobjects.Game;

import client.gui.ClientGUI;
import client.gui.components.View;

public class GameField extends View{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tile[][] gamefield = null; 
	

	GameField(ClientGUI client){
		super("GameField", client);	
		this.setLayout(new BorderLayout());
	}
	
	
	public void createField(Game game){
		//int width = field.length;
		//int height = field[0].length;
		
		
		//gamefield = new Tile[width][height];
		
	}
	
}
