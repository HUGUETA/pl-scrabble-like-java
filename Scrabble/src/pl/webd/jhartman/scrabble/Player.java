package pl.webd.jhartman.scrabble;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates all player details
 * 
 * @author jhartman
 *
 */
public class Player {
	private String name;
	private int score;
	private PlayerType type;
	private ArrayList<Letter> letters = new ArrayList<Letter>();
	
	public static enum PlayerType {HUMAN, COMPUTER};
	
	public Player()
	{
		this("Undefined", PlayerType.COMPUTER);
	}
	
	/**
	 * Constructor which should be normally invoked.
	 * 
	 * @param name
	 */
	public Player(String name, PlayerType type)
	{
		this.name = name;
		this.setType(type);
		score = 0;
	}
	
	/**
	 * Constructor which sets the name and adds initial letters
	 * 
	 * @param name
	 * @param startLetters
	 */
	public Player(String name, ArrayList<Letter> startLetters)
	{
		letters.addAll(startLetters);
		this.name = name;
		score = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Adds points to player's score
	 * 
	 * @param add
	 * @return Amount of points after adding
	 */
	public int addScore(int add) {
		score += add;
		return score;
	}

	/**
	 * Gets current player's score
	 * @return Current amount of points
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Gets all player's letters (available in his rack)
	 * @return List of player's letters
	 */
	public List<Letter> getAllLetters() {
		return letters;
	}

	/**
	 * Adds a letter to player's rack
	 * 
	 * @param l Letter to add
	 */
	public void addLetter(Letter l) {
		letters.add(l);
	}

	/**
	 * Removes a letter from player's rack
	 * 
	 * @param l Letter to remove
	 */
	public void useLetter(Letter l) {
	}

	public PlayerType getType() {
		return type;
	}

	public void setType(PlayerType type) {
		this.type = type;
	}
}
