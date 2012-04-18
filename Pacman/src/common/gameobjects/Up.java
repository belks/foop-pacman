package common.gameobjects;

import java.awt.Point;




public class Up implements IStrategy {

	@Override
	public Point move(Point position) {
		return new Point(position.x, position.y + 1);
	}
	
	public String toString(){
		return "UP";
	}
}
