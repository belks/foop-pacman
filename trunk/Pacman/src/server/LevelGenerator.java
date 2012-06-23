package server;

import java.util.Random;

import common.gameobjects.FieldState;
import common.gameobjects.Level;


/**
 * this class generate levels for the pacman game 
 */
public class LevelGenerator {
	/** 
	 * Returns a new generated level
	 */
	public static Level GetLevel(){
		byte[][] temp = null;
		Random randomGenerator = new Random();
		int lev = randomGenerator.nextInt(3);
		int cointSum = 1;
		
		switch(lev){
			case 0:
				temp = new byte[21][20];
				for (int i = 0; i < temp.length; i++) {
					for (int j = 0; j < temp[0].length; j++) {
						if(0 == i || 20 == i){
							temp[i][j] = FieldState.getByteValue(FieldState.Wall);
						} else if( 0 == j || 19 == j){
							temp[i][j] = FieldState.getByteValue(FieldState.Wall);
						} else {
							temp[i][j] = FieldState.getByteValue(FieldState.Coin);
						}
					}
				}
				
				cointSum = 339;
				break;
			case 1:
				temp = new byte[21][21];
				for (int i = 0; i < temp.length; i++) {
					for (int j = 0; j < temp[0].length; j++) {
						if(0 == i || 20 == i){
							temp[i][j] = FieldState.getByteValue(FieldState.Wall);
						} else if( 0 == j || 20 == j){
							temp[i][j] = FieldState.getByteValue(FieldState.Wall);
						} else if (i % 2 == 0 && j % 2 == 0){
							temp[i][j] = FieldState.getByteValue(FieldState.Wall);
						} else {
							temp[i][j] = FieldState.getByteValue(FieldState.Coin);
						}
					}
				}
				
				cointSum = 277;
				break;
			case 2:
				temp = new byte[21][20];
				for (int i = 0; i < temp.length; i++) {
					for (int j = 0; j < temp[0].length; j++) {
						if(0 == i || 20 == i){
							temp[i][j] = FieldState.getByteValue(FieldState.Wall);
						} else if( 0 == j || 19 == j){
							temp[i][j] = FieldState.getByteValue(FieldState.Wall);
						} else if (i % 2 == 0 && j != 1 && j != 18 && j != 10){
							temp[i][j] = FieldState.getByteValue(FieldState.Wall);
						} else {
							temp[i][j] = FieldState.getByteValue(FieldState.Coin);
						}
					}
				}
				
				cointSum = 204;
				break;
		}
		
		Level level = new Level(temp.length, temp[0].length);
		level.setMap(temp);
		level.setCoints(cointSum);
		return level;
	}
}
