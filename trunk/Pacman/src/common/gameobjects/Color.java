package common.gameobjects;


public enum Color {
	Blue, Red, White;
	
	public static int getValue(Color c){
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
	
	
	public static Color getColor(byte b){
		if(b == 0){
			return Color.Blue;
		} else if(b == 1){
			return Color.Red;
		} else if(b == 2){
			return Color.White;
		} else {
			return null;
		}
	}
}