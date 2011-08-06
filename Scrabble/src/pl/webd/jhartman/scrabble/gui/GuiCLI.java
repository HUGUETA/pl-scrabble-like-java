package pl.webd.jhartman.scrabble.gui;

import java.util.List;

import pl.webd.jhartman.scrabble.Board;
import pl.webd.jhartman.scrabble.Letter;
import pl.webd.jhartman.scrabble.Player;
import pl.webd.jhartman.scrabble.Scrabble;
import pl.webd.jhartman.scrabble.Word;
import pl.webd.jhartman.scrabble.Player.PlayerType;

public class GuiCLI {
	public static Scrabble s;

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		s = new Scrabble(1);
		s.players.add(new Player("Jarek", PlayerType.HUMAN));
		s.players.add(new Player("Computer", PlayerType.COMPUTER));
		
		s.initialLetters();
		
		mainLoop();
		
		System.out.println("Finished");
	}

	static void drawAll()
	{
		drawBoard();	
		drawPlayers();
	}
	
	static void drawBoard()
	{
		System.out.println("              111111");
		System.out.println("    0123456789012345");
		System.out.println("   +---------------+");
		
		
		for(int y=0; y<Board.BOARD_HEIGHT; y++)
		{
			System.out.format("%2d |", y);
			for(int x=0; x<Board.BOARD_WIDTH; x++)
			{
				if(s.board.getLetter(x, y) != null)
					System.out.print(s.board.getLetter(x, y));
				else
					if (s.board.getPremiumColor(x, y) == Board.PC.NO)
						System.out.print(" ");
					else
						System.out.print(".");
			}
			System.out.println("|");
		}
		System.out.println("   +---------------+");
	}	
	
	static void drawPlayers()
	{
		for(Player p : s.players )
		{
			System.out.println("Name : " + p.getName());
			
			drawLetters(p);
		}
	}
	
	static void drawLetters(Player p)
	{
		List<Letter> letters = p.getAllLetters();
		
		System.out.println("Numer : 1234567");
		System.out.print(  "Letter: ");
		
		for(Letter l : letters)
			System.out.print(l);
		
		System.out.println();		
		System.out.println("---------------");
	}
	
	static void mainLoop()
	{

		{
			// Get word and put on the board
			Word w = new Word();
			
			w.addLetter(new Letter('P'), 1, 7);	
			w.addLetter(new Letter('R'), 2, 7);
			w.addLetter(new Letter('Ó'), 3, 7);	
			w.addLetter(new Letter('B'), 4, 7);	
			w.addLetter(new Letter('U'), 5, 7);
			w.addLetter(new Letter('J'), 6, 7);
			w.addLetter(new Letter('E'), 7, 7);				
			
			addNewWord(w);
		}
		{
			// Get word and put on the board
			Word w = new Word();
		
			w.addLetter(new Letter('P'), 0, 2);
			w.addLetter(new Letter('R'), 0, 3);
			w.addLetter(new Letter('Y'), 0, 4);
			w.addLetter(new Letter('M'), 0, 5);
			w.addLetter(new Letter('A'), 0, 6);
			w.addLetter(new Letter('S'), 0, 7);	
			
			addNewWord(w);
		}			
	}
	
	private static void addNewWord(Word w)
	{
		System.out.println("============ New move ==================");
		
		System.out.println("Word direction :" + w.getWordDirection());
		
		if(s.board.checkIfWordIsValid(w))
		{
			System.out.println("Word :" + w + " is valid.");
			
			System.out.println("Points in move :" + s.board.calculatePoints(w));
			s.board.addWordToBoard(w);	
		}
		else
			System.out.println("Word :" + w + " is invalid: " + w.getValidity());
		
		// Update the board
		drawAll();

	}
}
