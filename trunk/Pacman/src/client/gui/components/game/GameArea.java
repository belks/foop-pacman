package client.gui.components.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedHashMap;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import client.gui.images.ImageDealer;
import common.gameobjects.FieldState;
import common.gameobjects.Level;
import common.gameobjects.Pacman;



public class GameArea extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Level level = null;
	private LinkedHashMap<Integer, Pacman> pacmans = new LinkedHashMap<Integer, Pacman>();;
	

	public GameArea() {
		super();
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.setOpaque(true);
		this.setBackground(Color.GRAY);
	}
	
	
	public void setPacman(Integer id, Pacman pacmans){
		this.pacmans.put(id, pacmans);
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
					
			
			int x = 0;	//collumns
			for(byte[] line : map){
				
				int y= 0;	//rows
				for(byte state: line){
					int startingX = centerHorizotal + x*sideLenght;
					int startingY = centerVertical + y*sideLenght;
					
					Image img = null;
					Pacman p = this.pacmanOnField(x, y);
					if(p==null){
						img = ImageDealer.getImage(FieldState.getStringValue(state));
					}else{
						img = ImageDealer.getImage("pacman");
					}
					g.drawImage(img, startingX, startingY, sideLenght, sideLenght, null);
					
					y++;
				}
				
				x++;
			}
		}
	}

	
	
	private Pacman pacmanOnField(int x, int y){
		Pacman pacman = null;
		
		if(!pacmans.isEmpty()){
			for(Integer id : pacmans.keySet()){
				Pacman p = pacmans.get(id);
				if( (p.getPosition().x == x) && (p.getPosition().y == y) ){
					pacman = p;
					break;
				}
			}
		}
		
		return pacman;
	}




	public void setLevel(Level level) {
		this.level = level;
	}





	public Level getLevel() {
		return level;
	}
	
}
