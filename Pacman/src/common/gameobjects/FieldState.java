package common.gameobjects;


public enum FieldState {
	Pacman, Wall, Coin, Free;
	
	public static byte getByteValue(FieldState fs){
		if(fs == FieldState.Wall){
			return 0;
		} else if(fs == FieldState.Coin){
			return 1;
		} else if(fs == FieldState.Free){
			return 2;
		} else if(fs == FieldState.Pacman){
			return 3;
		} else {
			return -1;
		}
	}
	
	
	public static FieldState getState(byte b){
		if(b == 0){
			return FieldState.Wall;
		} else if(b == 1){
			return FieldState.Coin;
		} else if(b == 2){
			return FieldState.Free;
		} else if(b == 3){
			return FieldState.Pacman;
		} else {
			return null;
		}
	}
	
	public static String getStringValue(FieldState fs){
		if(fs == FieldState.Wall){
			return "Wall";
		} else if(fs == FieldState.Coin){
			return "Coin";
		} else if(fs == FieldState.Free){
			return "Free";
		} else if(fs == FieldState.Pacman){
			return "Pacman";
		} else {
			return "Unknown";
		}
	}
}
