package common.gameobjects;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Map;






public class Level {

	
	private byte[][] maps;
	private Map<Point, FieldState> lastChanges;
	private Dimension mapSize = null;
	
	
	/**
	 * Constructor
	 */
	public Level(int width, int height){
		this.maps = new byte[width][height];
		this.setMapSize(new Dimension(width, height));
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
		return lastChanges;
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

}
