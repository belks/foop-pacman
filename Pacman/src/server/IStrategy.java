package server;

import common.gameobjects.Position;


/** 
 * Represent the move strategy of a pacman
 */
public interface IStrategy {

		
			
			/** 
			 * calculate the next position of the pacman. Gets the curent position and returns the new position.
			 */
			public abstract Position move(Position position);
			
		

}
