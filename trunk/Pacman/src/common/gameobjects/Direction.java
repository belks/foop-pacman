package common.gameobjects;

public enum Direction {
	Up, Down, Left, Right;
	
	public static int getValue(Direction c){
		if(c == Direction.Up){
			return 0;
			
		} else if(c == Direction.Down){
			return 1;
			
		} else if(c == Direction.Left){
			return 2;
			
		} else if(c == Direction.Right){
			return 3;
			
		} else {
			return -1;
		}
	}
	
	
	public static Direction getDirection(byte b){
		if(b == 0){
			return Direction.Up;
			
		} else if(b == 1){
			return Direction.Down;
			
		} else if(b == 2){
			return Direction.Left;
			
		} else if(b == 3){
			return Direction.Right;
			
		} else {
			return null;
		}
	}
	

}
