package pl.webd.jhartman.scrabble;

import java.util.ArrayList;

public class Scrabble {
	public final Board board = new Board();
	public ArrayList<Player> players;
	public Pool pool = new Pool();
	
	public Scrabble(int howManyPlayers){
		players = new ArrayList<Player>(howManyPlayers);
	}
	
	public void run() {
	}
	
	/**
	 * Each player gets 7 letters.
	 */
	public void initialLetters()
	{
		for(Player p : players)
			for(int i=0; i<7; i++)
				p.addLetter(pool.getNext());
	}
}
