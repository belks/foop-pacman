package client.gui.components.game;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

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
		//this.setOpaque(false);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	}
	
	

	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(level != null){
			int cols = level.getMapSize().width;
			int rows = level.getMapSize().height;
			byte[][] map = level.getMap();
			
			int sideLenght = Math.min(Math.round(this.getWidth()/cols), Math.round(this.getHeight()/rows));
						
			int centerHorizotal = Math.round((this.getWidth()-(sideLenght*cols))/2);
			int centerVertical = Math.round((this.getHeight()-(sideLenght*rows))/2);
						
			
			int colCount = 0;
			for(byte[] line : map){
				
				int rowCount= 0;
				for(byte state: line){
					int startingX = centerHorizotal + colCount*sideLenght;
					int startingY = centerVertical + rowCount*sideLenght;
					
					Image img = ImageDealer.getImage(FieldState.getStringValue(state));
					
					g.drawImage(img, startingX, startingY, sideLenght, sideLenght, null);
					
					rowCount++;
				}
				
				colCount++;
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
