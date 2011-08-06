package pl.webd.jhartman.scrabble.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import pl.webd.jhartman.scrabble.Board;
import pl.webd.jhartman.scrabble.Letter;
import pl.webd.jhartman.scrabble.Player;
import pl.webd.jhartman.scrabble.Player.PlayerType;
import pl.webd.jhartman.scrabble.Scrabble;
import pl.webd.jhartman.scrabble.Word;

public class ScrabbleGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4486050578465359414L;

	public static Scrabble s;	
	public static ScrabbleGUI frame;

	private JPanel contentPane;
	private static JTextArea gameLogTextArea;
	private static JTextArea appLogTextArea;
	
	private static int step = 0;
	private Canvas boardCanvas;
	private JLabel player2NameLabel;
	private JLabel player1NameLabel;
	private JLabel player2PointsLabel;
	private JLabel player1PointsLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		s = new Scrabble(1);
		s.players.add(new Player("Jarek", PlayerType.HUMAN));
		s.players.add(new Player("Computer", PlayerType.COMPUTER));
		
		s.initialLetters();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ScrabbleGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				frame.updatePlayersDetails();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ScrabbleGUI() {

		
		setResizable(false);
		setTitle("Scrabble");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 787, 602);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel boardPanel = new JPanel();
		boardPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		boardPanel.setBounds(16, 10, 473, 473);
		contentPane.add(boardPanel);
		boardPanel.setLayout(null);
		
		boardCanvas = new Canvas() {
			private static final long serialVersionUID = 3678971088393809762L;

			@Override
			public void paint(Graphics g)
			{
				drawBoard(g);	
				drawPlayers(g);
			}
		};
		boardCanvas.setBackground(Color.WHITE);
		boardCanvas.setBounds(3, 3, 467, 467);
		boardPanel.add(boardCanvas);
		
		JPanel handPanel = new JPanel();
		handPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		handPanel.setBounds(16, 496, 473, 70);
		contentPane.add(handPanel);
		handPanel.setLayout(null);
		
		JPanel textPanels = new JPanel();
		textPanels.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		textPanels.setBounds(499, 203, 275, 362);
		contentPane.add(textPanels);
		textPanels.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 275, 363);
		textPanels.add(tabbedPane);
		
		gameLogTextArea = new JTextArea();
		gameLogTextArea.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		gameLogTextArea.setEditable(false);
		tabbedPane.addTab("Gra", null, gameLogTextArea, null);
		
		appLogTextArea = new JTextArea();
		appLogTextArea.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		appLogTextArea.setEditable(false);
		tabbedPane.addTab("Informacje", null, appLogTextArea, null);
		
		JPanel playersPanel = new JPanel();
		playersPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		playersPanel.setBounds(499, 10, 275, 189);
		contentPane.add(playersPanel);
		playersPanel.setLayout(null);
		
		JButton endButton = new JButton("Koniec");
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		endButton.setBounds(183, 148, 86, 35);
		playersPanel.add(endButton);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			/**
			 * Pressed OK Button
			 * 
			 */
			public void actionPerformed(ActionEvent e) {
				switch (step) {
				case 0:
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
				break;
				case 1:
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
				break;
				case 2:
				{
					// Get word and put on the board
					Word w = new Word();

					w.addLetter(new Letter('D'), 5, 8);
					w.addLetter(new Letter('A'), 6, 8);
					w.addLetter(new Letter('N'), 7, 8);

					addNewWord(w);
				}
				break;

				default:
					break;
				}


				step++;
			}
		});
		okButton.setBounds(6, 148, 117, 35);
		playersPanel.add(okButton);
		
		player1NameLabel = new JLabel("Player 1:");
		player1NameLabel.setBounds(6, 6, 117, 16);
		playersPanel.add(player1NameLabel);
		
		player1PointsLabel = new JLabel("Punkty: X");
		player1PointsLabel.setBounds(16, 27, 107, 16);
		playersPanel.add(player1PointsLabel);
		
		player2NameLabel = new JLabel("Player 1:");
		player2NameLabel.setBounds(6, 55, 117, 16);
		playersPanel.add(player2NameLabel);
		
		player2PointsLabel = new JLabel("Punkty: X");
		player2PointsLabel.setBounds(16, 76, 107, 16);
		playersPanel.add(player2PointsLabel);
	}
	
	
	static void drawPlayers(Graphics g)
	{
		for(Player p : s.players )
			if(p.getType() == PlayerType.HUMAN)
				drawLetters(p, g);
	}
	
	static void drawLetters(Player p, Graphics g)
	{
		List<Letter> letters = p.getAllLetters();

		for(int i=0; i<letters.size(); i++)
		{
				g.setColor (Color.yellow);
				g.fill3DRect(10+i*30, 500, 25, 25, true);

				g.setColor (Color.black);
				g.drawString(letters.get(i).toString(), 17+i*30, 518);
		}
	}	
	
	static void drawBoard(Graphics g)
	{
		for(int y=0; y<Board.BOARD_HEIGHT; y++)
			for(int x=0; x<Board.BOARD_WIDTH; x++)
			{
				if(s.board.getLetter(x, y) != null)
				{
					g.setColor (Color.yellow);
					g.fill3DRect(10+x*30, 10+y*30, 25, 25, true);

					g.setColor (Color.black);
					g.drawString(s.board.getLetter(x, y).toString(), 17+x*30, 28+y*30);
				}
				else
				{
					if (s.board.getPremiumColor(x, y) == Board.PC.NO)
					{
						g.setColor (new Color(220, 220, 220));
						g.fill3DRect(10+x*30, 10+y*30, 25, 25, true);
					}
					else if (s.board.getPremiumColor(x, y) == Board.PC.TW)
					{
						g.setColor (new Color(255, 90, 90));
						g.fill3DRect(10+x*30, 10+y*30, 25, 25, true);
					}						
					else if (s.board.getPremiumColor(x, y) == Board.PC.DW)
					{
						g.setColor (new Color(255,187,163));
						g.fill3DRect(10+x*30, 10+y*30, 25, 25, true);
					}
					else if (s.board.getPremiumColor(x, y) == Board.PC.TL)
					{
						g.setColor (new Color(90, 90, 255));
						g.fill3DRect(10+x*30, 10+y*30, 25, 25, true);
					}
					else if (s.board.getPremiumColor(x, y) == Board.PC.DL)
					{
						g.setColor (new Color(200, 200, 255));
						g.fill3DRect(10+x*30, 10+y*30, 25, 25, true);
					}
				}
			}
	}
	
	public JTextArea getGameLogTextArea() {
		return gameLogTextArea;
	}
	public JTextArea getAppLogTextArea() {
		return appLogTextArea;
	}	
	
	public static void writeToAppLog(String s)
	{
		appLogTextArea.append(s + "\n");		
	}
	
	public static void writeToGameLog(String s)
	{
		gameLogTextArea.append(s + "\n");
	}
	
	private void addNewWord(Word w)
	{
		writeToAppLog("Word direction :" + w.getWordDirection());
		
		int points = 0;
		
		if(s.board.checkIfWordIsValid(w) && (points = s.board.calculatePoints(w)) > 0)
		{
			writeToGameLog("Punkty w ruchu :" + points + "\n");
			s.board.addWordToBoard(w);	
			s.players.get(0).addScore(points);
		}
		else
			writeToGameLog("Błędny ruch :" + w.toString());
		
		// Update the board

		boardCanvas.repaint();
		frame.updatePlayersDetails();
	}	
	
	private void updatePlayersDetails()
	{
		JLabel nameLabels[]  = { player1NameLabel, player2NameLabel };
		JLabel pointLabels[] = { player1PointsLabel, player2PointsLabel }; 
		
		for(int i=0; i<s.players.size(); i++)
		{
			nameLabels[i].setText("Gracz : " + s.players.get(i).getName());
			pointLabels[i].setText("Punkty : " + s.players.get(i).getScore());
		}
	}

	public Canvas getBoardCanvas() {
		return boardCanvas;
	}
	public JLabel getPlayer2NameLabel() {
		return player2NameLabel;
	}
	public JLabel getPlayer1NameLabel() {
		return player1NameLabel;
	}
	public JLabel getPlayer2PointsLabel() {
		return player2PointsLabel;
	}
	public JLabel getPlayer1PointsLabel() {
		return player1PointsLabel;
	}
}
