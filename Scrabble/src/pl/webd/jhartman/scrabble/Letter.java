package pl.webd.jhartman.scrabble;

/** 
 * Represents single letter.
 * This letter is now in the pool or on player's rack
 * 
 * @author jhartman
 *
 */
public class Letter {
	protected Character character;
	
	/**
	 * Array of all kinds of letters
	 */
	public static final Character[] letters      = {'A','Ą','B','C','Ć','D','E','Ę','F','G','H','I','J','K','L','Ł','M','N','Ń','O','Ó','P','R','S','Ś','T','U','W','Y','Z','Ż','Ż','.'};
	private static final int[]       letterValues = { 1,  5,  3,  2,  6,  2,  1,  5,  5,  3,  3,  1,  3,  2,  2,  3,  2,  1,  7,  1,  5,  2,  1,  1,  5,  2,  3,  1,  2,  1,  9,  5,  0 };
	private static final int[]       amountInPool  = { 9,  1,  2,  3,  1,  3,  7,  1,  1,  2,  2,  8,  2,  3,  3,  2,  3,  5,  1,  6,  1,  3,  4,  4,  1,  3,  2,  4,  4,  5,  1,  1,  2 };

	/**
	 * Fake constructor. Do not invoke
	 */
	public Letter()
	{
		this('-');
	}
	
	/**
	 * Default constuctor
	 * @param letter
	 */
	public Letter(Character letter)
	{
		character = new Character(Character.toUpperCase(letter));
	}
	
	public String toString()
	{
		return character.toString();
	}
	
	/**
	 * Returns amount of points represented by a letter
	 * @param l Letter to check
	 * @return amount of points
	 */
	public static int getValueForLetter(Character l)
	{
		l = Character.toUpperCase(l);
		
		return letterValues[ getIndexOfLetter(l) ];
	}
	
	/**
	 * Returns amount of points represented by this letter
	 * @return Amount of points
	 */
	public int getValueForLetter()
	{
		return letterValues[ getIndexOfLetter(character) ];
	}	

	/**
	 * Returns a Character represented by this letter.
	 * @return Character
	 */
	public Character getCharacter()
	{
		return character;
	}
	
	/**
	 * Returns number of letters of given kind in the pool when game begins
	 * 
	 * @param l - Letter
	 * @return - total number of letters
	 */
	public static int getAmountOfLettersInPool(Character l)
	{
		l = Character.toUpperCase(l);
		
		return amountInPool[ getIndexOfLetter(l) ];
	}

	/**
	 * Returns number of letters of given kind in the pool when game begins
	 * 
	 * @return - total number of letters
	 */
	public int getAmountOfLettersInPool()
	{
		return amountInPool[ getIndexOfLetter(character) ];
	}	
	
	private static int getIndexOfLetter(Character l)
	{
		l = Character.toUpperCase(l);

		for(int i=0; i<letters.length; i++)
		{
			if(letters[i].equals(l))
				return i;
		}
		return -1;
	}
	
}
