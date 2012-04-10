package client.gui.components.game;

import javax.swing.JPanel;

import common.gameobjects.Level;

public class GameArea extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Level level = null;
	
	
	


	public GameArea(Level level) {
		super();
		this.level = level;
		this.setOpaque(false);
	}
	
	

}
