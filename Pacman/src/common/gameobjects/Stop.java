package common.gameobjects;

import java.awt.Point;

public class Stop implements IStrategy{

	@Override
	public Point move(Point position) {
		return position;
	}
	
	
	public String toString(){
		return "STOP";
	}

}
