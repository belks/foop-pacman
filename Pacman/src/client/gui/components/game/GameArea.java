package client.gui.components.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import client.gui.images.ImageDealer;
import common.gameobjects.FieldState;
import common.gameobjects.Level;
import common.gameobjects.Pacman;
import common.tools.Config;



public class GameArea extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Level level = null;
	private List<Pacman> pacmans = new LinkedList<Pacman>();
	private Config config = null;

	public GameArea(Config c) {
		super();
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setOpaque(true);
		this.setBackground(Color.GRAY);
		this.config = c;
	}
	
	
	
	
	public void setPacmans(List<Pacman> pacs){
		this.pacmans = pacs;
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		List<Pacman> pacsCopy = new LinkedList<Pacman>(pacmans);
		
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
					
										
					//draw normal field
					String stateStr = FieldState.getStringValue(state).toLowerCase();
					
					
					String imageName = this.config.get("client.activegame.image."+stateStr);
					Image img = ImageDealer.getImage(imageName);
					g.drawImage(img, startingX, startingY, sideLenght, sideLenght, null);
					
						
						
					Pacman p = this.pacmanOnField(x, y, pacsCopy);	
					if(p!=null){
						//draw pacman
						String color = p.colorToString().toLowerCase();
						String dir = p.getDirection().toString().toLowerCase();
						
						if(!color.equalsIgnoreCase("unknown") && !dir.equalsIgnoreCase("unknown")){
							String imagePacman = this.config.get("client.activegame.image.pacman."+color+"."+dir);
							Image imgPac = ImageDealer.getImage(imagePacman);
							g.drawImage(imgPac, startingX, startingY, sideLenght-2, sideLenght-2, null);
						}
						
					}
					
					
					
					y++;
				}
				
				x++;
			}
		}
	}

	
	
	private Pacman pacmanOnField(int x, int y, List<Pacman> pacs){
		Pacman pacman = null;
		
		if(!pacs.isEmpty()){
			for(Pacman p : pacs){
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
