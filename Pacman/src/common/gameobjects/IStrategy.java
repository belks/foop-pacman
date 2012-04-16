package common.gameobjects;

import java.awt.Point;




/** 
 * Represent the move strategy of a pacman
 */
public interface IStrategy {

		
			
	/** 
	 * calculate the next position of the pacman. Gets the curent position and returns the new position.
	 */
	public abstract Point move(Point position);
			
		

}
