package common.gameobjects;


public enum Color {
	Blue, Red, White;
	
	public int getValue(Color c){
		if(c == Color.Blue){
			return 0;
		} else if(c == Color.Red){
			return 1;
		} else if(c == Color.White){
			return 2;
		} else {
			return -1;
		}
	}
}
