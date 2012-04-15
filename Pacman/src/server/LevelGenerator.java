package server;

import common.gameobjects.Level;


/**
 * this class generate levels for the pacman game 
 */
public class LevelGenerator {
	/** 
	 * Returns a new generated level
	 */
	public static Level GetLevel(){
		byte[][] temp = new byte[20][20];
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[0].length; j++) {
				if(0 == i || 19 == i){
					temp[i][j] = (byte)0;
				} else if( 0 == j || 19 == j){
					temp[i][j] = (byte)0;
				} else{
					temp[i][j] = (byte)2;
				}
			}
		}
		
		Level level = new Level(temp.length, temp[0].length);
		level.setMap(temp);
		level.setCoints(324);
		return level;
	}
}
