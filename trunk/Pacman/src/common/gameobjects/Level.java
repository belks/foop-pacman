package common.gameobjects;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Level {
	
	private byte[][] maps;
	private Map<Point, FieldState> lastChanges;
	private Dimension mapSize = null;
	private int coints = 0;
	
	
	/**
	 * Constructor
	 */
	public Level(int width, int height){
		this.maps = new byte[width][height];
		this.setMapSize(new Dimension(width, height));
		lastChanges = new HashMap<Point, FieldState>();
	}
	
	public Level(int width, int height, int coints){
		this.maps = new byte[width][height];
		this.setMapSize(new Dimension(width, height));
		lastChanges = new HashMap<Point, FieldState>();
		this.coints = coints;
	}
		
	/**
	 * Getter of the property <tt>map</tt>
	 * @return  Returns the maps.
	 */
	public byte[][] getMap() {
		return maps;
	}

	
	/**
	 * Setter of the property <tt>map</tt>
	 * @param map  The maps to set.
	 */
	public void setMap(byte[][] map) {
		maps = map;
	}

	/** 
	 * Getter of the property <tt>lastChanges</tt>
	 * @return  Returns the lastChanges.
	 */
	public Map<Point, FieldState> getLastChanges() {
		Map<Point, FieldState> ret = lastChanges;
		lastChanges = new HashMap<Point, FieldState>();
		
		return ret;
	}

	/** 
	 * Setter of the property <tt>lastChanges</tt>
	 * @param lastChanges  The lastChanges to set.
	 */
	public void setLastChanges(Map<Point, FieldState> value) {
		lastChanges = value;
	}

	public void setMapSize(Dimension mapSize) {
		this.mapSize = mapSize;
	}

	public Dimension getMapSize() {
		return mapSize;
	}

	public void setFiel(Point position, FieldState fieldState){
		maps[position.x][position.y] = FieldState.getByteValue(fieldState);
		
		lastChanges.put(position, fieldState);
	}
	
	public FieldState getField(int x, int y){
		return FieldState.getState(maps[x][y]);
	}
	
	public FieldState getField(Point field){
		return FieldState.getState(maps[field.x][field.y]);
	}

	public int getCoints() {
		return coints;
	}

	public void setCoints(int coints) {
		this.coints = coints;
	}
	
	public void decrementCoints(){
		coints--;
	}
}
