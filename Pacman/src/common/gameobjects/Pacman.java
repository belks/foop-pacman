package common.gameobjects;

import java.awt.Point;





public class Pacman {
	
	private Point position = null;
	private String name = null;
	private int coints = 0;
	private Color color = null;
	
	
	public Pacman(String name, Color color){
		this.name = name;
		this.color = color;
	}
	

	/** 
	 * Getter of the property <tt>position</tt>
	 * @return  Returns the position.
	 * @uml.property  name="position"
	 */
	public Point getPosition() {
		return position;
	}

	/** 
	 * Setter of the property <tt>position</tt>
	 * @param position  The position to set.
	 * @uml.property  name="position"
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

	

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



}
