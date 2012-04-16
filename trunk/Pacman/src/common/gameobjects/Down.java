package common.gameobjects;

import java.awt.Point;


public class Down implements IStrategy {

	@Override
	public Point move(Point position) {
		return new Point(position.x, position.y - 1);
	}

}
