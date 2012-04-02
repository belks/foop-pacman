package common;

import java.util.Map;





public class Level {

	/**
	 * @uml.property   name="map"
	 */
	private byte[][] maps;
	/**
	 * @uml.property   name="lastChanges"
	 */
	private Map<Position, FieldState> lastChanges;
	/**
	 * Getter of the property <tt>map</tt>
	 * @return  Returns the maps.
	 * @uml.property  name="map"
	 */
	public byte[][] getMap() {
		return maps;
	}

	/**
	 * Setter of the property <tt>map</tt>
	 * @param map  The maps to set.
	 * @uml.property  name="map"
	 */
	public void setMap(byte[][] map) {
		maps = map;
	}

	/** 
	 * Getter of the property <tt>lastChanges</tt>
	 * @return  Returns the lastChanges.
	 * @uml.property  name="lastChanges"
	 */
	Map getLastChanges() {
		return lastChanges;
	}

	/** 
	 * Setter of the property <tt>lastChanges</tt>
	 * @param lastChanges  The lastChanges to set.
	 * @uml.property  name="lastChanges"
	 */
	public void setLastChanges(Map<Position, FieldState> value) {
		lastChanges = value;
	}

		
		/**
		 */
		public Level(int height, int width){
		}


		/**
		 * @uml.property   name="game"
		 * @uml.associationEnd   inverse="level:common.Game"
		 * @uml.association   name="contains"
		 */
		private Game game;
		/**
		 * Getter of the property <tt>game</tt>
		 * @return  Returns the game.
		 * @uml.property  name="game"
		 */
		public Game getGame() {
			return game;
		}

		/**
		 * Setter of the property <tt>game</tt>
		 * @param game  The game to set.
		 * @uml.property  name="game"
		 */
		public void setGame(Game game) {
			this.game = game;
		}

}
