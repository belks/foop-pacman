package common.gameobjects;

import java.awt.Point;




public class Right implements IStrategy {

	@Override
	public Point move(Point position) {
		return new Point(position.x + 1, position.y);
	}

}
