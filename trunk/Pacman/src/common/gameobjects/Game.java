package common.gameobjects;


import java.util.List;

public class Game {
	private List<Pacman> pacmans = null;
	private Level level = null;
	private int totalRounds = 0;
	private int currentRound = 0;
	
	
	/**
	 * Constructor
	 */
	public Game(Level level, List<Pacman> pacmans){
		this.level = level;
		this.pacmans = pacmans;
	}

	public Game(Level level, List<Pacman> pacmans, int rounds){
		this.level = level;
		this.pacmans = pacmans;
		this.totalRounds = rounds;
	}
	
	public List<Pacman> getPacmans() {
		return pacmans;
	}

	public void addPacman(Pacman pacman) {
		if(!this.pacmans.contains(pacman)){
			this.pacmans.add(pacman);
		}
	}

	
	public void setPacmans(List<Pacman> pacmans) {
		this.pacmans = pacmans;
	}

	/**
	 * Removes a single instance of the specified element from this collection, if it is present (optional operation).
	 * @param element  to be removed from this collection, if present.
	 */
	public boolean removePacman(Pacman pacman) {
		return this.pacmans.remove(pacman);
	}

	/**
	 * Removes all of the elements from this collection (optional operation).
	 */
	public void clearAllPacmans() {
		this.pacmans.clear();
	}
		
	public Level getLevel() {
		return level;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}

	public int getTotalRounds() {
		return totalRounds;
	}

	public void setTotalRounds(int totalRounds) {
		this.totalRounds = totalRounds;
	}

	public int getCurrentRound() {
		return currentRound;
	}

	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}
	
	public void incrementRoundCount(){
		this.currentRound++;
	}
}
