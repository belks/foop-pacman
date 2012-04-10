package client.gui.components.game;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import client.gui.images.ImageDealer;

import common.gameobjects.FieldState;
import common.gameobjects.Level;

public class GameArea extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Level level = null;
	

	public GameArea() {
		super();
		this.setOpaque(false);
	}
	
	

	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(level != null){
			System.out.println("Repainting game Area");
			int cols = level.getMapSize().width;
			int rows = level.getMapSize().height;
			byte[][] map = level.getMap();
			
			int tileWidth = Math.round(this.getWidth()/cols);
			int tileHeight = Math.round(this.getHeight()/rows);
			int sideLenght = Math.min(tileWidth, tileHeight);
			
			
			
			int rowCount = 0;
			for(byte[] line : map){
				
				int colCount = 0;
				for(byte state: line){
					int startingX = colCount*sideLenght;
					int startingY = rowCount*sideLenght;
					
					Image img = ImageDealer.getImage(FieldState.getStringValue(state));
					
					g.drawImage(img, startingX, startingY, sideLenght, sideLenght, null);
					
					colCount++;
				}
				
				rowCount++;
			}
		}
	}





	public void setLevel(Level level) {
		this.level = level;
	}





	public Level getLevel() {
		return level;
	}
	
	

}
