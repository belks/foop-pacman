package common.gameobjects;


public enum FieldState {
	Pacman, Wall, Coin, Free;
	
	public byte getByteValue(FieldState fs){
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
	
	
	public FieldState getField(byte b){
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
}
