package common;


public enum FieldState {
	Pacman, Wall, Coin, Free;
	
	public int getByteValue(FieldState fs){
		if(fs == FieldState.Free){
			return 0;
		} else if(fs == FieldState.Coin){
			return 1;
		} else if(fs == FieldState.Wall){
			return 2;
		} else if(fs == FieldState.Pacman){
			return 3;
		} else {
			return -1;
		}
	}
}
