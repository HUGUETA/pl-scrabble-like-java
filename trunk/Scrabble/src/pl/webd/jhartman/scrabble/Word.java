package pl.webd.jhartman.scrabble;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates letters that are formed in a word and will be added to the Board
 * 
 * @author jhartman
 *
 */
public class Word {
	
	/**
	 * Describes if a word is horizontal (ACROSS)
	 * or vertical (DOWN)
	 */
	public static enum WordDirection { UNKNOWN, ACROSS, DOWN };
	
	/**
	 * List of check statuses for method checkIfWordIsValid
	 */
	public static enum WordValidity {
		/** Word considered as valid */
		VALID,
		/** Word invalid in terms of checks in Word.java */
		INV_SYNTAX,
		/** Trying to add new letters to occupied cells */
		INV_BOARD_ALREADY_OCCUPIED,
		/** New word (incl. old letters) has gaps */
		INV_HAS_GAPS,
		/** New word is not connected to current crossword or 1st word not passing (7,7) */
		INV_SOLITARE,
		/** Not in dictionary */
		INV_NOT_A_WORD,
		/** unknown */
		INV_OTHER
	}	
	
	private List<LetterOnBoard> letters = new ArrayList<LetterOnBoard>();
	private WordDirection wordDirection;
	
	/**
	 *  Describers where new word starts and ends
	 *  (including letters already on the board):
	 *
	 * Board before:
	 *
	 *   |T|O| | | |
	 *
	 * Board after
	 *    v - startOnBoard
	 *   |T|O|R|N| |
	 *          ^ - endOnBoard
	 *
	 * For ACROSS words it's X-coordinate,
	 * for DOWN words - it's Y-coordinate.
	 * 
	 * These values will be written by an method in Board class
	 * because it can be set _only_ in context of current board.
	 * 
	 */
	private int startOnBoard;
	private int endOnBoard;
	
	/**
	 * Informs if word is considered as valid:
	 *  - letters in word do not overlap
	 *  - letters are in one line (horizontal or vertical)
	 */
	private WordValidity validity;
	
	public Word()
	{
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("[");
		
		for(LetterOnBoard lob : letters)
			sb.append(lob + "(" + lob.getX() + "," + lob.getY() + ")(" + lob.getValueForLetter() + ") ");

		sb.append("]");
		
		return sb.toString();
	}
	
	/**
	 * Adds a letter to the Word. 
	 * @param l New Letter
	 * @param x Coordinate where this letter will be placed on the board
	 * @param y Coordinate where this letter will be placed on the board
	 */
	public void addLetter(Letter l, int x, int y) {
		LetterOnBoard lob = new LetterOnBoard(l, x, y);
		
		// check if it contains letters in same place
		for(LetterOnBoard l_check : letters)
			if(l_check.getX() == x && l_check.getY() == y)
			{
				validity = WordValidity.INV_SYNTAX;
				return;
			}

		// check if all new letters are in one row
		// (horizonal or verticcal)
		
		int width = 0;
		int height = 0;
		
		for(LetterOnBoard l_check : letters)
		{
			width  = Math.max(width, Math.abs(l_check.getX()-x));
			height = Math.max(height, Math.abs(l_check.getY()-y));
		}

		if(width>0 && height>0)
		{
			validity = WordValidity.INV_SYNTAX;
			return;
		}
		
		//... if everything is fine - add the letter
		
		validity = WordValidity.VALID;
		
		letters.add(lob);
	}
		
	/**
	 * Returns List of LetterOnBoard constituting this Word
	 * @return List of LetterOnBoard 
	 */
	public List<LetterOnBoard> getWord()
	{
		return letters;
	}

	/**
	 * Check where word begins
	 * 
	 * @return - X-coordinate on the board
	 */
	public int getMinX()
	{
		int min=Board.BOARD_WIDTH;
		
		for(LetterOnBoard lob : letters)
			min = Math.min(min, lob.getX());
		
		return min;
	}

	/**
	 * Check where word ends.
	 * 
	 * @return - X-coordinate on the board
	 */
	public int getMaxX()
	{
		int max=0;
		
		for(LetterOnBoard lob : letters)
			max = Math.max(max, lob.getX());
		
		return max;
	}
	
	/**
	 * Check where word begins.
	 * 
	 * @return - Y-coordinate on the board
	 */	
	public int getMinY()
	{
		int min=Board.BOARD_HEIGHT;
		
		for(LetterOnBoard lob : letters)
			min = Math.min(min, lob.getY());
		
		return min;
	}
	
	/**
	 * Check where word ends.
	 * 
	 * @return - Y-coordinate on the board
	 */	
	public int getMaxY()
	{
		int max=0;
		
		for(LetterOnBoard lob : letters)
			max = Math.max(max, lob.getY());
		
		return max;
	}	

	/**
	 * Gets a LetterOnBoard which is in the word and 
	 * is located at certain coordinates.
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 * @return - a LetterOnBoard
	 */
	public LetterOnBoard getLetterFrom(int x, int y)
	{
		for(LetterOnBoard lob : letters)
			if(lob.getX() == x && lob.getY() == y)
				return lob;
		
		return null;
	}	
	
	/**
	 * Detects if VALID word is horizontal (ACROSS) or vertical (DOWN) @see WordValidity
	 * @return WordDirection enum
	 */
	public WordDirection getWordDirection()
	{
		if(wordDirection != null)
			return wordDirection;
		
		if(validity != WordValidity.VALID)
		{
			wordDirection = WordDirection.UNKNOWN;
			return WordDirection.UNKNOWN;
		}

		int width  = getMaxX() - getMinX();
		int height = getMaxY() - getMinY();

		if(width > 0 && height == 0)
			wordDirection = WordDirection.ACROSS;
		else if(width == 0 && height > 0)
			wordDirection = WordDirection.DOWN;
		else
			wordDirection = WordDirection.UNKNOWN;		
			
		return wordDirection;				
	}

	/**
	 * @return the validity
	 */
	public WordValidity getValidity() {
		return validity;
	}

	/**
	 * @param validity the validity to set
	 */
	public void setValidity(WordValidity validity) {
		this.validity = validity;
	}

	/**
	 * @return the startOnBoard
	 */
	public int getStartOnBoard() {
		return startOnBoard;
	}

	/**
	 * @param startOnBoard the startOnBoard to set
	 */
	public void setStartOnBoard(int startOnBoard) {
		this.startOnBoard = startOnBoard;
	}

	/**
	 * @return the endOnBoard
	 */
	public int getEndOnBoard() {
		return endOnBoard;
	}

	/**
	 * @param endOnBoard the endOnBoard to set
	 */
	public void setEndOnBoard(int endOnBoard) {
		this.endOnBoard = endOnBoard;
	}
}
