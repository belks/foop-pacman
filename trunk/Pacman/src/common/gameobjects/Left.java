package common.gameobjects;

import java.awt.Point;




public class Left implements IStrategy {

	@Override
	public Point move(Point position) {
		return new Point(position.x - 1, position.y);
	}
	
	
	public String toString(){
		return "LEFT";
	}

}
