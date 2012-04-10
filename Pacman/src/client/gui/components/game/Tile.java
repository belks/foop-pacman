package client.gui.components.game;

import java.awt.Graphics;

import javax.swing.JLabel;

import client.gui.images.ImageDealer;

import common.gameobjects.FieldState;

public class Tile extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	Tile(){
		this.setOpaque(false);
		this.setBorder(null);	
	}
	
	Tile(byte b){
		super(ImageDealer.getIcon(FieldState.getStringValue(FieldState.getState(b))));
		this.setOpaque(false);
		this.setBorder(null);	
	}
	
	
	public void changeState(FieldState state){
		this.setIcon(ImageDealer.getIcon(FieldState.getStringValue(state)));
		this.repaint();
	}
	
	public void changeState(byte state){
		this.changeState(FieldState.getState(state));
	}
	
	public void paintComponent(Graphics g){
		
	}
}
