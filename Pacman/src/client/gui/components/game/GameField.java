package client.gui.components.game;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import common.gameobjects.Game;

import client.gui.ClientGUI;
import client.gui.components.View;

public class GameField extends View{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tile[][] tiles = new Tile[30][30]; 
	private JPanel gamefield = new JPanel();
	private JPanel infoArea = new JPanel();

	GameField(ClientGUI client){
		super("GameField", client);	
		this.setLayout(new BorderLayout());
		
		gamefield.setLayout(new GridLayout(30,30));
		this.add(gamefield, BorderLayout.CENTER);
		this.add(infoArea, BorderLayout.WEST);
	}
	
	
	public void createField(Game game){
		int width = game.getLevel().getMapSize().width;
		int height = game.getLevel().getMapSize().height;
		tiles = new Tile[width][height];
		
		byte[][] map = game.getLevel().getMap();
		
		
		for(byte[] line : map){
			
			for(byte state : line){
				gamefield.add(new Tile(state));
			}
			
		}
		
		
		
	}
	
}
