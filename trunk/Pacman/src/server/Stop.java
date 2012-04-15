package server;

import java.awt.Point;

public class Stop implements IStrategy{

	@Override
	public Point move(Point position) {
		return position;
	}

}
