package common;

import java.util.Collection;
import java.util.Iterator;



public class Pacman {

	/**
	 * @uml.property   name="position"
	 * @uml.associationEnd   aggregation="shared" inverse="pacman:common.Position"
	 * @uml.association   name="have a"
	 */
	private Position position = new common.Position();

	/** 
	 * Getter of the property <tt>position</tt>
	 * @return  Returns the position.
	 * @uml.property  name="position"
	 */
	public Position getPosition() {
		return position;
	}

	/** 
	 * Setter of the property <tt>position</tt>
	 * @param position  The position to set.
	 * @uml.property  name="position"
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * @uml.property  name="Name"
	 */
	private String name;

	/**
	 * Getter of the property <tt>Name</tt>
	 * @return  Returns the name.
	 * @uml.property  name="Name"
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter of the property <tt>Name</tt>
	 * @param Name  The name to set.
	 * @uml.property  name="Name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @uml.property  name="coints"
	 */
	private int coints;

	/**
	 * Getter of the property <tt>coints</tt>
	 * @return  Returns the coints.
	 * @uml.property  name="coints"
	 */
	public int getCoints() {
		return coints;
	}

	/**
	 * Setter of the property <tt>coints</tt>
	 * @param coints  The coints to set.
	 * @uml.property  name="coints"
	 */
	public void setCoints(int coints) {
		this.coints = coints;
	}

	/**
	 * @uml.property  name="color"
	 */
	private Color color;

	/**
	 * Getter of the property <tt>color</tt>
	 * @return  Returns the color.
	 * @uml.property  name="color"
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter of the property <tt>color</tt>
	 * @param color  The color to set.
	 * @uml.property  name="color"
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @uml.property   name="game"
	 * @uml.associationEnd   inverse="pacman:common.Game"
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
	 * @param game  the game to set.
	 * @uml.property  name="game"
	 */
	public void setGame(Game game) {
		this.game = game;
	}

}
