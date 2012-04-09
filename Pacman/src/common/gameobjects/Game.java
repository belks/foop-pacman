package common.gameobjects;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;





public class Game {

	/**
	 * @uml.property   name="pacman"
	 * @uml.associationEnd   multiplicity="(1 -1)" aggregation="shared" inverse="game:common.Pacman"
	 * @uml.association   name="contains"
	 */
	private Collection<Pacman> pacman;

	/** 
	 * Getter of the property <tt>pacman</tt>
	 * @return  Returns the pacman.
	 * @uml.property  name="pacman"
	 */
	public Collection<Pacman> getPacman() {
		return pacman;
	}

	/**
	 * Returns an iterator over the elements in this collection. 
	 * @return  an <tt>Iterator</tt> over the elements in this collection
	 * @see java.util.Collection#iterator()
	 * @uml.property  name="pacman"
	 */
	public Iterator<Pacman> pacmanIterator() {
		return pacman.iterator();
	}

	/**
	 * Returns <tt>true</tt> if this collection contains no elements.
	 * @return  <tt>true</tt> if this collection contains no elements
	 * @see java.util.Collection#isEmpty()
	 * @uml.property  name="pacman"
	 */
	public boolean isPacmanEmpty() {
		return pacman.isEmpty();
	}

	/**
	 * Returns <tt>true</tt> if this collection contains the specified element. 
	 * @param element  whose presence in this collection is to be tested.
	 * @see java.util.Collection#contains(Object)
	 * @uml.property  name="pacman"
	 */
	public boolean containsPacman(Pacman pacman) {
		return this.pacman.contains(pacman);
	}

	/**
	 * Returns <tt>true</tt> if this collection contains all of the elements in the specified collection.
	 * @param elements  collection to be checked for containment in this collection.
	 * @see java.util.Collection#containsAll(Collection)
	 * @uml.property  name="pacman"
	 */
	public boolean containsAllPacman(Collection<? extends Pacman> pacman) {
		return this.pacman.containsAll(pacman);
	}

	/**
	 * Returns the number of elements in this collection.
	 * @return  the number of elements in this collection
	 * @see java.util.Collection#size()
	 * @uml.property  name="pacman"
	 */
	public int pacmanSize() {
		return pacman.size();
	}

	/**
	 * Returns all elements of this collection in an array.
	 * @return  an array containing all of the elements in this collection
	 * @see java.util.Collection#toArray()
	 * @uml.property  name="pacman"
	 */
	public Pacman[] pacmanToArray() {
		return pacman.toArray(new Pacman[pacman.size()]);
	}

	/**
	 * Returns an array containing all of the elements in this collection;  the runtime type of the returned array is that of the specified array.
	 * @param a  the array into which the elements of this collection are to be stored.
	 * @return  an array containing all of the elements in this collection
	 * @see java.util.Collection#toArray(Object[])
	 * @uml.property  name="pacman"
	 */
	public <T extends Pacman> T[] pacmanToArray(T[] pacman) {
		return (T[]) this.pacman.toArray(pacman);
	}

	/**
	 * Ensures that this collection contains the specified element (optional operation). 
	 * @param element  whose presence in this collection is to be ensured.
	 * @see java.util.Collection#add(Object)
	 * @uml.property  name="pacman"
	 */
	public boolean addPacman(Pacman pacman) {
		return this.pacman.add(pacman);
	}

	/** 
	 * Setter of the property <tt>pacman</tt>
	 * @param pacman  The pacman to set.
	 * @uml.property  name="pacman"
	 */
	public void setPacman(Collection<Pacman> pacman) {
		this.pacman = pacman;
	}

	/**
	 * Removes a single instance of the specified element from this collection, if it is present (optional operation).
	 * @param element  to be removed from this collection, if present.
	 * @see java.util.Collection#add(Object)
	 * @uml.property  name="pacman"
	 */
	public boolean removePacman(Pacman pacman) {
		return this.pacman.remove(pacman);
	}

	/**
	 * Removes all of the elements from this collection (optional operation).
	 * @see java.util.Collection#clear()
	 * @uml.property  name="pacman"
	 */
	public void clearPacman() {
		this.pacman.clear();
	}

	/**
	 * @uml.property   name="level"
	 * @uml.associationEnd   multiplicity="(1 1)" aggregation="shared" inverse="game:common.Level"
	 * @uml.association   name="contains"
	 */
	private Level level = null;

	/**
	 * Getter of the property <tt>level</tt>
	 * @return  Returns the level.
	 * @uml.property  name="level"
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Setter of the property <tt>level</tt>
	 * @param level  The level to set.
	 * @uml.property  name="level"
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

		
		/**
		 */
		public Game(Level level, Map pacman){
		}

}
