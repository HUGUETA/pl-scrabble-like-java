package pl.webd.jhartman.scrabble;

import java.util.List;
import pl.webd.jhartman.scrabble.Word.WordDirection;
import pl.webd.jhartman.scrabble.gui.ScrabbleGUI;

/**
 * Represents game board including letters already on the table and the layout (e.g. bonus fields)
 * 
 * @author jhartman
 *
 */
public class Board {
	private static Dictionary dictionary = new Dictionary();
	
	/**
	 * Enumerate type of premium colors:
	 * <pre>
	 *  - NO - no premim color
	 *  - DL - double letter
	 *  - TL - triple letter
	 *  - DW - double word
	 *  - TW - triple word
	 * </pre>
	 */
	public enum PC {
		/** No - no premium color */
		NO, 
		/** DL - double letter */
		DL, 
		/** TL - tripple letter */
		TL, 
		/** DL - double word */
		DW, 
		/** DL - tripple word */
		TW
	  };
	  
	  /** Height of the board */
	  public static final int BOARD_HEIGHT = 15;
	  /** Width of the board */
	  public static final int BOARD_WIDTH  = 15;
	  
	
	/**
	 * Layout of Premium Colors (PC) on standard 15x15 Scrabble board.
	 */
	private PC[][] premiumColorsLayout = {
			{ PC.TW, PC.NO, PC.NO, PC.DL, PC.NO, PC.NO, PC.NO, PC.TW, PC.NO, PC.NO, PC.NO, PC.DL, PC.NO, PC.NO, PC.TW },
			{ PC.NO, PC.DW, PC.NO, PC.NO, PC.NO, PC.TL, PC.NO, PC.NO, PC.NO, PC.TL, PC.NO, PC.NO, PC.NO, PC.DW, PC.NO },
			{ PC.NO, PC.NO, PC.DW, PC.NO, PC.NO, PC.NO, PC.DL, PC.NO, PC.DL, PC.NO, PC.NO, PC.NO, PC.DW, PC.NO, PC.NO },
			{ PC.DL, PC.NO, PC.NO, PC.DW, PC.NO, PC.NO, PC.NO, PC.DL, PC.NO, PC.NO, PC.NO, PC.DW, PC.NO, PC.NO, PC.DL },
			{ PC.NO, PC.NO, PC.NO, PC.NO, PC.DW, PC.NO, PC.NO, PC.NO, PC.NO, PC.NO, PC.DW, PC.NO, PC.NO, PC.NO, PC.NO },
			{ PC.NO, PC.TL, PC.NO, PC.NO, PC.NO, PC.TL, PC.NO, PC.NO, PC.NO, PC.TL, PC.NO, PC.NO, PC.NO, PC.TL, PC.NO },
			{ PC.NO, PC.NO, PC.DL, PC.NO, PC.NO, PC.NO, PC.DL, PC.NO, PC.DL, PC.NO, PC.NO, PC.NO, PC.DL, PC.NO, PC.NO },
			{ PC.TW, PC.NO, PC.NO, PC.DL, PC.NO, PC.NO, PC.NO, PC.DW, PC.NO, PC.NO, PC.NO, PC.DL, PC.NO, PC.NO, PC.TW },
			{ PC.NO, PC.NO, PC.DL, PC.NO, PC.NO, PC.NO, PC.DL, PC.NO, PC.DL, PC.NO, PC.NO, PC.NO, PC.DL, PC.NO, PC.NO },
			{ PC.NO, PC.TL, PC.NO, PC.NO, PC.NO, PC.TL, PC.NO, PC.NO, PC.NO, PC.TL, PC.NO, PC.NO, PC.NO, PC.TL, PC.NO },
			{ PC.NO, PC.NO, PC.NO, PC.NO, PC.DW, PC.NO, PC.NO, PC.NO, PC.NO, PC.NO, PC.DW, PC.NO, PC.NO, PC.NO, PC.NO },
			{ PC.DL, PC.NO, PC.NO, PC.DW, PC.NO, PC.NO, PC.NO, PC.DL, PC.NO, PC.NO, PC.NO, PC.DW, PC.NO, PC.NO, PC.DL },
			{ PC.NO, PC.NO, PC.DW, PC.NO, PC.NO, PC.NO, PC.DL, PC.NO, PC.DL, PC.NO, PC.NO, PC.NO, PC.DW, PC.NO, PC.NO },
			{ PC.NO, PC.DW, PC.NO, PC.NO, PC.NO, PC.TL, PC.NO, PC.NO, PC.NO, PC.TL, PC.NO, PC.NO, PC.NO, PC.DW, PC.NO },
			{ PC.TW, PC.NO, PC.NO, PC.DL, PC.NO, PC.NO, PC.NO, PC.TW, PC.NO, PC.NO, PC.NO, PC.DL, PC.NO, PC.NO, PC.TW }			
	};
	
	/**
	 * Position of letters on standard 15x15 Scrabble board.
	 */
	private Letter[][] lettersOnBoard = new Letter[BOARD_HEIGHT][BOARD_WIDTH];
	
	/**
	 * Gets what letter is currently stored in this cell
	 * 
	 * @param x - X for cell coordinate, must be 0..14 where 0 is left, 14 - right side of the board
	 * @param y - Y for cell coordinate, must be 0..14 where 0 is top, 14 - bottom of the board
	 * @return A letter, @see Letter
	 */
	public Letter getLetter(int x, int y) {
		return lettersOnBoard[x][y];
	}
	
	/**
	 * Puts a letter to the board.
	 * 
	 * @param l - @see Letter
	 * @param x for cell coordinate, must be 0..14 where 0 is left, 14 - right side of the board
	 * @param y for cell coordinate, must be 0..14 where 0 is top, 14 - bottom of the board
	 */
	public void setLetter(Letter l, int x, int y) {
		lettersOnBoard[x][y] = l;
	}
	
	/**
	 * Gets a Premium Color - that's usefull when you want to draw the board
	 * or calculate points.
	 * 
	 * @param x for cell coordinate, must be 0..14 where 0 is left, 14 - right side of the board
	 * @param y for cell coordinate, must be 0..14 where 0 is top, 14 - bottom of the board
	 * @return - enum PC
	 */
	public PC getPremiumColor(int x, int y) {
		return premiumColorsLayout[x][y];
	}
	
	/**
	 * Check if new word is valid in context of current board
	 * 
	 * @param w - Word to add
	 * @return - true or false
	 */
	public boolean checkIfWordIsValid(Word w)
	{
		List<LetterOnBoard> letters = w.getWord();
		
		/**
		 * 1. If word is invalid (see definition of invalidity in Word.java)
		 * refuse it immediately
		 */
		if(w.getValidity() != Word.WordValidity.VALID)
			return false;
		
		/**
		 * 2. Check if fields are not already occupied 
		 */
		
		for(LetterOnBoard lob : letters)
			if(lettersOnBoard[lob.getX()][lob.getY()] != null)
			{
				w.setValidity(Word.WordValidity.INV_BOARD_ALREADY_OCCUPIED);
				return false;
			}
		
		/**
		 * 3. Get additional parameters which will be set in the Word object
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
		 */
		findSurroundingLetters(w);
		
		/** 
		 * 4. Check if new word (including already added letters has any gaps)
		 */
		if(! hasGaps(w))
		{
			w.setValidity(Word.WordValidity.INV_HAS_GAPS);
			return false;
		}
		
		/**
		 * 5. Check if new word (incl. already added) in fact contains
		 * any old letters --> new word has to be attached to old crossword
		 */
		
		if(! checkIfNotSolitare(w))
		{
			w.setValidity(Word.WordValidity.INV_SOLITARE);
			return false;
		}
		
		/**
		 * 6. And finally - check if all new words 
		 * (from letters already on the board and new one)
		 * are in the dictionary
		 * 
		 * REMOVED FROM HERE
		 * Checking against dictionary
		 * will be done when calculating total points for added word
		 * 
		 */
		
		w.setValidity(Word.WordValidity.VALID);		
		return true;
	}
	
	/**
	 * Checks if the word after adding has any gaps 
	 * (i.e. cells without any letter)
	 * @param w - new word to add
	 * @param start - where word starts (x for ACROSS, y for DOWN) (including letters already on the board)
	 * @param end - where word ends (x for ACROSS, y for DOWN) (including letters already on the board)
	 * @return - true or false
	 */
	private boolean hasGaps(Word w)
	{
		int start = w.getStartOnBoard();
		int end   = w.getEndOnBoard();
		
		if(w.getWordDirection() == Word.WordDirection.ACROSS)
		{
			int y = w.getMaxY();
			
			for(int x=start; x<(end+1); x++)
				if(getLetter(x, y) == null && (w.getLetterFrom(x, y) == null))
						return false;
		} 
		else if(w.getWordDirection() == Word.WordDirection.DOWN)
		{
			int x = w.getMaxX();
			
			for(int y=start; y<(end+1); y++)
				if(getLetter(x, y) == null && (w.getLetterFrom(x, y) == null))
						return false;
		}

		return true;
	}
	
	/**
	 * New word should somehow touch current crossword on the board
	 * (of course except when it's 1st move)
	 * @param w - new word to add
	 * @param start - where word starts (x for ACROSS, y for DOWN) (including letters already on the board)
	 * @param end - where word ends (x for ACROSS, y for DOWN) (including letters already on the board)
	 * @return - true if not solitare (i.e. valid)
	 */
	private boolean checkIfNotSolitare(Word w)
	{
		int start = w.getStartOnBoard();
		int end   = w.getEndOnBoard();
		
		/**
		 * If it's 1st move do not check if word is isolated
		 */
		if(firstMove())
		{
			/**
			 * OK, we're in 1st move but now Word should be crossing cell (7,7)
			 */
			return (w.getLetterFrom(7, 7) != null);
		}
		
		if(w.getWordDirection() == Word.WordDirection.ACROSS)
		{
			int y = w.getMaxY();

			for(int x=start; x<(end+1); x++)
			{
				/**
				 * Checks if new word includes any old letters, e.g.
				 *  Board before:
				 *
				 *   |T|O| | | |
				 *
				 * Board after
				 *    v - start
				 *   |T|O|R|N| |
				 *          ^ - end
				 *
				 * Check from start to end
				 */
				
				if(getLetter(x, y) != null)
						return true;

				/**
				 * Additionally check for any letters above and below
				 * 
				 * Board before:
				 * 
				 * | | | |T|
				 * | | | |O|
				 * | | | | |
				 * 
				 * Board after
				 * 
				 * | | | |T|
				 * | | | |O|
				 * |T|O|R|N|
				 * 
				 */
				
				if(y-1 >= 0)
					if(getLetter(x, y-1) != null)
						return true;	
				
				if(y+1 <= Board.BOARD_HEIGHT)
					if(getLetter(x, y-1) != null)
						return true;					
			}
		} 
		else if(w.getWordDirection() == Word.WordDirection.DOWN)
		{
			int x = w.getMaxX();
			
			for(int y=start; y<(end+1); y++)
			{
				if(getLetter(x, y) != null)
						return true;
				if(x-1 >= 0)
					if(getLetter(x-1, y) != null)
						return true;	
				
				if(x+1 <= Board.BOARD_WIDTH)
					if(getLetter(x+1, y) != null)
						return true;				
			}
		}
		
		return false;		
	}
	
	/**
	 * Check if the board is empty --> we're in 1st move
	 * @return
	 */
	private boolean firstMove()
	{
		for(Letter y[] : lettersOnBoard)
			for(Letter x : y)
				if(x != null)
					return false;
		
		return true;
	}
	
	private int getWhereAcrossWordBegins(Word w)
	{
		int startX = w.getMinX();
		int y      = w.getMinY();
		
		while(startX-- > 0)
		{
			if(getLetter(startX, y) == null)
				break;			
		}
		
		return startX+1;
	}
	
	private int getWhereAcrossWordBegins(LetterOnBoard lob)
	{
		int startX = lob.getX();
		int y      = lob.getY();
		
		while(startX-- > 0)
		{
			if(getLetter(startX, y) == null)
				break;			
		}
		
		return startX+1;
	}	
	
	private int getWhereAcrossWordEnds(Word w)
	{
		int startX = w.getMaxX();
		int y      = w.getMinY();
		
		while(startX++ < Board.BOARD_WIDTH)
		{
			if(getLetter(startX, y) == null)
				break;			
		}
		
		return startX-1;
	}	
	
	private int getWhereAcrossWordEnds(LetterOnBoard lob)
	{
		int startX = lob.getX();
		int y      = lob.getY();
		
		while(startX++ < Board.BOARD_WIDTH)
		{
			if(getLetter(startX, y) == null)
				break;			
		}
		
		return startX-1;
	}	
	
	
	private int getWhereDownWordBegins(Word w)
	{
		int x      = w.getMinX();
		int startY = w.getMinY();
		
		while(startY-- > 0)
		{
			if(getLetter(x, startY) == null)
				break;			
		}
		
		return startY+1;
	}
	
	private int getWhereDownWordBegins(LetterOnBoard lob)
	{
		int x      = lob.getX();
		int startY = lob.getY();
		
		while(startY-- > 0)
		{
			if(getLetter(x, startY) == null)
				break;			
		}
		
		return startY+1;
	}
	
	private int getWhereDownWordEnds(Word w)
	{
		int x       = w.getMinX();
		int startY  = w.getMaxY();
		
		while(startY++ < Board.BOARD_HEIGHT)
		{
			if(getLetter(x, startY) == null)
				break;			
		}
		
		return startY-1;
	}		
	
	private int getWhereDownWordEnds(LetterOnBoard lob)
	{
		int x       = lob.getX();
		int startY  = lob.getY();
		
		while(startY++ < Board.BOARD_HEIGHT)
		{
			if(getLetter(x, startY) == null)
				break;			
		}
		
		return startY-1;
	}		
	
	/**
	 * Adds letters from Word to the Board
	 * It does not counts any points or validates the move!
	 * @param w
	 */
	public void addWordToBoard(Word w)
	{
		List<LetterOnBoard> letters = w.getWord();
		
		if(w.getValidity() != Word.WordValidity.VALID)
			return;
		
		for(LetterOnBoard lob : letters)
			lettersOnBoard[lob.getX()][lob.getY()] = lob;
	}
	
	public void findSurroundingLetters(Word w)
	{
		/**
		 * 1. Get any letters before and after added word
		 */
		
		if(w.getWordDirection() == Word.WordDirection.ACROSS)
		{
			/**
			 * Find beginning and end of the word (including letters already on the board)
			 */
			
			w.setStartOnBoard(getWhereAcrossWordBegins(w));
			w.setEndOnBoard(getWhereAcrossWordEnds(w));
		}
		else if(w.getWordDirection() == Word.WordDirection.DOWN)
		{
			/**
			 * Find beginning and end of the word (including letters already on the board)
			 */
			
			w.setStartOnBoard(getWhereDownWordBegins(w));
			w.setEndOnBoard(getWhereDownWordEnds(w));
		}
		
		/**
		 * 2. Get any _crossing_ words for each newly added letter
		 */
		
		if(w.getWordDirection() == Word.WordDirection.ACROSS)
		{
			for(LetterOnBoard lob : w.getWord())
			{
				lob.setCrossingWordStarts(getWhereDownWordBegins(lob));
				lob.setCrossingWordEnds(getWhereDownWordEnds(lob));
			}
		}
		else if(w.getWordDirection() == Word.WordDirection.DOWN)
		{
			/**
			 * Find beginning and end of the word (including letters already on the board)
			 */
			
			for(LetterOnBoard lob : w.getWord())
			{
				lob.setCrossingWordStarts(getWhereAcrossWordBegins(lob));
				lob.setCrossingWordEnds(getWhereAcrossWordEnds(lob));
			}		
		}	
		
	}
	
	/**
	 * Calculate points gained in this move
	 * 
	 * @param w - Word to add
	 * @return - number of points
	 */
	public int calculatePoints(Word w)
	{
		int totalPoints = 0;
		
		/**
		 * Check if word has been validated AND is valid
		 */
		if(w.getValidity() != Word.WordValidity.VALID)
			return 0;
				
		if(w.getWordDirection() == WordDirection.ACROSS)
		{
			/**
			 * Before:
			 * 
			 * 
			 *   |N|E|W|
			 * 
			 * After:     
			 *   
			 *     |P|
			 *     |R|
			 *   |N|E|W|
			 *     |V|  
			 */
			
			/**
			 * 1. Calculate points from left to right
			 */
			{
				int points = 0;
				int wordBonus = 1;
				
				int start = w.getStartOnBoard();
				int end   = w.getEndOnBoard();

				int y = w.getMaxY();

				for(int x=start; x<(end+1); x++)
				{
					LetterOnBoard lob = w.getLetterFrom(x, y);
					if(getLetter(x, y) == null && (lob !=null) )
					{
						// We have new letter over here
						// Add bonuses

						wordBonus *= getWordBonus(lob);
						points += lob.getValueForLetter() * getLetterBonus(lob);
					}
					else
					{
						// Old letter over here
						// Do not get bonuses, only letter value

						points += getLetter(x,y).getValueForLetter();
					}
				}

				points *= wordBonus;
				
				if(dictionary.checkWord(getAcrossWord(w, start, end, y)))
				{
					ScrabbleGUI.writeToGameLog(" Słowo :"  + getAcrossWord(w, start, end, y) + ", punktów :" + points);
					totalPoints += points;
				}
				else
				{
					ScrabbleGUI.writeToGameLog(" Słowo nie istnieje: "  + getAcrossWord(w, start, end, y));
					w.setValidity(Word.WordValidity.INV_NOT_A_WORD);
					return 0;
				}
				
				ScrabbleGUI.writeToGameLog("Suma punktów w ruchu :" + totalPoints);
			}
			
			/** 
			 * 2. Calculate points for all _crossing_ words
			 * (combined from old letters from top to bottom)
			 */
			
			{
				for(LetterOnBoard lob : w.getWord())
				{
					int points = 0;
					int wordBonus = 1;
					
					int start = lob.getCrossingWordStarts();
					int end   = lob.getCrossingWordEnds();
					
					/**
					 * If there is no word, just single letter
					 * just added (like for N or W) - just skip it
					 */
					if(start == end)
						continue;
					
					int x = lob.getX();
					
					for(int y=start; y<(end+1); y++)
					{
						if(getLetter(x, y) == null)
						{
							// We have new letter over here
							// Add bonuses
							
							wordBonus *= getWordBonus(lob);
							points += lob.getValueForLetter() * getLetterBonus(lob);
						}
						else
						{
							// Old letter over here
							// Do not get bonuses, only letter value

							points += getLetter(x, y).getValueForLetter(); 
						}
					}
					
					points *= wordBonus;
					
					if(dictionary.checkWord(getDownWord(w, x, start, end)))
					{
						ScrabbleGUI.writeToGameLog(" Słowo :"  + getDownWord(w, x, start, end) + ", punktów :" + points);
						totalPoints += points;
					}
					else
					{
						ScrabbleGUI.writeToGameLog(" Słowo nie istnieje :"  + getDownWord(w, x, start, end));
						w.setValidity(Word.WordValidity.INV_NOT_A_WORD);
						return 0;
					}
					
					ScrabbleGUI.writeToGameLog("Suma punktów :" + totalPoints);					
				}
			}
		} 
		else if (w.getWordDirection() == WordDirection.DOWN)
		{
			/**
			 * Before:
			 *     |P|
			 *     |R|
			 *     |E|
			 *     |V|
			 
			 * After:
			 *     |P|
			 *     |R|
			 *   |N|E|W|
			 *     |V|     
			 *     
			 */
			
			/**
			 * 1. Calculate points from top to bottom
			 */
			{
				int points = 0;
				int wordBonus = 1;

				int start = w.getStartOnBoard();
				int end   = w.getEndOnBoard();			
				int x = w.getMaxX();

				for(int y=start; y<(end+1); y++)
				{
					LetterOnBoard lob = w.getLetterFrom(x, y);
					if(getLetter(x, y) == null && lob !=null )
					{
						// We have new letter over here
						// Add bonuses

						wordBonus *= getWordBonus(lob);
						points += lob.getValueForLetter() * getLetterBonus(lob);
					}
					else
					{
						// Old letter over here
						// Do not get bonuses, only letter value

						points += getLetter(x, y).getValueForLetter(); 
					}
				}

				points *= wordBonus;

				if(dictionary.checkWord(getDownWord(w, x, start, end)))
				{
					ScrabbleGUI.writeToGameLog(" Słowo :"  + getDownWord(w, x, start, end) + ", punktów :" + points);
					totalPoints += points;
				}
				else
				{
					ScrabbleGUI.writeToGameLog(" Słowo nie istnieje :"  + getDownWord(w, x, start, end));
					w.setValidity(Word.WordValidity.INV_NOT_A_WORD);
					return 0;
				}				
				
				ScrabbleGUI.writeToGameLog("Suma punktów :" + totalPoints);
			}
			
			/** 
			 * 2. Calculate points for all _crossing_ words
			 * (combined from old letters from left to right)
			 */
			
			{
				for(LetterOnBoard lob : w.getWord())
				{
					int points = 0;
					int wordBonus = 1;
					
					int start = lob.getCrossingWordStarts();
					int end   = lob.getCrossingWordEnds();
					
					/**
					 * If there is no word, just single letter
					 * just added (like for P or R) - just skip it
					 */
					if(start == end)
						continue;
					
					int y = lob.getY();
					
					for(int x=start; x<(end+1); x++)
					{
						if(getLetter(x, y) == null)
						{
							// We have new letter over here
							// Add bonuses

							wordBonus *= getWordBonus(lob);
							points += lob.getValueForLetter() * getLetterBonus(lob);
						}
						else
						{
							// Old letter over here
							// Do not get bonuses, only letter value

							points += getLetter(x,y).getValueForLetter();
						}
					}
					
					points *= wordBonus;

					if(dictionary.checkWord(getAcrossWord(w, start, end, y)))
					{
						ScrabbleGUI.writeToGameLog(" Słowo :"  + getAcrossWord(w, start, end, y) + ", punktów :" + points);
						totalPoints += points;
					}
					else
					{
						ScrabbleGUI.writeToGameLog(" Słowo nie istnieje :"  + getAcrossWord(w, start, end, y));
						w.setValidity(Word.WordValidity.INV_NOT_A_WORD);
						return 0;
					}				
					
					ScrabbleGUI.writeToGameLog(" Suma punktów :" + totalPoints);
				}
			}
		}
		
		/*
		 * If player uses all 7 letters - add extra bonus 50
		 */
		
		if(w.getWord().size() == 7)
			totalPoints += 50;
		
		return totalPoints;
	}
	
	private int getLetterBonus(LetterOnBoard lob)
	{
		switch(premiumColorsLayout[lob.getX()][lob.getY()])
		{
			case DL :
				// Double letter - x2
				return 2;
			case TL :
				// Tripple letter - x3
				return 3;		
			default:
				// No bonus - multplayer is equal 1
				return 1;
		}
	}
	
	private int getWordBonus(LetterOnBoard lob)
	{
		switch(premiumColorsLayout[lob.getX()][lob.getY()])
		{
			case DW :
				// Double word - x2
				return 2;
			case TW :
				// Tripple word - x3
				return 3;		
			default:
				// No bonus - multplayer is equal 1
				return 1;
		}
	}	
	
	public String getAcrossWord(Word w, int startX, int endX, int y)
	{
		StringBuilder word = new StringBuilder();
		
		for(int x=startX; x<(endX+1); x++)
			if(getLetter(x, y) == null)
				word.append(w.getLetterFrom(x, y));				
			else
				word.append(getLetter(x, y));
		
		return word.toString();
	}
	
	public String getDownWord(Word w, int x, int startY, int endY)
	{
		StringBuilder word = new StringBuilder();
		
		for(int y=startY; y<(endY+1); y++)
			if(getLetter(x, y) == null)
				word.append(w.getLetterFrom(x, y));				
			else
				word.append(getLetter(x, y));
		
		return word.toString();	
	}
}
