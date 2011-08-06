package pl.webd.jhartman.scrabble;

/** 
 * Single letter placed on the board
 * 
 * @author jhartman
 * 
 */
public class LetterOnBoard extends Letter {
	/** X coordinate for the letter */
	private int x;
	/** Y coordinate for the letter */
	private int y;
	
	/**
	 * For each LetterOnBoard we need to find all _crossing_ words
	 * that appeared from adding a new word: 
	 * 
	 * Before:
	 *    1 2 3 4
	 * 1 |D|
	 * 2 |I|
	 * 3 |E|
	 * 4
	 * 
	 * After move:
	 *    1 2 3 4
	 * 1 |D|
	 * 2 |I|
	 * 3 |E|
	 * 4 |D|A|T|A| 
	 * 
	 *  For D added at (1,4) we need to find where new word starts
	 *  (this crossingWordStarts=1 - 1st D in DIED) 
	 *  and where new word ends (crossingWordEnds=4 - last D in DIED).
	 *  So new word DIED spans between 1 and 4.
	 *  Direction of new word will be opposite to direction of word to be added.
	 *  
	 *  For A(1,4), T(3,4) and A(4,4) crossingWordStarts=crossingWordEnds=4 --> it won't be counted as pointed words
	 * 
	 * These values will be written by an method in Board class
	 * beacuse it can be set _only_ in context of current board.
	 * 
	 */
	private int crossingWordStarts;
	private int crossingWordEnds;
	
	public LetterOnBoard(Letter l, int x, int y)
	{
		super(l.getCharacter());
		
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the crossingWordStarts
	 */
	public int getCrossingWordStarts() {
		return crossingWordStarts;
	}

	/**
	 * @param crossingWordStarts the crossingWordStarts to set
	 */
	public void setCrossingWordStarts(int crossingWordStarts) {
		this.crossingWordStarts = crossingWordStarts;
	}

	/**
	 * @return the crossingWordEnds
	 */
	public int getCrossingWordEnds() {
		return crossingWordEnds;
	}

	/**
	 * @param crossingWordEnds the crossingWordEnds to set
	 */
	public void setCrossingWordEnds(int crossingWordEnds) {
		this.crossingWordEnds = crossingWordEnds;
	}
}
