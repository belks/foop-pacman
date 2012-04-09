package common.gameobjects;




public class Position {

	/**
	 * @uml.property  name="xPosition"
	 */
	private String xPosition;

	/**
	 * Getter of the property <tt>xPosition</tt>
	 * @return  Returns the xPosition.
	 * @uml.property  name="xPosition"
	 */
	public String getXPosition() {
		return xPosition;
	}

	/**
	 * Setter of the property <tt>xPosition</tt>
	 * @param xPosition  The xPosition to set.
	 * @uml.property  name="xPosition"
	 */
	public void setXPosition(String xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * @uml.property  name="yPostion"
	 */
	private String yPostion;

	/**
	 * Getter of the property <tt>yPostion</tt>
	 * @return  Returns the yPostion.
	 * @uml.property  name="yPostion"
	 */
	public String getYPostion() {
		return yPostion;
	}

	/**
	 * Setter of the property <tt>yPostion</tt>
	 * @param yPostion  The yPostion to set.
	 * @uml.property  name="yPostion"
	 */
	public void setYPostion(String yPostion) {
		this.yPostion = yPostion;
	}

	/**
	 * @uml.property   name="pacman"
	 * @uml.associationEnd   inverse="position:common.Pacman"
	 * @uml.association   name="have a"
	 */
	private Pacman pacman;

	/** 
	 * Getter of the property <tt>pacman</tt>
	 * @return  Returns the pacman.
	 * @uml.property  name="pacman"
	 */
	public Pacman getPacman() {
		return pacman;
	}

	/** 
	 * Setter of the property <tt>pacman</tt>
	 * @param pacman  The pacman to set.
	 * @uml.property  name="pacman"
	 */
	public void setPacman(Pacman pacman) {
		this.pacman = pacman;
	}

}
